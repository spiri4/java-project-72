package hexlet.code.dto;

import hexlet.code.model.Url;

import java.util.ArrayList;
import java.util.List;

public class UrlPage extends BasePage {
    private Url url;
    private List<Object> checks = new ArrayList<>();

    public UrlPage(Url url) {
        this.url = url;
    }

    public Url getUrl() {
        return url;
    }

    public List<Object> getChecks() {
        return checks;
    }

    public void setChecks(List<Object> checks) {
        this.checks = checks;
    }
}
