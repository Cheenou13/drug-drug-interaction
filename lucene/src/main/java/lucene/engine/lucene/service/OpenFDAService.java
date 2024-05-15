package lucene.engine.lucene.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class OpenFDAService {
    private static final String OPEN_FDA_URL = "https://api.fda.gov/drug/label.json?search=drug_interactions:([DRUG1]+AND+[DRUG2])";

    public String getDrugInteractions(String drug1, String drug2) throws IOException {
        String url = OPEN_FDA_URL.replace("[DRUG1]", drug1).replace("[DRUG2]", drug2);
        System.out.println("OPENFDA URL: "+url);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
