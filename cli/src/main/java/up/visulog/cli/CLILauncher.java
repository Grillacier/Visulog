package up.visulog.cli;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Optional;

import up.visulog.analyzer.Analyzer;
import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;
import up.visulog.webgen.HTML;

import up.visulog.webgen.CanvasJS;


public class CLILauncher {

	/*list of commands and their meanings*/
	private static final String CMDList[][]= {
			{"--addPlugin","Allows to analyze the argument put in parameter and creates an instance of PluginConfig.\n"},
			{"--loadConfigFile","Load options from file.\n"},
			{"--justSaveConfigFile","Allows you to save command line options to a file instead of running the scan.\n"},
			{"--import","Choose the project you want visulog to analyze for example: --import=/home/prepro/visulog"}
	};
	
	private static boolean helpCMDUsed=false;
	
    public static void main(String[] args) {
        var config = makeConfigFromCommandLineArgs(args);
        if (config.isPresent() && args.length>0 && args[0].indexOf("help")==-1) {
        	argumentChecking(args);
        	var analyzer = new Analyzer(config.get());
            var results = analyzer.computeResults();
			results.createHtml("index");
        } else {
        	helpCMDUsed=true;
        	displayHelpAndExit();
        }
    }
    
    public static void argumentChecking(String args[]) {
    	boolean flag=false;
    	for (int i=0; i<args.length; i++) {
        	for(int j=0; j<CMDList.length; j++) if(args[i].indexOf(CMDList[j][0])!=-1) flag=true;
        	if (flag) flag=false;
        	else displayHelpAndExit();
		}
    }

    static Optional<Configuration> makeConfigFromCommandLineArgs(String[] args) {
    	var gitPath=FileSystems.getDefault().getPath(".");
    	String flagGitPath= ".";
    	
        var plugins = new HashMap<String, PluginConfig>();
        for (var arg : args) {
            if (arg.startsWith("--")) {
                String[] parts = arg.split("=");
                if (parts.length != 2) return Optional.empty();
                else {
                    String pName = parts[0];
                    String pValue = parts[1];

                    if (pName.equals(CMDList[0][0])) {
                    	// TODO: parse argument and make an instance of PluginConfig
                        // Let's just trivially do this, before the TODO is fixed:
                        if (pValue.equals("countCommits")) plugins.put("countCommits", new PluginConfig(){});
                        if (pValue.equals("countModificationsAdd")) plugins.put("countModificationsAdd", new PluginConfig(){});
					}else if (pName.equals(CMDList[1][0])) {
						// TODO (load options from a file)
					}else if (pName.equals(CMDList[2][0])) {
						// TODO (save command line options to a file instead of running the analysis)
					}else if (pName.equals(CMDList[3][0])) {
						File file = new File(pValue);
						if(file.exists() && file.isDirectory()) {
							gitPath=FileSystems.getDefault().getPath(pValue);
							flagGitPath=pValue;
						}
					}else return Optional.empty();
                }
            } else gitPath = FileSystems.getDefault().getPath(arg);
        }
        System.out.println("Project analysis: "+ (!flagGitPath.equals(".") ? flagGitPath : "Visulog (default)"));//Debug
        return Optional.of(new Configuration(gitPath, plugins));
    }

    private static void displayHelpAndExit() {
        if (!helpCMDUsed) System.out.println("Wrong command... One of your arguments is surely wrong.");
        System.out.println("Here is the list of commands:");
        //print the list of options and their syntax
        for (String optionsAndSyntax[] : CMDList)for (String optionsOrSyntax : optionsAndSyntax) System.out.println(optionsOrSyntax);
        System.exit(0);
    }
}
