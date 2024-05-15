package lucene.engine.lucene.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PubMedService {

    private final RestTemplate restTemplate;
    private static final Logger LOGGER = Logger.getLogger(PubMedService.class.getName());
    private static final int MAX_RETRIES = 5;

    @Autowired
    public PubMedService() {
        this.restTemplate = new RestTemplate();
        this.restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    public PubMedResponse searchDDI(String drugA, String drugB) {
        String query = "\"" + drugA + "\" AND \"" + drugB + "\" AND (interaction OR interactions OR adverse OR effect)";
        String url = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&term=" + query + "&retmode=xml&retmax=10";

        ResponseEntity<String> response = makeRequestWithRetry(url);
        List<String> pubMedIds = parsePubMedIds(response.getBody());
        List<PubMedResponse.PubMedArticle> articles = fetchArticleDetails(pubMedIds);
        return new PubMedResponse(articles);
    }

    private List<String> parsePubMedIds(String xml) {
        List<String> pubMedIds = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));
            Document doc = builder.parse(is);

            NodeList idNodes = doc.getElementsByTagName("Id");
            for (int i = 0; i < idNodes.getLength(); i++) {
                pubMedIds.add(idNodes.item(i).getTextContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pubMedIds;
    }

    private List<PubMedResponse.PubMedArticle> fetchArticleDetails(List<String> pubMedIds) {
        List<PubMedResponse.PubMedArticle> articles = new ArrayList<>();
        try {
            String ids = String.join(",", pubMedIds);
            String url = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi?db=pubmed&id=" + ids + "&retmode=xml";

            ResponseEntity<String> response = makeRequestWithRetry(url);
            LOGGER.info("XML Response: " + response.getBody());

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(response.getBody()));
            Document doc = builder.parse(is);

            NodeList docSums = doc.getElementsByTagName("DocSum");
            for (int i = 0; i < docSums.getLength(); i++) {
                Element docSumElement = (Element) docSums.item(i);

                Node idNode = docSumElement.getElementsByTagName("Id").item(0);
                Node titleNode = docSumElement.getElementsByTagName("Title").item(0);
                Node abstractNode = docSumElement.getElementsByTagName("Source").item(0);  // Adjust this line as per the actual XML structure

                String id = idNode != null ? idNode.getTextContent() : null;
                String title = titleNode != null ? titleNode.getTextContent() : null;
                String abstractText = abstractNode != null ? abstractNode.getTextContent() : null;

                String content = fetchArticleContent(id);

                articles.add(new PubMedResponse.PubMedArticle(id, title, abstractText, content));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articles;
    }

    private String fetchArticleContent(String id) {
        try {
            String url = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id=" + id + "&retmode=xml";

            ResponseEntity<String> response = makeRequestWithRetry(url);
            LOGGER.info("Article XML Response: " + response.getBody());

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(response.getBody()));
            Document doc = builder.parse(is);

            NodeList abstractTextNodes = doc.getElementsByTagName("AbstractText");
            if (abstractTextNodes.getLength() > 0) {
                return abstractTextNodes.item(0).getTextContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ResponseEntity<String> makeRequestWithRetry(String url) {
        int attempt = 0;
        while (attempt < MAX_RETRIES) {
            try {
                return restTemplate.getForEntity(url, String.class);
            } catch (HttpClientErrorException.TooManyRequests e) {
                attempt++;
                if (attempt == MAX_RETRIES) {
                    throw e;
                }
                try {
                    long waitTime = (long) Math.pow(2, attempt) * 1000;
                    Thread.sleep(waitTime);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            } catch (RestClientException e) {
                throw e;
            }
        }
        throw new RestClientException("Max retries reached");
    }
}
