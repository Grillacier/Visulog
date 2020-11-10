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
        
        public String getResultAsHtmlDiv() {
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
        	for (var item : commitsPerAuthor.entrySet()) {
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

      /*  @Override
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<div>Commits per author: <ul>");
            for (var item : commitsPerAuthor.entrySet()) {
                html.append("<li>").append(item.getKey()).append(": ").append(item.getValue()).append("</li>");
            }
            html.append("</ul></div>");
            return html.toString();
        }*/
    }
}
