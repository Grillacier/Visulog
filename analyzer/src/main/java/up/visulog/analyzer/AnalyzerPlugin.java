package up.visulog.analyzer;

import java.lang.module.Configuration;
import java.util.Map;

public interface AnalyzerPlugin {
    interface Result {
        String getResultAsString();
        String getResultAsHtmlDiv();
        String getResultAsStringValue();
        String getResultAsStringKey();
    }

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