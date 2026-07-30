package hexlet.code.dto;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;

import java.time.format.DateTimeFormatter;

public class UrlListItem {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final Url url;
    private final UrlCheck lastCheck;

    public UrlListItem(Url url, UrlCheck lastCheck) {
        this.url = url;
        this.lastCheck = lastCheck;
    }

    public Url getUrl() {
        return url;
    }

    public UrlCheck getLastCheck() {
        return lastCheck;
    }

    public String getFormattedLastCheckCreatedAt() {
        if (lastCheck == null || lastCheck.getCreatedAt() == null) {
            return "";
        }
        return lastCheck.getCreatedAt().format(DATE_TIME_FORMATTER);
    }

    public String getFormattedLastCheckStatusCode() {
        if (lastCheck == null || lastCheck.getStatusCode() == null) {
            return "";
        }
        return String.valueOf(lastCheck.getStatusCode());
    }
}
