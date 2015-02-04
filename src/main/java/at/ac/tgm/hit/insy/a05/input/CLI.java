package at.ac.tgm.hit.insy.a05.input;


import at.ac.tgm.hit.insy.a05.output.ExportFactory;
import at.ac.tgm.hit.insy.a05.output.Exportable;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.Messages;

import java.io.*;


/**
 * Pars the command line ars
 *
 * @author Burkhard Hampl [burkhard.hampl@student.tgm.ac.at]
 * @version 0.1
 */
public class CLI {

    @Option(name = "-h", usage = "specify server hostname (default: localhost)")
    private String hostname = "localhost";

    @Option(name = "-u", usage = "specify username for login (default: username of os)")
    private String user = System.getProperty("user.name");

    @Option(name = "-d", usage = "specify database to use (required)", required = true)
    private String databaseName;

    @Option(name = "-o", usage = "specify output file (default: EER: eer.dot, RM: rm.html)")
    private File file;

    @Option(name = "-f", usage = "specify output formant [EER | RM] (required)", required = true)
    private String format;

    @Option(name = "-p", usage = "specify password for login (default: password prompt)")
    private String password;

    private Exportable export;

    /**
     * Pars the command line arguments
     *
     * @param args the command line arguments
     */
    public void parseArgs(String[] args) throws IllegalArgumentException{
        // initialize a new CmdLineParser with CLI
        CmdLineParser parser = new CmdLineParser(this);

        try {
            // pars the args
            parser.parseArgument(args);

            this.export = ExportFactory.chooseExport(this.format);
            if (this.file == null)
                this.file = new File(export.getDefaultFileName());
        } catch (CmdLineException | IllegalArgumentException e) {
            // if something went wrong the help is printed

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            parser.printUsage(out);
            throw new IllegalArgumentException(e.getMessage() + '\n' + "java -jar Backflip.jar [options...] arguments..." + '\n' + out.toString());
        }

        // ask Password with Eclipse/IntelliJ workaround
        if (this.password==null)
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
    public Exportable getFormat() {
        return export;
    }
}
