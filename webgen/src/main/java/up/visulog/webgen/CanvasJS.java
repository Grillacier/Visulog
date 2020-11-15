package up.visulog.webgen;


import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import up.visulog.analyzer.Analyzer;
import up.visulog.analyzer.AnalyzerPlugin;
import up.visulog.analyzer.AnalyzerResult;


public class CanvasJS {
	
	public String startHTML() {
    	return "<!DOCTYPE html>\n<html>\n";
    }
    
    public String startBody() {
    	return "<body>\n";
    }
    
    public String endBody() {
    	return "</body>\n";
    }
    
    public String chartCanvas() {
    	return "<div id=\"chartContainer\" style=\"height: 370px; width: 70%;\"></div>\n"
    			+ "<script src=\"https://canvasjs.com/assets/script/canvasjs.min.js\"></script>\n";
    }


    public String toHTML() {
  
    	return startHTML() + subResults.stream().map(AnalyzerPlugin.Result::getResultAsHtmlDiv).reduce("", (acc, cur) -> acc + cur) + startBody() + chartCanvas() + endBody() + endHTML();
    }
    
    public String endHTML() {
    	return "</html>";
    }
	
	 public void createHtml(String name) {
	        try {
	        	Scanner scanner = new Scanner( System.in );
	        	String nom="index";
	        	
	        	List<String> lines = new ArrayList<String>();
	        	for (String line : toHTML().split(">"))lines.add(line+">");//allows to have a good layout of the document
	        	
	        	if (!name.equals(""))nom=name;//attributes the name to the file
	        	File destination = new File(nom+".html");
	        	
	        	System.out.println("File saved in: "+destination.getAbsoluteFile());
	        	
	        	if(destination.exists() && !destination.isDirectory()){
	        			System.out.println("Do you want to overwrite the old file? (yes/no) [default case : yes]");
	        			if (scanner.nextLine().equalsIgnoreCase("no")) {
	        				scanner.close();
							return;
						}
	        	}
	    		FileWriter newfile= new FileWriter(destination.getPath());
	        	for (String string : lines) newfile.write(string);
	    		newfile.close();
	    		System.out.println(destination.toURI());  
	    		
	    		AnalyzerResult listResult = new AnalyzerResult (List<AnalyzerPlugin.Result>);
	    		var subResults = listResult.getSubResults();
	    		subResults.openBrowser(destination);
	    		scanner.close();
	        }catch(IOException e){
	        	System.out.println(e.getMessage());
	        }
	    }
	

	  public String getResultAsHtmlDiv()  {
      	StringBuilder script = new StringBuilder("<head>\n<script>\n window.onload = function () {\n"
      			+"var chart = new CanvasJS.Chart(\"chartContainer\", {\n"
      			+ "animationEnabled: true,\n"
      			+ "title: {\n"
      			+ "text:\"Commits per author\"\n"
      			+ "},\n"
      			+ "axisX:{\n"
      			+ "interval: 1\n"
      			+ "},\n"
      			+ "axisY2:{\n"
      			+ "interlacedColor: \"rgba(1,77,101,.2)\",\n"
      			+ "gridColor: \"rgba(1,77,101,.1)\",\n"
      			+ "title: \"Number of Commits\"\n"
      			+ "},\n"
      			+ "data: [{\n"
      			+ "type: \"bar\",\n"
      			+ "name: \"author\",\n"
      			+ "axisYType: \"secondary\",\n"
      			+ "color: \"#014D65\",\n"
      			+ "dataPoints: [\n");
      	for (var item : .getCommitsPerAuthor().entrySet()) {
      		script.append("{ y: ").append(item.getValue()).append(", label: \"").append(item.getKey()).append("\"},\n");
      	}
      	script.append("{ y: 0, label: \"Author\"}\n"
      			+ "]\n"
      			+ "}]\n"
      			+ "});\n"
      			+ "chart.render();\n"
      			+ "}\n"
      			+ "</script>\n</head>\n");
      	return script.toString();
      }
	
	}
