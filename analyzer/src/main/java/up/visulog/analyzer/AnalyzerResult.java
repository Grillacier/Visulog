package up.visulog.analyzer;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import up.visulog.webgen.ToHtmlFlow;

public class AnalyzerResult implements ToHtmlFlow {
    public List<AnalyzerPlugin.Result> getSubResults() {
        return subResults;
    }

    private final List<AnalyzerPlugin.Result> subResults;

    public AnalyzerResult(List<AnalyzerPlugin.Result> subResults) {
        this.subResults = subResults;
    }

    @Override
    public String toString() {
        return subResults.stream().map(AnalyzerPlugin.Result::getResultAsString).reduce("", (acc, cur) -> acc + "\n" + cur);
    }

    public String toHTML() {
        return resultHtmlFlow(subResults.stream().map(AnalyzerPlugin.Result::getResultAsHtmlDiv).reduce("", (acc, cur) -> acc + cur));
    }

    public void createHtml(String name) {
        try {
        	Scanner scanner = new Scanner( System.in );
        	String nom="index";
        	
        	List<String> lines = new ArrayList<String>();
        	for (String line : toHTML().split(">"))lines.add(line+">");//allows to have a good layout of the document
        	
        	if (!name.equals(""))nom=name;//attribute the name to the file
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
    		
    		openBrowser(destination);
    		scanner.close();
        }catch(IOException e){
        	System.out.println(e.getMessage());
        }
    }
    
    public void openBrowser(File f) {
    	Scanner scanner = new Scanner( System.in );
    	System.out.println("Do you want to open the file in your browser? (yes/no) [default case : yes]");
		if (scanner.nextLine().equalsIgnoreCase("no")) {
			scanner.close();
			return;
		}
		try {
    		Desktop.getDesktop().browse(f.toURI());
		} catch (Exception e) {
			System.out.println("No default browser.");
		}
		scanner.close();
    }
}

