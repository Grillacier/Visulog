package up.visulog.analyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

public class CountModificationsDelPerAuthor implements AnalyzerPlugin{
    private final Configuration configuration;
    private Result result;

    public CountModificationsDelPerAuthor(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    static Result processLog(List<Commit> gitLog) {
        var result = new Result();
        for (var commit : gitLog) {
        	//if (commit.merged()) {
            	var nb = result.modificationPerAuthor.getOrDefault(commit.author, 0);
                result.modificationPerAuthor.put(commit.author, nb + commit.modificationDel);
			//}
        }
        return result;
    }

    @Override
	public void run() {
        //System.out.println(result+"aaaaaaaaaaaaaaaaaaa");
        result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()));
	}

	@Override
	public Result getResult() {
        if (result == null) run();
        return result;
	}

	
	public static class Result implements AnalyzerPlugin.Result {
        private final Map<String, Integer> modificationPerAuthor = new HashMap<>();

        public Map<String, Integer> getCommitsPerAuthor() {
            return modificationPerAuthor;
        }

        @Override
        public String getResultAsString() {
            return modificationPerAuthor.toString();
        }
        
        @Override
        public String getResultAsHtmlDiv() {
        	StringBuilder html = new StringBuilder("<h1 style='text-align: center;'> Count Modification Per Author </h1>");
            return html.toString();
        }
        
        @Override
        public String getResultAsStringValue() {
        	String parse="";
            for (var item : modificationPerAuthor.entrySet()) {
            	parse+=item.getValue()+",";
            }
            return parse;
        }
        
        public String getResultAsStringKey() {
        	String parse="";
            for (var item : modificationPerAuthor.entrySet()) {
            	parse+=item.getKey()+",";
            }
            return parse;
        }
    }
}
