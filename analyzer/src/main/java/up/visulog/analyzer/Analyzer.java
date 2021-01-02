package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.lang.*;

public class Analyzer {
    private final Configuration config;

    private AnalyzerResult result;
    /**
    *Constructor of  Analyzer
    *
    *@param config Set the config with this one
  */
    public Analyzer(Configuration config) {
        this.config = config;
    }

    /**
      * Create an AnalyzerResult with different plugin
      *
      *@return result of a search of plugins
      */
    public AnalyzerResult computeResults() {
        List<AnalyzerPlugin> plugins = new ArrayList<>();
        for (var pluginConfigEntry: config.getPluginConfigs().entrySet()) {
            var pluginName = pluginConfigEntry.getKey();
            var pluginConfig = pluginConfigEntry.getValue();
            var plugin = makePlugin(pluginName, pluginConfig);
            plugin.ifPresent(plugins::add);
        }
        // run all the plugins
        // TODO: try running them in parallel
        for (var plugin: plugins) plugin.run();
        for (var plugin: plugins) {
            Thread t = new Thread(plugin);
            t.start();
        }

        // store the results together in an AnalyzerResult instance and return it
        return new AnalyzerResult(plugins.stream().map(AnalyzerPlugin::getResult).collect(Collectors.toList()));
    }


  // TODO: find a way so that the list of plugins is not hardcoded in this factory
  /**
  *Return a plugin depending of the parametre
  *
  *@param pluginName The name of the pluginName
  *@param pluginConfig The configuration of the pluginName
  *
  *@return The plugin that match with the name

  */
    private Optional<AnalyzerPlugin> makePlugin(String pluginName, PluginConfig pluginConfig) {
        switch (pluginName) {
            case "countCommits" : return Optional.of(new CountCommitsPerAuthorPlugin(config));
            case "countModificationsAdd" : return Optional.of(new CountModificationsAddPerAuthor(config));
            case "countModificationsDel" : return Optional.of(new CountModificationsDelPerAuthor(config));
            case "countTotal" : return Optional.of(new CountTotal(config));
            default : return Optional.empty();

        }
    }

}
