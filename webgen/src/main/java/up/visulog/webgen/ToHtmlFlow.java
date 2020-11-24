package up.visulog.webgen;

import htmlflow.*;


public interface ToHtmlFlow{
	
  default String resultHTMLFlow(String result) {
	  String html= StaticHtml
              .view()
                  .html()
                      .head()
                          .title().text("Commits per author").__()
                      .__() //head
                      .body()
                          .p().text(result).__()
                     .__() //body
                  .__() //html
              .render();
      return html;
  }

/*static HtmlView view = StaticHtml.view(v -> v
            .html()
                .body()
                    .p().text(result).__()
                .__() //body
            .__()); // html
			view
			.setPrintStream(System.out)
			.write(); 
  }*/
}
