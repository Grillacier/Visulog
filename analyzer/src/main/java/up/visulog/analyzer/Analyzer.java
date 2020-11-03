package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Analyzer implements Runnable {
    private final Configuration config;

    private AnalyzerResult result;

    public Analyzer(Configuration config) {
        this.config = config;
    }

    //creation of a Thread to stock the runnable plugins
    private Thread tousLesPlugins = new Thread();

    private volatile boolean running = true;

    public void arreter() {
        this.running = false;
    }

    //run() method from the Runnable interface
    @Override
    public void run() {
        while (running) {
            //traitement du thread
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public AnalyzerResult computeResults() {
        List<AnalyzerPlugin> plugins = new ArrayList<>();
        for (var pluginConfigEntry: config.getPluginConfigs().entrySet()) {
            var pluginName = pluginConfigEntry.getKey();
            var pluginConfig = pluginConfigEntry.getValue();
            var plugin = makePlugin(pluginName, pluginConfig);
            plugin.ifPresent(plugins::add);
        }
        // run all the plugins
        for (var plugin: plugins) {
            tousLesPlugins = Thread(plugin);
        }
        //start() calls the run() method
        tousLesPlugins.start();
        arreter();

        // store the results together in an AnalyzerResult instance and return it
        return new AnalyzerResult(plugins.stream().map(AnalyzerPlugin::getResult).collect(Collectors.toList()));
    }

    // TODO: find a way so that the list of plugins is not hardcoded in this factory
    private Optional<AnalyzerPlugin> makePlugin(String pluginName, PluginConfig pluginConfig) {
        switch (pluginName) {
            case "countCommits" : return Optional.of(new CountCommitsPerAuthorPlugin(config));
            default : return Optional.empty();
        }
    }

}
