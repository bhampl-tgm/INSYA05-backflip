package at.ac.tgm.hit.insy.a05.tests;

import static org.junit.Assert.*;

import at.ac.tgm.hit.insy.a05.input.source.DatabaseConnection;
import at.ac.tgm.hit.insy.a05.input.source.MySQLConnection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public class TestDatabaseMapper {

    private Connection con;

    public void initialize() {
        this.con = Mockito.mock(Connection.class);

    }
}
