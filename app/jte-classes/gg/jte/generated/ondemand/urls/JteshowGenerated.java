package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.UrlPage;
@SuppressWarnings("unchecked")
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,2,2,2,4,4,7,7,8,8,8,13,13,13,17,17,17,21,21,21,25,25,25,26,26,26,2,2,2,2};
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
				jteOutput.writeContent("</td>\n        </tr>\n      </tbody>\n    </table>\n    ");
			}
		});
		jteOutput.writeContent("\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlPage page = (UrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
