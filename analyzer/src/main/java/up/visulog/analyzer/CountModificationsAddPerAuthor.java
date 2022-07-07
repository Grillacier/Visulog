package up.visulog.analyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

public class CountModificationsAddPerAuthor implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;
    /**
    *Constructor of the class
    *
    *@param generalConfiguration This parameter will be the current configration
    */
    public CountModificationsAddPerAuthor(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }
    /**
      * Return a result that count the number of modification of commit per author
      *
      *@param gitLog List of commits
      *
      *@return Number of modification of commit per author
      */
    static Result processLog(List<Commit> gitLog) {
        var result = new Result();
        for (var commit : gitLog) {
        	//if (commit.merged()) {
            	var nb = result.modificationPerAuthor.getOrDefault(commit.author, 0);
                result.modificationPerAuthor.put(commit.author, nb + commit.modificationAdd);
			//}
        }
        return result;
    }
    /**
    * Set result with the precessLog's fonction if result is not defined or return the current result
    *
    *@return The actual result
    *
    *@see run
  */
    @Override
	public void run() {
        //System.out.println(result+"aaaaaaaaaaaaaaaaaaa");
        result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()));
	}
    /**
      *Return the actual result or set it
      *
      *@return the actual Result
      */
	@Override
	public Result getResult() {
        if (result == null) run();
        return result;
	}


	public static class Result implements AnalyzerPlugin.Result {
        private final Map<String, Integer> modificationPerAuthor = new HashMap<>();
        /**
      *Getter of modificationsAddPerAuthor
      *
      *@return The current modificationPerAuthor
    */
        public Map<String, Integer> getCommitsPerAuthor() {
            return modificationPerAuthor;
        }
        /**
      *Give the String to modificationPerAuthor author and return it;
      *
      *@return modificationPerAuthor toString return
    */
        @Override
        public String getResultAsString() {
            return modificationPerAuthor.toString();
        }
        /**
       *Create an html division
       *
       *@return html code

     */
        @Override
        public String getResultAsHtmlDiv() {
        	StringBuilder html = new StringBuilder("<h1 style='text-align: center;'> Count Modification Per Author </h1>");
            return html.toString();
        }
        /**
     *return the result composed of value of the hashmap
     *
     *@return list of value of modificationPerAuthor

   */
        @Override
        public String getResultAsStringValue() {
        	String parse="";
            for (var item : modificationPerAuthor.entrySet()) {
            	parse+=item.getValue()+",";
            }
            return parse;
        }
        /**
   *return a list of key as an String of the hashmap modificationPerAthor
   *
   *@return a String composed of keys
 */
        public String getResultAsStringKey() {
        	String parse="";
            for (var item : modificationPerAuthor.entrySet()) {
            	parse+=item.getKey()+",";
            }
            return parse;
        }
    }

}
