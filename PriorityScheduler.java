import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PriorityScheduler extends Scheduler {
    public PriorityScheduler(List<Process> processes) {
        super(processes);
    }

    @Override
    public void schedule() {

        Comparator<Process> priorityComparator = new Comparator<Process>() {
            @Override
            public int compare(Process p1, Process p2) {
                return Integer.compare(p1.getPriority(), p2.getPriority());
            }
        };

        PriorityQueue<Process> priorityQueue = new PriorityQueue<Process>(priorityComparator);


    }
}