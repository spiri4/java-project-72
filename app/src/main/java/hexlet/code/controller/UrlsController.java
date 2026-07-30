package hexlet.code.controller;

import hexlet.code.dto.BasePage;
import hexlet.code.dto.UrlListItem;
import hexlet.code.dto.UrlPage;
import hexlet.code.dto.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;
import hexlet.code.util.UrlNormalizer;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

import static io.javalin.rendering.template.TemplateUtil.model;

public class UrlsController {
    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var items = new ArrayList<UrlListItem>();
        for (var url : urls) {
            var lastCheck = UrlCheckRepository.findLatestByUrlId(url.getId()).orElse(null);
            items.add(new UrlListItem(url, lastCheck));
        }
        var page = new UrlsPage(items);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void create(Context ctx) throws SQLException {
        var rawUrl = ctx.formParam("url");
        URI parsedUrl;
        try {
            parsedUrl = new URI(rawUrl.trim());
            parsedUrl.toURL();
        } catch (Exception e) {
            var page = new BasePage();
            page.setFlash("Некорректный URL");
            page.setFlashType("danger");
            ctx.status(HttpStatus.UNPROCESSABLE_CONTENT);
            ctx.render("index.jte", model("page", page));
            return;
        }

        // Нормализуем URL: только протокол, хост и порт
        var normalizedUrl = UrlNormalizer.normalize(parsedUrl);

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

        var checks = UrlCheckRepository.findByUrlId(id);
        var page = new UrlPage(url, checks);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flashType"));
        ctx.render("urls/show.jte", model("page", page));
    }
}
