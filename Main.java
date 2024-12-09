import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process("P1", 0, 17, 4, "Red"));
        processes.add(new Process("P2", 3, 6, 9, "Blue"));
        processes.add(new Process("P3", 4, 10, 3, "Green"));
        processes.add(new Process("P4", 29, 4, 8, "Yellow"));

        FCAIScheduler scheduler = new FCAIScheduler(processes , 1);
        ProcessorLogs processorLogs = scheduler.simulate();

        SchedulingReportGenerator schedulingReportGenerator = new SchedulingReportGenerator(processes);

        SchedulingGUI gui = new SchedulingGUI(processorLogs.getLogs() , processes );

        //System.out.println(processorLogs.toString());
    }
}