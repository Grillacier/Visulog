package up.visulog.webgen;


import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;

import java.io.IOException;
import java.nio.file.Path;

//import up.visulog.analyzer.Analyzer;
//import up.visulog.analyzer.AnalyzerPlugin;
//import up.visulog.analyzer.AnalyzerResult;
//import up.visulog.analyzer.CountCommitsPerAuthorPlugin;


public class CanvasJS {

	public static String createGraph(String title, List<String> label, List<String> value) {
		String people="";
		for (int i = 0; i < label.size(); i++) {
			people+="{ y: "+value.get(i)+", label: \""+label.get(i)+"\" },\n";
		}
		
		String s="window.onload = function() {" +
				"var chart = new CanvasJS.Chart(\"chartContainer\", {" + 
				"	theme: \"light2\", " + 
				"	exportEnabled: true," + 
				"	animationEnabled: true," + 
				"	title: {" + 
				"		text: \""+title+"\" " + 
				"	}," + 
				"	data: [{" + 
				"		type: \"pie\"," + 
				"		startAngle: 25," + 
				"		toolTipContent: \"<b>{label}</b>: {y}%\"," + 
				"		showInLegend: \"true\"," + 
				"		legendText: \"{label}\"," + 
				"		indexLabelFontSize: 16," + 
				"		indexLabel: \"{label} - {y}%\"," + 
				"		dataPoints: [" + 
				people + 
				"		]" + 
				"	}]" + 
				"});" + 
				"chart.render();" + 
				"" + 
				"}";
		
		return s;
	}
	
	public static String importCanvasjs() {
		return "https://canvasjs.com/assets/script/canvasjs.min.js";
	}
	
	public static String importHtmlCanvasjs() {
		return "<div id=\"chartContainer\" style=\"height: 370px; width: 100%;\"></div>";
	}
	
//	public String startHTML() {
//    	return "<!DOCTYPE html>\n<html>\n";
//    }
//    
//    public String startBody() {
//    	return "<body>\n";
//    }
//    
//    public String endBody() {
//    	return "</body>\n";
//    }
//    
//    public String chartCanvas() {
//    	return "<div id=\"chartContainer\" style=\"height: 370px; width: 70%;\"></div>\n"
//    			+ "<script src=\"https://canvasjs.com/assets/script/canvasjs.min.js\"></script>\n";
//    }
//
//
//    public String toHTML(AnalyzerResult analyzerResult ) {
//    	return startHTML() + analyzerResult.getSubResults().stream().map(AnalyzerPlugin.Result::getResultAsHtmlDiv).reduce("", (acc, cur) -> acc + cur) + startBody() + chartCanvas() + endBody() + endHTML();
//    }
//    
//    public String endHTML() {
//    	return "</html>";
//    }
	
//	 public void createHtml(String name, AnalyzerResult analyzerResult) {
//	        try {
//	        	Scanner scanner = new Scanner( System.in );
//	        	String nom="index";
//	        	
//	        	List<String> lines = new ArrayList<String>();
//	        	//dans toHTML, mettre un AnalyzerResult en argu
//	        	for (String line : toHTML(analyzerResult).split(">"))lines.add(line+">");//allows to have a good layout of the document
//	        	
//	        	if (!name.equals(""))nom=name;//attributes the name to the file
//	        	File destination = new File(nom+".html");
//	        	
//	        	System.out.println("File saved in: "+destination.getAbsoluteFile());
//	        	
//	        	if(destination.exists() && !destination.isDirectory()){
//	        			System.out.println("Do you want to overwrite the old file? (yes/no) [default case : yes]");
//	        			if (scanner.nextLine().equalsIgnoreCase("no")) {
//	        				scanner.close();
//							return;
//						}
//	        	}
//	    		FileWriter newfile= new FileWriter(destination.getPath());
//	        	for (String string : lines) newfile.write(string);
//	    		newfile.close();
//	    		System.out.println(destination.toURI());  
//	    		
//	    		analyzerResult.openBrowser(destination);
//	    		scanner.close();
//	        }catch(IOException e){
//	        	System.out.println(e.getMessage());
//	        }
//	    }
	

	  
	  
	}
	
