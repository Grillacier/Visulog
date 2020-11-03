/**
 * WIP
 * DO NOT MERGE
 * not sure if this is the right method to generate a file, need advices
 **/

package up.visulog.analyzer;

import java.util.List;

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
    	static HtmlView view = StaticHtml.view(v -> v
            .html()
            	.body()
            		.p().text(subResults.stream().map(AnalyzerPlugin.Result::getResultAsHtmlDiv).reduce("", (acc, cur) -> acc + cur)).__()
            	.__() //body
            .__()); // html
    	String html = view.render(); 
    	return html;
    }

    public void createHtml(String name) {
        if (name.equals(""))
            File destination = new File("/visulog/index.html");
        else
            File destination = new File("/visulog/"+name+".html");
        try {
            Files.write(toHTML(), destination, Charset.forName("UTF-8"));
        } catch (IOException e) {}
    }
}
