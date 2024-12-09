import java.util.Comparator;
import java.util.List;
import java.lang.Math;

public class SchedulingReportGenerator {
    private double averageWaitingTime;
    private double averageTurnaroundTime;

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

        averageWaitingTime = Math.ceil( totalWaitingTime / processes.size() );
        averageTurnaroundTime = Math.ceil( totalTurnaroundTime / processes.size() );

        System.out.println( "Average Waiting Time: " + getAverageWaitingTime() );
        System.out.println( "Average Turnaround Time: " + getAverageTurnaroundTime() );

    }

    int getAverageWaitingTime(){
        return (int)averageWaitingTime;
    }

    int getAverageTurnaroundTime(){
        return (int)averageTurnaroundTime;
    }

}
