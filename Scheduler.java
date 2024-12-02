import java.util.List;

public abstract class Scheduler {
    protected List<Process> processes;
    protected int totalProcesses;

    public Scheduler(List<Process> processes) {
        this.processes = processes;
        totalProcesses = processes.size();
    }

    public abstract void schedule();
}
