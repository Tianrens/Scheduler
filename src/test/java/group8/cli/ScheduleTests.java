package group8.cli;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import group8.models.TaskNode;
import group8.parser.DOTDataParser;
import group8.parser.GraphGenerator;
import group8.scheduler.IScheduler;
import group8.scheduler.OneProcessorScheduler;
import group8.scheduler.TopologyFinder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ScheduleTests {



    private AppConfig _config;
    private String[] _cliInput = {"INPUT.dot", "P", "3"};


    //builds AppConfig, required for oneProcessorScheduler,
    // however, NOTHING INITIATED HERE SHOULD EFFECT THE TESTS
    @Before
    public void buildConfig(){
        AppConfigBuilder cli = new AppConfigBuilder(_cliInput);
        try {
             _config = cli.build();
        } catch (CLIException e) {
            e.printStackTrace();
            String getHelp = "java -jar scheduler.jar INPUT.jar P [OPTION]" + System.lineSeparator()
                    + "INPUT.dot    a task graph with integer weights in dot format" + System.lineSeparator()
                    + "P            number of processors to schedule the INPUT graph on" + System.lineSeparator()
                    + "Optional:" + System.lineSeparator()
                    + "-p N         use N cores for execution in parallel(default is sequential)" + System.lineSeparator()
                    + "-v           visualise the search" + System.lineSeparator()
                    + "-o OUTPUT    output file is named OUTPUT(default is INPUT-output.dot)";
            System.out.println(e.getMessage() + System.lineSeparator() + getHelp);
            System.exit(-1);
            fail();
        }
    }

    @Before
    public void buildGraph(){


        List<String> a = new ArrayList<String>();
        a.add("a");
        a.add("3");

        List<String> b = new ArrayList<String>();
        b.add("b");
        b.add("2");

        List<String> c = new ArrayList<String>();
        c.add("c");
        c.add("1");

        List<String> d = new ArrayList<String>();
        d.add("d");
        d.add("3");

        List<String> e = new ArrayList<String>();
        e.add("e");
        e.add("2");

        List<String> f = new ArrayList<String>();
        f.add("f");
        f.add("1");




        GraphGenerator generator = new GraphGenerator(new DOTDataParser());
        generator.addData(a);
        generator.addData(b);
        generator.addData(c);
        generator.addData(d);
        generator.addData(e);
        generator.addData(f);

        Graph graph =


        IScheduler scheduler = new OneProcessorScheduler(new TopologyFinder());
        scheduler.generateValidSchedule()
    }

    @Test
    public void simpleScheduleTest(){
        OneProcessorScheduler

    }

}
