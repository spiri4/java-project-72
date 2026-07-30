package hexlet.code.dto;

import java.util.List;

public class UrlsPage extends BasePage {
    private List<UrlListItem> urls;

    public UrlsPage(List<UrlListItem> urls) {
        this.urls = urls;
    }

    public List<UrlListItem> getUrls() {
        return urls;
    }
}
