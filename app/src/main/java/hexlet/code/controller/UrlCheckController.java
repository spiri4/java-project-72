package hexlet.code.controller;

import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.Unirest;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class UrlCheckController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlCheckController.class);

    public static void create(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Url with id = " + id + " not found"));

        try {
            var response = Unirest.get(url.getName()).asString();
            var statusCode = response.getStatus();

            if (statusCode >= 400) {
                ctx.sessionAttribute("flash", "Произошла ошибка при проверке");
                ctx.sessionAttribute("flashType", "danger");
                ctx.redirect(NamedRoutes.urlPath(url.getId()));
                return;
            }

            var document = Jsoup.parse(response.getBody());
            var title = document.title();
            var h1Element = document.selectFirst("h1");
            var descriptionElement = document.selectFirst("meta[name=description]");

            var h1 = h1Element != null ? h1Element.text() : "";
            var description = descriptionElement != null ? descriptionElement.attr("content") : "";

            var urlCheck = new UrlCheck(statusCode, title, h1, description, url.getId());
            UrlCheckRepository.save(urlCheck);

            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flashType", "success");
        } catch (Exception e) {
            LOGGER.error("Failed to check url {}", url.getName(), e);
            ctx.sessionAttribute("flash", "Произошла ошибка при проверке");
            ctx.sessionAttribute("flashType", "danger");
        }

        ctx.redirect(NamedRoutes.urlPath(url.getId()));
    }
}
