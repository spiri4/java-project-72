package hexlet.code.repository;

import hexlet.code.model.UrlCheck;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UrlCheckRepository extends BaseRepository {
    public static void save(UrlCheck urlCheck) throws SQLException {
        var sql = "INSERT INTO url_checks (url_id, status_code, h1, title, description, created_at)"
                + " VALUES (?, ?, ?, ?, ?, ?)";
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            var createdAt = LocalDateTime.now();
            statement.setLong(1, urlCheck.getUrlId());
            statement.setInt(2, urlCheck.getStatusCode());
            statement.setString(3, urlCheck.getH1());
            statement.setString(4, urlCheck.getTitle());
            statement.setString(5, urlCheck.getDescription());
            statement.setTimestamp(6, Timestamp.valueOf(createdAt));
            statement.executeUpdate();

            var generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                urlCheck.setId(generatedKeys.getLong(1));
                urlCheck.setCreatedAt(createdAt);
            } else {
                throw new SQLException("DB did not return an id after saving UrlCheck");
            }
        }
    }

    public static List<UrlCheck> findByUrlId(Long urlId) throws SQLException {
        var sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY id DESC";
        var result = new ArrayList<UrlCheck>();
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, urlId);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(buildUrlCheck(resultSet));
            }
        }
        return result;
    }

    public static Optional<UrlCheck> findLatestByUrlId(Long urlId) throws SQLException {
        var sql = "SELECT * FROM url_checks WHERE url_id = ? ORDER BY id DESC LIMIT 1";
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, urlId);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(buildUrlCheck(resultSet));
            }
            return Optional.empty();
        }
    }

    public static Map<Long, UrlCheck> findLatestChecks() throws SQLException {
        var sql = "SELECT DISTINCT ON (url_id) * FROM url_checks ORDER BY url_id DESC, id DESC";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            var result = new HashMap<Long, UrlCheck>();
            while (resultSet.next()) {
                var urlCheck = buildUrlCheck(resultSet);
                result.put(urlCheck.getUrlId(), urlCheck);
            }
            return result;
        }
    }

    private static UrlCheck buildUrlCheck(java.sql.ResultSet resultSet) throws SQLException {
        var urlCheck = new UrlCheck(
                resultSet.getInt("status_code"),
                resultSet.getString("title"),
                resultSet.getString("h1"),
                resultSet.getString("description"),
                resultSet.getLong("url_id")
        );
        urlCheck.setId(resultSet.getLong("id"));
        urlCheck.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        return urlCheck;
    }
}
