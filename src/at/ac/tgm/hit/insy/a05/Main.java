package at.ac.tgm.hit.insy.a05;

import at.ac.tgm.hit.insy.a05.input.CLI;
import at.ac.tgm.hit.insy.a05.input.output.Exportable;
import at.ac.tgm.hit.insy.a05.input.source.ConnectionFactory;
import at.ac.tgm.hit.insy.a05.input.source.DatabaseMapper;

public class Main {

    private static final CLI CLI = new CLI();

    private ConnectionFactory connectionFactory;

    private DatabaseMapper databaseMapper;

    private Exportable exportable;

    public static void main(String[] args) {
        Main.CLI.parsCLI(args);
    }

}
