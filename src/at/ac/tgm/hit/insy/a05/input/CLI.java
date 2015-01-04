package at.ac.tgm.hit.insy.a05.input;


import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;


/**
 * Pars the command line ars
 *
 * @author Burkhard Hampl
 * @version 0.1
 */
public class CLI {

    @Option(name = "-h", usage = "specify server hostname")
    private String hostname = "localhost";

    @Option(name = "-u", usage = "specify username for login")
    private String user = System.getProperty("user.name");

    @Option(name = "-d", usage = "specify database to use", required = true)
    private String databaseName;

    @Option(name = "-o", usage = "specify output file with extension", required = true)
    private File file;

    @Option(name = "-l", usage = "specify doc installation location")
    private String dotlocation;

    @Option(name = "-f", usage = "specify output formant [EER | RM]", required = true)
    private String format;

    @Option(name = "-t", usage = "specify output type")
    private String type;

    private String password;

    /**
     * Pars the command line arguments
     *
     * @param args the command line arguments
     */
    public void parsCLI(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);

        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar Backflip.jar [options...] arguments...");
            parser.printUsage(System.err);
            System.err.println();

            System.exit(1);
        }

        // ask Password

        this.password = new String(System.console().readPassword("Enter password:"));
    }

    public String getHostname() {
        return this.hostname;
    }

    public String getUser() {
        return this.user;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public File getFile() {
        return this.file;
    }

    public String getPasswrd() {
        return this.password;
    }

    public String getPassword() {
        return password;
    }

    public String getDotlocation() {
        return dotlocation;
    }

    public String getFormat() {
        return format;
    }

    public String getType() {
        return type;
    }
}
