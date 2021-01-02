package up.visulog.config;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Configuration {

    private final Path gitPath;
    private final Map<String, PluginConfig> plugins;
    /**
    *Constructor of configuration
    *
    *@param gitPath Path of the git that will be printed
    *@param plugins List of plugin that will be added
  */
    public Configuration(Path gitPath, Map<String, PluginConfig> plugins) {
        this.gitPath = gitPath;
        this.plugins = Map.copyOf(plugins);
    }
    /** Getter of getGitPath
    *
    *@return The actual gitPath

  */
    public Path getGitPath() {
        return gitPath;
    }
  /**The getter of plugins
      *
      *@return The actual plugins in this coniguration
      */
    public Map<String, PluginConfig> getPluginConfigs() {
        return plugins;
    }
}
