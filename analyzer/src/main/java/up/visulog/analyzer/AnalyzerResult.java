package up.visulog.analyzer;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import up.visulog.webgen.CanvasJS;
import up.visulog.webgen.HTML;

public class AnalyzerResult {

	private final List<AnalyzerPlugin.Result> subResults;

	/**
		*The getter of subResults
		*
		*@return the actual subResults
		*/
    public List<AnalyzerPlugin.Result> getSubResults() {
        return subResults;
    }
		/**
		*Setter of subResults
		*
		*@param subResults Set the parameter as the current getSubResults
		*/
    public AnalyzerResult(List<AnalyzerPlugin.Result> subResults) {
        this.subResults = subResults;
    }

    @Override
    public String toString() {
        return subResults.stream().map(AnalyzerPlugin.Result::getResultAsString).reduce("", (acc, cur) -> acc + "\n" + cur);
    }
		/**
			*Return an HTML page code to print the graph
			*/
    public String toHTML() {
    	String temptabValue=subResults.stream().map(AnalyzerPlugin.Result::getResultAsStringValue).reduce("", (acc, cur) -> acc + cur);
    	String temptabKey=subResults.stream().map(AnalyzerPlugin.Result::getResultAsStringKey).reduce("", (acc, cur) -> acc + cur);

    	List<String> value= Arrays.asList(temptabValue.split(","));
    	List<String> label= Arrays.asList(temptabKey.split(","));

    	return HTML.resultHTMLFlow(subResults.stream().map(AnalyzerPlugin.Result::getResultAsHtmlDiv).reduce("", (acc, cur) -> acc + cur)
        		,CanvasJS.importCanvasjs()
        		,CanvasJS.createGraph(label, value)
        		,CanvasJS.importHtmlCanvasjs());
    }
		/** Create a new HTML file
		*
		*@param name The name of the file that will be created
	*/
    public void createHtml(String name) {
        try {
        	String nom="index";

        	List<String> lines = new ArrayList<String>();
        	for (String line : toHTML().split(">"))lines.add(line+">");//allows to have a good layout of the document

        	if (!name.equals(""))nom=name;//attribute the name to the file
        	File destination = new File(nom+".html");

        	System.out.println("File saved in: "+destination.getAbsoluteFile());

    		FileWriter newfile= new FileWriter(destination.getPath());
        	for (String string : lines) newfile.write(string);
    		newfile.close();
    		System.out.println(destination.toURI());

    		openBrowser(destination);
        }catch(IOException e){
        	System.out.println(e.getMessage());
        }
    }
		/** Open a Browser using a file
		*
		*@param f the file of the browser
		*/
    public void openBrowser(File f) {
		try {
    		Desktop.getDesktop().browse(f.toURI());
		} catch (Exception e) {
			System.out.println("No default browser.");
		}

    }

}
