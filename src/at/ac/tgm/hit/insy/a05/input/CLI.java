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

    @Option(name = "-o", usage = "specify output file")
    private File file = new File(".");

    @Option(name = "-f", usage = "specify output formant [EER | RM]", required = true)
    private String format;

    private String password;

    /**
     * Pars the command line arguments
     *
     * @param args the command line arguments
     */
    public void parseArg(String[] args) {
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

    /**
     * Getter for property 'hostname'.
     *
     * @return Value for property 'hostname'.
     */
    public String getHostname() {
        return this.hostname;
    }

    /**
     * Getter for property 'user'.
     *
     * @return Value for property 'user'.
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Getter for property 'databaseName'.
     *
     * @return Value for property 'databaseName'.
     */
    public String getDatabaseName() {
        return this.databaseName;
    }

    /**
     * Getter for property 'file'.
     *
     * @return Value for property 'file'.
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Getter for property 'password'.
     *
     * @return Value for property 'password'.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Getter for property 'format'.
     *
     * @return Value for property 'format'.
     */
    public String getFormat() {
        return format;
    }
}
