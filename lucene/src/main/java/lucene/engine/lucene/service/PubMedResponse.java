package lucene.engine.lucene.service;

import java.util.List;

public class PubMedResponse {
    private List<PubMedArticle> articles;

    public PubMedResponse(List<PubMedArticle> articles) {
        this.articles = articles;
    }

    public List<PubMedArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<PubMedArticle> articles) {
        this.articles = articles;
    }

    public static class PubMedArticle {
        private String id;
        private String title;
        private String abstractText;
        private String content;

        public PubMedArticle(String id, String title, String abstractText, String content) {
            this.id = id;
            this.title = title;
            this.abstractText = abstractText;
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAbstractText() {
            return abstractText;
        }

        public void setAbstractText(String abstractText) {
            this.abstractText = abstractText;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
