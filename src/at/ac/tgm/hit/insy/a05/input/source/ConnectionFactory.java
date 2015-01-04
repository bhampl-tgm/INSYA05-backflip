package at.ac.tgm.hit.insy.a05.input.source;

import java.sql.Connection;

public interface ConnectionFactory {

    public abstract Connection createConnection(String hostname, String database, String username, String password);

}
