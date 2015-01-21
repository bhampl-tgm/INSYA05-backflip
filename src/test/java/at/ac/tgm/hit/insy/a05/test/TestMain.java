package at.ac.tgm.hit.insy.a05.test;

import at.ac.tgm.hit.insy.a05.Main;
import jdk.nashorn.internal.runtime.ListAdapter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.spi.LoggerContext;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.logging.Logger;

import static at.ac.tgm.hit.insy.a05.Main.main;
import static org.junit.Assert.*;

/**
 * Created by Martin Kritzl on 21.01.2015.
 */
public class TestMain {
//    public InitialLoggerContext init = new InitialLoggerContext("MyTestConfig.xml");
//
//    @Test
//    public void testSomeAwesomeFeature() {
//        final LoggerContext ctx = init.getContext();
//        final Logger logger = init.getLogger("org.apache.logging.log4j.my.awesome.test.logger");
//        final Configuration cfg = init.getConfiguration();
//        final ListAppender app = init.getListAppender("List");
//        logger.warn("Test message");
//        final List<LogEvent> events = app.getEvents();
//    }

    public void test() {
        main(new String[]{"-h", "localhost", "-u", "insy4", "-d", "backflip", "-o", "test.html", "-f", "rm"});

    }
}
