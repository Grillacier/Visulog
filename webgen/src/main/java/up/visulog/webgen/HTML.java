package up.visulog.webgen;

import htmlflow.*;

//import java.awt.Desktop;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//import htmlflow.HtmlView;
//import htmlflow.StaticHtml;
//import up.visulog.analyzer.AnalyzerPlugin;
//import up.visulog.analyzer.AnalyzerResult;

public class HTML{

/* I plan to make the following toHTML function write typesafe HTML 
 * It returns a String (through the render() method of its view() method) of the html, head, title, body, and p HTML tags 
 * In the text of the paragraph p, at some point, the getResultAsHtmlDiv() function will be called
 * I plan to make this getResultAsHtmlDiv() function return a String of the div, li, and ul HTML tags for each commit per author 
 * (see how the initial getResultAsHtmlDiv() works in CountCommitsPerAuthorPlugin.java)
 * Thus, for this function, I plan to use a new HmlView/view() method, and return a String through its render() method
 * (see my comments in CountCommitsPerAuthorPlugin.java because I have some issues with getResultAsHtmlDiv())
 * To sum up, the function toHTML returns a String of some HTML tags and a result through its render() method, 
 * and this result calls getResultAsHtmlDiv(), which returns a String of some HTML tags and the commits per author.
 * I hope the reasoning is correct, please tell me if I'm wrong
 */


	public static String resultHTMLFlow(String result, String importscript, String script, String canvasjsobject){
	    HtmlView view = StaticHtml.view(v -> v
	        .html()
	          .head()
	          .script().attrSrc(importscript).__()
              .script().text(script).__()
	            .title().text("Count Commits Per Author").__()
	          .__() //head
	          .body()
              	.p().text(result).__()
              	.text(canvasjsobject)
              .__() //body
	        .__()); //html et fin view
	    String html = view.render();
	    return html;
	  }
	
	
//	// I added an AnalyzerResult type argument to the function so that we can use the function toHTML(AnalyzerResult) (l.49)
//    public void createHtml(String name, AnalyzerResult analyzerResult) {
//    	try {
//        	//Scanner scanner = new Scanner( System.in );
//        	String nom="index";
//        	
//        	List<String> lines = new ArrayList<String>();
//        	for (String line : toHTML(analyzerResult).split(">"))lines.add(line+">");//allows to have a good layout of the document
//        	
//        	if (!name.equals(""))nom=name;//attribute the name to the file
//        	File destination = new File(nom+".html");
//        	
//        	System.out.println("File saved in: "+destination.getAbsoluteFile());
//        	
//        	if(destination.exists() && !destination.isDirectory()){
//        			System.out.println("Do you want to overwrite the old file? (yes/no) [default case : yes]");
//        			//if (scanner.nextLine().equalsIgnoreCase("no")) {
//        			//	scanner.close();
//					//	return;
//					//}
//        	}
//    		FileWriter newfile= new FileWriter(destination.getPath());
//        	for (String string : lines) newfile.write(string);
//    		newfile.close();
//    		System.out.println(destination.toURI());  
//    		
//    		openBrowser(destination);
//    		//scanner.close();
//        }catch(IOException e){
//        	System.out.println(e.getMessage());
//        }
//    }
//    
//    public void openBrowser(File f) {
//    	//Scanner scanner = new Scanner( System.in );
//    	System.out.println("Do you want to open the file in your browser? (yes/no) [default case : yes]");
//		//if (scanner.nextLine().equalsIgnoreCase("no")) {
//		//	scanner.close();
//		//	return;
//		//}
//		try {
//    		Desktop.getDesktop().browse(f.toURI());
//		} catch (Exception e) {
//			System.out.println("No default browser.");
//		}
//		//scanner.close();
//	}
//	
//
//	/* Function to code with htmlFlow
//	 *
//    public String getResultAsHtmlDiv() {
//    	StringBuilder html = new StringBuilder("<div>Commits per author: <ul>");
//        for (var item : commitsPerAuthor.entrySet()) {
//            html.append("<li>").append(item.getKey()).append(": ").append(item.getValue()).append("</li>");
//        }
//        html.append("</ul></div>");
//        return html.toString();
//    }*/

}
