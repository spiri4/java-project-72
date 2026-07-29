package hexlet.code.dto;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import java.util.ArrayList;
import java.util.List;

public class UrlPage extends BasePage {
    private Url url;
    private List<UrlCheck> checks = new ArrayList<>();

    public UrlPage(Url url) {
        this.url = url;
    }

    public UrlPage(Url url, List<UrlCheck> checks) {
        this.url = url;
        this.checks = checks;
    }

    public Url getUrl() {
        return url;
    }

    public List<UrlCheck> getChecks() {
        return checks;
    }

    public void setChecks(List<UrlCheck> checks) {
        this.checks = checks;
    }
}
