import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.print("num of processes: ");
        int numOfProcesses = scanner.nextInt();
        scanner.nextLine();

        for( int i=0; i<numOfProcesses ; i++){
            System.out.print( "Name for process " + (i+1) + " : " );
            String name = scanner.nextLine();
            System.out.print( "Color for process " + (i+1) + " : " );
            String color = scanner.nextLine();
            System.out.print( "Arrival time for process " + (i+1) + " : " );
            int arrivalTime = scanner.nextInt();
            System.out.print( "Burst time for process " + (i+1) + " : " );
            int burstTime = scanner.nextInt();
            System.out.print( "Priority time for process " + (i+1) + " : " );
            int priorityTime = scanner.nextInt();
            scanner.nextLine();
            System.out.println("------------------------------------------");

            processes.add( new Process(name, arrivalTime, burstTime, priorityTime, color) );
        }

//        processes.add(new Process("P1", 0, 17, 4, "Red"));
//        processes.add(new Process("P2", 3, 6, 9, "Blue"));
//        processes.add(new Process("P3", 4, 10, 3, "Green"));
//        processes.add(new Process("P4", 29, 4, 8, "Yellow"));

        Scheduler scheduler = new PriorityScheduler( processes );

        ProcessorLogs processorLogs = scheduler.simulate();

        SchedulingReportGenerator schedulingReportGenerator = new SchedulingReportGenerator(processes);

        SchedulingGUI gui = new SchedulingGUI(processorLogs.getLogs() , processes , schedulingReportGenerator.getAverageWaitingTime() , schedulingReportGenerator.getAverageTurnaroundTime() );

    }
}