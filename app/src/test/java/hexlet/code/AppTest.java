package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    private Javalin app;

    @BeforeEach
    public final void setUp() throws IOException, SQLException {
        // Тесты всегда на H2, даже если в окружении задан PostgreSQL
        System.setProperty("JDBC_DATABASE_URL", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;");
        app = App.getApp();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.rootPath());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlsPath());
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("data-test=\"urls\"");
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://example.com/path";
            var response = client.post(NamedRoutes.urlsPath(), requestBody);

            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://example.com");

            var savedUrl = UrlRepository.findByName("https://example.com");
            assertThat(savedUrl).isPresent();
            assertThat(savedUrl.get().getName()).isEqualTo("https://example.com");

            var showResponse = client.get(NamedRoutes.urlPath(savedUrl.get().getId()));
            assertThat(showResponse.code()).isEqualTo(200);
            assertThat(showResponse.body().string())
                    .contains("https://example.com")
                    .contains("data-test=\"url\"")
                    .contains("data-test=\"checks\"")
                    .contains("Запустить проверку");
        });
    }

    @Test
    public void testCreateUrlWithPort() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://example.com:8080/some/path";
            var response = client.post(NamedRoutes.urlsPath(), requestBody);

            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://example.com:8080");
            assertThat(UrlRepository.findByName("https://example.com:8080")).isPresent();
        });
    }

    @Test
    public void testCreateExistingUrl() {
        JavalinTest.test(app, (server, client) -> {
            client.post(NamedRoutes.urlsPath(), "url=https://example.com");
            var response = client.post(NamedRoutes.urlsPath(), "url=https://example.com/again");

            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://example.com");

            var urls = UrlRepository.getEntities();
            assertThat(urls).hasSize(1);

            var showResponse = client.get(NamedRoutes.urlPath(urls.get(0).getId()));
            assertThat(showResponse.code()).isEqualTo(200);
            assertThat(showResponse.body().string()).contains("https://example.com");
        });
    }

    @Test
    public void testCreateInvalidUrl() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.post(NamedRoutes.urlsPath(), "url=not-a-url");

            assertThat(response.code()).isEqualTo(422);
            assertThat(response.body().string()).contains("Некорректный URL");
            assertThat(UrlRepository.getEntities()).isEmpty();
        });
    }

    @Test
    public void testUrlPage() throws SQLException {
        var url = new Url("https://example.com");
        UrlRepository.save(url);

        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlPath(url.getId()));
            assertThat(response.code()).isEqualTo(200);
            var body = response.body().string();
            assertThat(body)
                    .contains("https://example.com")
                    .contains("data-test=\"url\"")
                    .contains("data-test=\"checks\"")
                    .contains("form method=\"post\" action=\"/urls/" + url.getId() + "/checks\"");
        });
    }

    @Test
    public void testUrlNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlPath(99999L));
            assertThat(response.code()).isEqualTo(404);
        });
    }
}
