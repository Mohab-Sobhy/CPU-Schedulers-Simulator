import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String [] args) {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process("P1", 0, 4, 3, "Red"));   // Priority 3
        processes.add(new Process("P2", 2, 2, 2, "Blue"));   // Priority 2
        processes.add(new Process("P3", 20, 5, 1, "Green"));  // Priority 1
        processes.add(new Process("P4", 4, 3, 4, "Yellow")); // Priority 4
        processes.add(new Process("P5", 6, 1, 5, "Purple")); // Priority 5
        PriorityScheduler ps = new PriorityScheduler(processes);

        ProcessorLogs processorLogs = ps.simulate(); //processor logs help us with gui

        SchedulingReportGenerator schedulingReportGenerator = new SchedulingReportGenerator(processes);

        SwingUtilities.invokeLater(() -> {
            SchedulingGUI gui = new SchedulingGUI(processorLogs.getLogs() , processes );
            gui.setVisible(true);
        });

        //System.out.println(processorLogs.toString());
    }
}