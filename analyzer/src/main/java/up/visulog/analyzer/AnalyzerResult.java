package up.visulog.analyzer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AnalyzerResult {
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
        return "<html><body>"+subResults.stream().map(AnalyzerPlugin.Result::getResultAsHtmlDiv).reduce("", (acc, cur) -> acc + cur) + "</body></html>";
    }

    public void createHtml(String name) {
    	String tableauChaine = "<html><body>chaine1 chaine2,chaine3,chaine4</body></html>";
        try {
        	String nom="index";
        	List<String> lines = new ArrayList<String>();
        	
        	for (String line : tableauChaine.split(">"))lines.add(line+">");//allows to have a good layout of the document
        	
        	if (!name.equals(""))nom=name;//attribute the name to the file
        	File destination = new File(nom+".html");
        	
        	System.out.println("File saved in: "+destination.getAbsoluteFile());
        	
        	if(destination.exists() && !destination.isDirectory()){
        		try ( Scanner scanner = new Scanner( System.in ) ){
        			System.out.println("Do you want to overwrite the old file? (yes/no) [default case : yes]");
        			if (scanner.nextLine().equalsIgnoreCase("no"))return;
        		}
        	}
        	FileWriter newfile= new FileWriter(destination.getPath());
        	for (String string : lines) newfile.write(string);
    		newfile.close();
        }catch(IOException e){
        	System.out.println(e.getMessage());
        }
    }
    
//    public static void main(String[] args) {
//    	AnalyzerResult a=new AnalyzerResult(null);
//    	a.createHtml("cwilliam");
//    	
//    }
}
