import java.util.Comparator;
import java.util.List;
import java.lang.Math;

public class SchedulingReportGenerator {

    SchedulingReportGenerator(List<Process> processes){
        processes.sort(Comparator.comparingInt(Process::getCompletionTime));

        float totalWaitingTime = 0;
        float totalTurnaroundTime = 0;

        System.out.println("Processes execution in order: ");
        for(Process process : processes){
            System.out.println(process.getName() + " -> Waiting Time: " + process.getWaitingTime() + " -> Turnaround Time: " + process.getTurnaroundTime());
            totalWaitingTime += process.getWaitingTime();
            totalTurnaroundTime += process.getTurnaroundTime();
        }

        float averageWaitingTime = totalWaitingTime / processes.size();
        float averageTurnaroundTime = totalTurnaroundTime / processes.size();

        System.out.println( "Average Waiting Time: " + Math.ceil(averageWaitingTime) );
        System.out.println( "Average Turnaround Time: " + Math.ceil(averageTurnaroundTime) );

    }

}
