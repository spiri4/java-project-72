package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.UrlPage;
import hexlet.code.util.NamedRoutes;
@SuppressWarnings("unchecked")
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,3,3,3,3,5,5,8,8,9,9,9,14,14,14,18,18,18,22,22,22,28,28,28,28,28,28,28,28,28,44,44,46,46,46,47,47,47,48,48,48,49,49,49,50,50,50,51,51,51,53,53,56,56,56,57,57,57,3,3,3,3};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, page, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <h1>Сайт: ");
				jteOutput.setContext("h1", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</h1>\n    <table class=\"table table-bordered\" data-test=\"url\">\n      <tbody>\n        <tr>\n          <td>ID</td>\n          <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.writeContent("</td>\n        </tr>\n        <tr>\n          <td>Имя</td>\n          <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</td>\n        </tr>\n        <tr>\n          <td>Дата создания</td>\n          <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getFormattedCreatedAt());
				jteOutput.writeContent("</td>\n        </tr>\n      </tbody>\n    </table>\n\n    <h2 class=\"mt-5\">Проверки</h2>\n    <form method=\"post\"");
				var __jte_html_attribute_0 = NamedRoutes.urlChecksPath(page.getUrl().getId());
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
					jteOutput.writeContent(" action=\"");
					jteOutput.setContext("form", "action");
					jteOutput.writeUserContent(__jte_html_attribute_0);
					jteOutput.setContext("form", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(">\n      <input type=\"submit\" class=\"btn btn-primary\" value=\"Запустить проверку\">\n    </form>\n\n    <table class=\"table table-bordered mt-3\" data-test=\"checks\">\n      <thead>\n        <tr>\n          <th>ID</th>\n          <th>Код ответа</th>\n          <th>h1</th>\n          <th>title</th>\n          <th>description</th>\n          <th>Дата создания</th>\n        </tr>\n      </thead>\n      <tbody>\n        ");
				for (var check : page.getChecks()) {
					jteOutput.writeContent("\n        <tr>\n          <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(check.getId());
					jteOutput.writeContent("</td>\n          <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(check.getStatusCode());
					jteOutput.writeContent("</td>\n          <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(check.getTruncatedH1());
					jteOutput.writeContent("</td>\n          <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(check.getTruncatedTitle());
					jteOutput.writeContent("</td>\n          <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(check.getTruncatedDescription());
					jteOutput.writeContent("</td>\n          <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(check.getFormattedCreatedAt());
					jteOutput.writeContent("</td>\n        </tr>\n        ");
				}
				jteOutput.writeContent("\n      </tbody>\n    </table>\n    ");
			}
		});
		jteOutput.writeContent("\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlPage page = (UrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
