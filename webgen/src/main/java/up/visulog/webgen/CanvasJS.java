package up.visulog.webgen;
import java.util.List;

public class CanvasJS {

	public static String createGraph(List<String> label, List<String> value) {
		String people="";
		for (int i = 0; i < label.size(); i++) {
			people+="{ y: "+value.get(i)+", label: \""+label.get(i)+"\" },\n";
		}
		
		String s="window.onload = function() {" +
				"var chart = new CanvasJS.Chart(\"chartContainer\", {" + 
				"	theme: \"light2\", " + 
				"	exportEnabled: true," + 
				"	animationEnabled: true," + 
				"	data: [{" + 
				"		type: \"pie\"," + 
				"		startAngle: 25," + 
				"		toolTipContent: \"<b>{label}</b>: {y}\"," + 
				"		showInLegend: \"true\"," + 
				"		legendText: \"{label}\"," + 
				"		indexLabelFontSize: 16," + 
				"		indexLabel: \"{label} - {y}\"," + 
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
	
}
	
