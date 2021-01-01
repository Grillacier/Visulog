package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CountCommitsPerAuthorPlugin implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;


    /**
          * Contructor of class CountCommitsPerAuthorPlugin
          *
          *@param generalConfiguration Set the attribute configuration

    */
    public CountCommitsPerAuthorPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    /**
      * Give the number of commit per author as an Result
      *
      *@param gitLog The list of commits per Author
      *
      *@return The result of number of commit

    */
    static Result processLog(List<Commit> gitLog) {
        var result = new Result();
        for (var commit : gitLog) {
            var nb = result.commitsPerAuthor.getOrDefault(commit.author, 0);
            result.commitsPerAuthor.put(commit.author, nb + 1);
        }
        return result;
    }

    @Override
    /**
    *Set the current result
    *
    *@see processLog
    */
    public void run() {
        result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()));
    }

    @Override
    /**
      * Set result with the precessLog's fonction if result is not defined or return the current result
      *
      *@return The actual result
      *
      *@see run
    */
    public Result getResult() {
        if (result == null) run();
        return result;
    }


	public static class Result implements AnalyzerPlugin.Result {
        private final Map<String, Integer> commitsPerAuthor = new HashMap<>();

        /**
          * Getter of commitPerAuthor
          *
          *@return The current commitsPerAuthor

        */
        public Map<String, Integer> getCommitsPerAuthor() {
            return commitsPerAuthor;
        }

        /**
          *Give the String to commit per author and return it;
          *
          *@return commitsPerAuthor toString return
        */
        @Override
        public String getResultAsString() {
            return commitsPerAuthor.toString();
        }

        /**
         *Create an html division
         *
         *@return html code

       */
        @Override
        public String getResultAsHtmlDiv() {
        	StringBuilder html = new StringBuilder("<h1 style='text-align: center;'> Count Commits Per Author </h1>");
            return html.toString();
        }

        /**
         *return the result composed of value of the hashmap commitPerAuthor
         *
         *@return list of value of countCommitPerAuthor

       */
        @Override
        public String getResultAsStringValue() {
        	String parse="";
            for (var item : commitsPerAuthor.entrySet()) {
            	parse+=item.getValue()+",";
            }
            return parse;
        }

        /**
       *return a list of key of the hashmap commitPerAuthor
       *
       *@return list of key of countCommitPerAuhtor
     */
        public String getResultAsStringKey() {
        	String parse="";
            for (var item : commitsPerAuthor.entrySet()) {
            	parse+=item.getKey()+",";
            }
            return parse;
        }
    }
}
