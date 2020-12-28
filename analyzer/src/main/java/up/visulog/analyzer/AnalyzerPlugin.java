package up.visulog.analyzer;

import java.lang.module.Configuration;

public interface AnalyzerPlugin extends Runnable {
    interface Result {
        String getResultAsString();
        String getResultAsHtmlDiv();
        String getResultAsStringValue();
        String getResultAsStringKey();
    }
    
    public static final String name = "";

    /**
     * run this analyzer plugin
     */
    void run();

    /**
     *
     * @return the result of this analysis. Runs the analysis first if not already done.
     */
    Result getResult();
}