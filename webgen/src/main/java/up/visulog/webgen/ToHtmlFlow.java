package up.visulog.webgen;
import htmlflow.*;
import htmlflow.util.*;
import htmlflow.flowifier.*;

public interface ToHtmlFlow {

	default String resultHtmlFlow (String results) {
		String html= StaticHtml
                .view()
                    .html()
                        .head()
                            .title().text("Commits per author").__()
                        .__() //head
                        .body()
                            .p().text(results).__()
                       .__() //body
                    .__() //html
                .render();
        return html;
	}
	
	
}
