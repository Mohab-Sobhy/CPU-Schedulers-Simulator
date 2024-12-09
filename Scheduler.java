import java.util.Comparator;
import java.util.List;

public abstract class Scheduler {
    protected List<Process> processes;
    protected int totalProcesses;


    public Scheduler(List<Process> processes) {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        this.processes = processes;
        totalProcesses = processes.size();
    }

    public abstract ProcessorLogs simulate();
}
