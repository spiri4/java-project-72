@import hexlet.code.dto.UrlsPage
@import hexlet.code.util.NamedRoutes

@param UrlsPage page

@template.layout.page(
    page = page,
    content = @`
    <h1>Сайты</h1>
    <table class="table table-bordered" data-test="urls">
      <thead>
        <tr>
          <th>ID</th>
          <th>Имя</th>
          <th>Последняя проверка</th>
          <th>Код ответа</th>
        </tr>
      </thead>
      <tbody>
        @for(var item : page.getUrls())
        <tr>
          <td>${item.getUrl().getId()}</td>
          <td><a href="${NamedRoutes.urlPath(item.getUrl().getId())}">${item.getUrl().getName()}</a></td>
          <td>${item.getFormattedLastCheckCreatedAt()}</td>
          <td>${item.getFormattedLastCheckStatusCode()}</td>
        </tr>
        @endfor
      </tbody>
    </table>
    `
)
