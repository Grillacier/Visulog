package up.visulog.analyzer;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AnalyzerResult {
	
	private final List<AnalyzerPlugin.Result> subResults;
	
	
    public List<AnalyzerPlugin.Result> getSubResults() {
        return subResults;
    }

    public AnalyzerResult(List<AnalyzerPlugin.Result> subResults) {
        this.subResults = subResults;
    }

    @Override
    public String toString() {
        return subResults.stream().map(AnalyzerPlugin.Result::getResultAsString).reduce("", (acc, cur) -> acc + "\n" + cur);
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
    }*/
}
