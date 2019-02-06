package ebitza.itemcalculator.Search_views;

public class SearchResult  {
        private String title;
        private String description;
        private String iconUrl;

        public SearchResult() {
            this.title = title;
            this.description = description;
            this.iconUrl = iconUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }
}