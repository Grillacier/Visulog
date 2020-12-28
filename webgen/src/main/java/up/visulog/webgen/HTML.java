package up.visulog.webgen;

import htmlflow.*;

public class HTML{


	public static String resultHTMLFlow(String result, String importscript, String script, String canvasjsobject){
	    HtmlView view = StaticHtml.view(v -> v
	        .html()
	          .head()
	          .script().attrSrc(importscript).__()
              .script().text(script).__()
	            .title().text("Count Commits Per Author").__()
	          .__() //head
	          .body()
              	.p()
              	.text(result).__()
              	.text(canvasjsobject)
              .__() //body
	        .__()); //html et fin view
	    String html = view.render();
	    return html;
	  }

}
