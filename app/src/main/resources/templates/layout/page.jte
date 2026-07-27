@import gg.jte.Content
@import hexlet.code.dto.BasePage
@import hexlet.code.util.NamedRoutes

@param BasePage page = null
@param Content content

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Анализатор страниц</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-3">
    <div class="container">
        <a class="navbar-brand" href="${NamedRoutes.rootPath()}">Анализатор страниц</a>
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="${NamedRoutes.rootPath()}">Главная</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${NamedRoutes.urlsPath()}">Сайты</a>
            </li>
        </ul>
    </div>
</nav>
<main class="container">
    @if(page != null && page.getFlash() != null)
    <div class="alert alert-${page.getFlashType()}" role="alert">
        ${page.getFlash()}
    </div>
    @endif
    ${content}
</main>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
