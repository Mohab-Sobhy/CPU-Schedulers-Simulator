import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class Scheduler {
    protected List<Process> processes; // to store processes from user
    protected Queue<String> ProcessesExecutionOrder; // to store name of process that executed in order
    protected int totalProcesses; // help us to know bum of processes
    protected int totalWaitingTime; // help us to calc average waiting time
    protected int totalTurnaroundTime; // help us to calc average turnaround time

    public Scheduler(List<Process> processes) {
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));
        ProcessesExecutionOrder = new LinkedList<>();
        this.processes = processes;
        totalProcesses = processes.size();
        totalWaitingTime = 0;
        totalTurnaroundTime = 0;
    }

    public abstract ProcessorLogs simulate();
}
