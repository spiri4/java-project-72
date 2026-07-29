@import hexlet.code.dto.UrlPage
@import hexlet.code.util.NamedRoutes

@param UrlPage page

@template.layout.page(
    page = page,
    content = @`
    <h1>Сайт: ${page.getUrl().getName()}</h1>
    <table class="table table-bordered" data-test="url">
      <tbody>
        <tr>
          <td>ID</td>
          <td>${page.getUrl().getId()}</td>
        </tr>
        <tr>
          <td>Имя</td>
          <td>${page.getUrl().getName()}</td>
        </tr>
        <tr>
          <td>Дата создания</td>
          <td>${page.getUrl().getFormattedCreatedAt()}</td>
        </tr>
      </tbody>
    </table>

    <h2 class="mt-5">Проверки</h2>
    <form method="post" action="${NamedRoutes.urlChecksPath(page.getUrl().getId())}">
      <input type="submit" class="btn btn-primary" value="Запустить проверку">
    </form>

    <table class="table table-bordered mt-3" data-test="checks">
      <thead>
        <tr>
          <th>ID</th>
          <th>Код ответа</th>
          <th>h1</th>
          <th>title</th>
          <th>description</th>
          <th>Дата создания</th>
        </tr>
      </thead>
      <tbody>
      </tbody>
    </table>
    `
)
