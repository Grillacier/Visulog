/**
 * WIP
 * DO NOT MERGE
 * not sure if this is the right method to generate a file, need advices
 **/

package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountCommitsPerAuthorPlugin implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;

    public CountCommitsPerAuthorPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    static Result processLog(List<Commit> gitLog) {
        var result = new Result();
        for (var commit : gitLog) {
            var nb = result.commitsPerAuthor.getOrDefault(commit.author, 0);
            result.commitsPerAuthor.put(commit.author, nb + 1);
        }
        return result;
    }

    @Override
    public void run() {
        result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()));
    }

    @Override
    public Result getResult() {
        if (result == null) run();
        return result;
    }

    static class Result implements AnalyzerPlugin.Result {
        private final Map<String, Integer> commitsPerAuthor = new HashMap<>();

        Map<String, Integer> getCommitsPerAuthor() {
            return commitsPerAuthor;
        }

        @Override
        public String getResultAsString() {
            return commitsPerAuthor.toString();
        }

        @Override
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div>Commits per author: <ul>");
            for (var item : commitsPerAuthor.entrySet()) {
                html.append("<li>").append(item.getKey()).append(": ").append(item.getValue()).append("</li>");
            }
            html.append("</ul></div>");
            return html.toString();
        }
    }
    
    public void graph(List<Commit> gitLog) {
    	System.out.print("<!DOCTYPE HTML><html><head><script>");
    	System.out.print("window.onload=function(){ var chart=new CanvasJS.Chart(\"chartContainer\",{ animationEnabled: true, theme: \"light1\", title:{ text: \"Commit per author\"},");
    	System.out.print("axisY:{ includeZero: true }, data: [{ type: \"column\", dataPoints: [");
    	foreach (commit.author : gitLog) {
    			System.out.print("{ label: \""+commit.author+"\", y: "+result+"},");
    		}
    	System.out.print("] }] }); chart.render();}");
    	System.out.print("</script></head><body><div id=\"chartContainer\" style=\"height: 370px; width: 100%;\"></div><script src=\"https://canvasjs.com/assets/script/canvasjs.min.js\"></script></body></html>");
    }
    
}
