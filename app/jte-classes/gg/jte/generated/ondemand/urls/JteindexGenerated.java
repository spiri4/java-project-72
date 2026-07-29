package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.UrlsPage;
import hexlet.code.util.NamedRoutes;
@SuppressWarnings("unchecked")
public final class JteindexGenerated {
	public static final String JTE_NAME = "urls/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,3,3,3,3,5,5,8,8,20,20,22,22,22,23,23,23,23,23,23,23,23,23,23,23,23,24,24,24,25,25,25,27,27,30,30,30,31,31,31,3,3,3,3};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlsPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, page, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <h1>Сайты</h1>\n    <table class=\"table table-bordered\" data-test=\"urls\">\n      <thead>\n        <tr>\n          <th>ID</th>\n          <th>Имя</th>\n          <th>Дата последней проверки</th>\n          <th>Код ответа</th>\n        </tr>\n      </thead>\n      <tbody>\n        ");
				for (var url : page.getUrls()) {
					jteOutput.writeContent("\n        <tr>\n          <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getId());
					jteOutput.writeContent("</td>\n          <td><a");
					var __jte_html_attribute_0 = NamedRoutes.urlPath(url.getId());
					if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
						jteOutput.writeContent(" href=\"");
						jteOutput.setContext("a", "href");
						jteOutput.writeUserContent(__jte_html_attribute_0);
						jteOutput.setContext("a", null);
						jteOutput.writeContent("\"");
					}
					jteOutput.writeContent(">");
					jteOutput.setContext("a", null);
					jteOutput.writeUserContent(url.getName());
					jteOutput.writeContent("</a></td>\n          <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getFormattedLastCheckCreatedAt());
					jteOutput.writeContent("</td>\n          <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getFormattedLastCheckStatusCode());
					jteOutput.writeContent("</td>\n        </tr>\n        ");
				}
				jteOutput.writeContent("\n      </tbody>\n    </table>\n    ");
			}
		});
		jteOutput.writeContent("\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlsPage page = (UrlsPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
