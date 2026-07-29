package hexlet.code.controller;

import hexlet.code.dto.BasePage;
import hexlet.code.dto.UrlPage;
import hexlet.code.dto.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import hexlet.code.util.UrlNormalizer;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlsController.class);

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var page = new UrlsPage(urls);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        var rawUrl = ctx.formParam("url");
        String normalizedUrl;

        try {
            if (rawUrl == null || rawUrl.isBlank()) {
                throw new IllegalArgumentException("URL is empty");
            }
            normalizedUrl = UrlNormalizer.normalize(rawUrl);
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Invalid URL: {}", rawUrl, e);
            var page = new BasePage();
            page.setFlash("Некорректный URL");
            page.setFlashType("danger");
            ctx.status(422);
            ctx.render("index.jte", model("page", page));
            return;
        }

        var existingUrl = UrlRepository.findByName(normalizedUrl);
        if (existingUrl.isPresent()) {
            ctx.sessionAttribute("flash", "Страница уже существует");
            ctx.sessionAttribute("flashType", "info");
            ctx.redirect(NamedRoutes.urlPath(existingUrl.get().getId()));
            return;
        }

        var url = new Url(normalizedUrl);
        UrlRepository.save(url);

        ctx.sessionAttribute("flash", "Страница успешно добавлена");
        ctx.sessionAttribute("flashType", "success");
        ctx.redirect(NamedRoutes.urlPath(url.getId()));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Url with id = " + id + " not found"));

        var page = new UrlPage(url);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("urls/show.jte", model("page", page));
    }
}
