package up.visulog.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.io.IOException;

import up.visulog.analyzer.Analyzer;
import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;
import up.visulog.webgen.HTML;

import up.visulog.webgen.CanvasJS;


public class CLILauncher {

	/*list of commands/plugins and their meanings*/
	private static final String CMDList[][]= {
			{"--addPlugin","Allows to analyze the argument put in parameter and creates an instance of PluginConfig.\n"},
			{"--loadConfigFile","Load options from file.\n"},
			{"--justSaveConfigFile","Allows you to save command line options to a file instead of running the scan.\n"},
			{"--loadProject","Choose the project you want visulog to analyze for example: --loadProject=/home/prepro/visulog .\n\n"}
	};
	private static final String PLUGINList[][]= {
			{"countCommits","Allows counting the number of commits made by the user.\n"},
			{"countModificationsAdd","Allows counting the number of modifications added to the project per user.\n"},
			{"countModificationsDel","Allows counting the number of modifications deleted to the project per user.\n"},
			{"countTotal","Allows counting the number of modifications added and deleted to the project per user.\n"}
	};
	
	private static boolean helpCMDUsed=false;
	
    public static void main(String[] args) {
        var config = makeConfigFromCommandLineArgs(args);
        if (config.isPresent() && args.length>0 && args[0].indexOf("help")==-1) {
        	argumentChecking(args);
        	if (!args[0].contains("--loadConfigFile")) {
        		ConfigFile(config, "index");
			}
        } else {
        	helpCMDUsed=true;
        	displayHelpAndExit();
        }
    }

    public static void ConfigFile(Optional<Configuration> config, String name) {
    	var analyzer = new Analyzer(config.get());
        var results = analyzer.computeResults();
		results.createHtml("index"+name);
	}
    
    /**
     * function checking if arguments are correctly entered when launching the project
     * if no, a list of commands is diplayed using another function
     * @see displayHelpAndExit()
     * @param args
     */
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
                //if (parts.length != 2) return Optional.empty();
                //else {
                    String pName = parts[0];
                    String pValue = parts[1];

                    /**
                     * creating the right plugin according to the command written
                     */
                    if (pName.equals(CMDList[0][0])) {
                    	// TODO: parse argument and make an instance of PluginConfig
                        // Let's just trivially do this, before the TODO is fixed:
                        if (pValue.equals("countCommits")) plugins.put("countCommits", new PluginConfig(){});
                       else if (pValue.equals("countModificationsAdd")) plugins.put("countModificationsAdd", new PluginConfig(){});
                       else if (pValue.equals("countModificationsDel")) plugins.put("countModificationsDel", new PluginConfig(){});
                       else if (pValue.equals("countTotal")) plugins.put("countTotal", new PluginConfig(){});
					}else if (pName.equals(CMDList[1][0])) {
						/// Load file to run several cmds
						try {
							File file = new File(pValue);
						    if (file.exists() && file.isFile()) {
						    	
						    	String line="";
						    	BufferedReader buff = new BufferedReader(new FileReader(file));
						    	List<String> list = new ArrayList<String>();
						    	while ((line = buff.readLine()) != null) { 
						    		list.add(line);
						    	}
						    	list.forEach(element -> {
						    		ConfigFile(makeConfigFromCommandLineArgs(new String[] {element}), element);
						    	});
						    	
						    } else {
						         System.out.println("Mauvais chemin");
						         return Optional.empty();
						    }
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("Erreur de fichier");
					         return Optional.empty();
						}
					}
                    /**
                     * saving the paramettters in a new file named savedConfig.txt
                     * or writing in this file if it already exists
                     */
                    else if (pName.equals(CMDList[2][0])) {
                        try {
                            FileWriter fileSave = new FileWriter("savedConfig.txt", true);
                            fileSave.write(args[0].substring(21) + "\n");
                            fileSave.close();
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
					}else if (pName.equals(CMDList[3][0])) {
						File file = new File(pValue);
						if(file.exists() && file.isDirectory()) {
							gitPath=FileSystems.getDefault().getPath(pValue);
							flagGitPath=pValue;
						}
					}else return Optional.empty();
                //}
            } else gitPath = FileSystems.getDefault().getPath(arg);
        }
        System.out.println("Project analysis: "+ (!flagGitPath.equals(".") ? flagGitPath : "Visulog (default)"));//Debug
        return Optional.of(new Configuration(gitPath, plugins));
    }

    /**
     * function displaying all availables commands when the one written is wrong
     */
    private static void displayHelpAndExit() {
        if (!helpCMDUsed) System.out.println("Wrong command... One of your arguments is surely wrong.");
        System.out.println("Here is the list of commands:");//print the list of options/plugin and their syntax
        for (String optionsAndSyntax[] : CMDList)for (String optionsOrSyntax : optionsAndSyntax) System.out.println(optionsOrSyntax);
        System.out.println("Here is the list of plugin:");//print the list of options/plugin and their syntax
        for (String pluginAndSyntax[] : PLUGINList)for (String pluginOrSyntax : pluginAndSyntax) System.out.println(pluginOrSyntax);
        System.exit(0);
    }
}
