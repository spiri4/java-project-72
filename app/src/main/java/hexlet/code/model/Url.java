package hexlet.code.model;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class Url {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Long id;
    private String name;
    private Timestamp createdAt;
    private Timestamp lastCheckCreatedAt;
    private Integer lastCheckStatusCode;

    public Url() {
    }

    public Url(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLastCheckCreatedAt() {
        return lastCheckCreatedAt;
    }

    public void setLastCheckCreatedAt(Timestamp lastCheckCreatedAt) {
        this.lastCheckCreatedAt = lastCheckCreatedAt;
    }

    public Integer getLastCheckStatusCode() {
        return lastCheckStatusCode;
    }

    public void setLastCheckStatusCode(Integer lastCheckStatusCode) {
        this.lastCheckStatusCode = lastCheckStatusCode;
    }

    public String getFormattedCreatedAt() {
        if (createdAt == null) {
            return "";
        }
        return createdAt.toLocalDateTime().format(DATE_FORMATTER);
    }

    public String getFormattedLastCheckCreatedAt() {
        if (lastCheckCreatedAt == null) {
            return "";
        }
        return lastCheckCreatedAt.toLocalDateTime().format(DATE_FORMATTER);
    }

    public String getFormattedLastCheckStatusCode() {
        return lastCheckStatusCode == null ? "" : String.valueOf(lastCheckStatusCode);
    }
}
