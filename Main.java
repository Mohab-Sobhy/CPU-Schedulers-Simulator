import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String [] args) {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process("P1", 0, 5, 3, "cyan"));
        processes.add(new Process("P2", 1, 2, 2, "orange"));
        processes.add(new Process("P3", 3, 2, 1, "purple"));

        SRTF ps = new SRTF(processes);

        ProcessorLogs processorLogs = ps.simulate(); //processor logs help us with gui

        SchedulingReportGenerator schedulingReportGenerator = new SchedulingReportGenerator(processes);

        SchedulingGUI gui = new SchedulingGUI(processorLogs.getLogs() , processes );

        //System.out.println(processorLogs.toString());
    }
}