package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import hexlet.code.util.UrlNormalizer;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    private static MockWebServer mockWebServer;
    private Javalin app;

    @BeforeAll
    public static void beforeAll() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    public static void afterAll() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    public final void setUp() throws IOException, SQLException {
        System.setProperty("JDBC_DATABASE_URL", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;");
        app = App.getApp();
    }

    private static String readFixture(String fileName) throws IOException {
        return Files.readString(
                Paths.get("src", "test", "resources", "fixtures", fileName).toAbsolutePath().normalize()
        ).trim();
    }

    private String mockUrl() {
        try {
            return UrlNormalizer.normalize(new java.net.URI(mockWebServer.url("/").toString()));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
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

    @Test
    public void testUrlCheck() throws IOException, SQLException {
        var url = new Url(mockUrl());
        UrlRepository.save(url);

        mockWebServer.enqueue(new MockResponse()
                .setBody(readFixture("index.html"))
                .setResponseCode(200));

        JavalinTest.test(app, (server, client) -> {
            var response = client.post(NamedRoutes.urlChecksPath(url.getId()));
            assertThat(response.code()).isEqualTo(200);
            var body = response.body().string();
            assertThat(body)
                    .contains("200")
                    .contains("Test page title")
                    .contains("Test header")
                    .contains("Test description for page analyzer")
                    .contains("data-test=\"checks\"");

            var checks = UrlCheckRepository.findByUrlId(url.getId());
            assertThat(checks).hasSize(1);
            assertThat(checks.get(0).getStatusCode()).isEqualTo(200);
            assertThat(checks.get(0).getTitle()).isEqualTo("Test page title");
            assertThat(checks.get(0).getH1()).isEqualTo("Test header");
            assertThat(checks.get(0).getDescription()).isEqualTo("Test description for page analyzer");

            var listResponse = client.get(NamedRoutes.urlsPath());
            assertThat(listResponse.body().string()).contains("200");
        });
    }

    @Test
    public void testUrlCheckErrorStatus() throws SQLException {
        var url = new Url(mockUrl());
        UrlRepository.save(url);

        mockWebServer.enqueue(new MockResponse().setResponseCode(500));

        JavalinTest.test(app, (server, client) -> {
            var response = client.post(NamedRoutes.urlChecksPath(url.getId()));
            assertThat(response.code()).isEqualTo(200);
            assertThat(UrlCheckRepository.findByUrlId(url.getId())).isEmpty();

            var body = response.body().string();
            assertThat(body).contains("data-test=\"checks\"");
            assertThat(body).doesNotContain("<td>500</td>");
        });
    }

    @Test
    public void testUrlCheckTruncation() throws SQLException {
        var url = new Url(mockUrl());
        UrlRepository.save(url);

        var longTitle = "T".repeat(250);
        var longH1 = "H".repeat(250);
        var longDescription = "D".repeat(250);
        var html = """
                <!DOCTYPE html>
                <html>
                <head>
                  <meta name="description" content="%s">
                  <title>%s</title>
                </head>
                <body><h1>%s</h1></body>
                </html>
                """.formatted(longDescription, longTitle, longH1);

        mockWebServer.enqueue(new MockResponse().setBody(html).setResponseCode(200));

        JavalinTest.test(app, (server, client) -> {
            var body = client.post(NamedRoutes.urlChecksPath(url.getId())).body().string();
            assertThat(body).contains("T".repeat(200) + "...");
            assertThat(body).contains("H".repeat(200) + "...");
            assertThat(body).contains("D".repeat(200) + "...");
            assertThat(body).doesNotContain("T".repeat(201));
        });
    }
}
