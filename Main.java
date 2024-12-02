import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String [] args){
        CPUSchedulingGUI csg = new CPUSchedulingGUI(new ProcessorLogs());
        List<Process> processes = new ArrayList<>();
        processes.add(new Process("P1", 0, 4, 3, "Red"));   // Priority 3
        processes.add(new Process("P2", 2, 2, 2, "Blue"));   // Priority 2
        processes.add(new Process("P3", 40, 5, 1, "Green"));  // Priority 1
        processes.add(new Process("P4", 4, 3, 4, "Yellow")); // Priority 4
        processes.add(new Process("P5", 6, 1, 5, "Purple")); // Priority 5
        PriorityScheduler ps = new PriorityScheduler(processes);
        ProcessorLogs processorLogs = ps.simulate();
        System.out.println(processorLogs.toString());
    }
}