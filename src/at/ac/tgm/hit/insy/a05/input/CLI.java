package at.ac.tgm.hit.insy.a05.input;


import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


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
    public void parseArgs(String[] args) {
        // initialize a new CmdLineParser with CLI
        CmdLineParser parser = new CmdLineParser(this);

        try {
            // pars the args
            parser.parseArgument(args);

            // checks if the order direction is valid
            if (!this.format.equalsIgnoreCase("EER") && !this.format.equalsIgnoreCase("RM"));
                //throw new CmdLineException(parser, Messages.NO_SORT_TYPE, this.format);

        } catch (CmdLineException e) {
            // if something went wrong the help is printed

            System.err.println(e.getMessage());
            System.err.println("java -jar Backflip.jar [options...] arguments...");
            parser.printUsage(System.err);
            System.err.println();

            System.exit(1);
        }

        // ask Password with Eclipse/IntelliJ workaround
        if (System.console() == null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in));
            System.out.print("Enter password:");
            try {
                this.password = reader.readLine();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        } else {
            this.password = new String(System.console().readPassword("Enter password:"));
        }
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
