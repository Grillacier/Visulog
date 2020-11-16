package up.visulog.webgen;

import htmlflow.HtmlView;
import htmlflow.StaticHtml;

public class HTML{

  public static String page(){
    HtmlView view = StaticHtml.view(v -> v
        .html()
          .head()
            .title().text("HtmlFlow").__()
          .__() //head
          .body()
            .div().attrClass("container")
              .h1().text("My first page with HtmlFlow").__()
              .img().attrSrc("http://bit.ly/2MoHwrU").__()
              .p().text("Typesafe is awesome! :-)").__()
            .__() //div
          .__() //body
        .__()); //html et fin view

    String html = view.render();

    //Desktop.getDesktop().browse(URI.create("fakepage.html"));

    return html;
  }


}
