import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SRTF extends Scheduler {
    private int completedProcessesCount = 0;
    private int scheduledProcessesCount = 0;

    public SRTF(List<Process> processes) {
        super(processes);
    }

    @Override
    public ProcessorLogs simulate() {

        ProcessorLogs processorLogs = new ProcessorLogs();

        // Comparator for shortest remaining burst time, starvation solved by aging
        Comparator<Process> priorityComparator = Comparator.comparingInt(Process::age);
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(priorityComparator);

        int currentTime = 0;
        Process currentProcess = null;

        // Continue until all processes are completed
        while (completedProcessesCount < processes.size()) {

            // Increment waiting time for all processes in the ready queue
            for (Process process : readyQueue) {
                process.setWaitingTime(process.getWaitingTime() + 1);
            }

            // Add newly arrived processes to the ready queue
            addNewArrivingProcessesToQueue(currentTime, readyQueue);

            PriorityQueue<Process> updatedQueue = new PriorityQueue<>(Comparator.comparingInt(Process::age));
            updatedQueue.addAll(readyQueue);
            readyQueue = updatedQueue;

            // Check for preemption
            if (currentProcess != null && !readyQueue.isEmpty() &&
                    currentProcess.getCurrentBurstTime() > readyQueue.peek().getCurrentBurstTime()) {
                readyQueue.add(currentProcess); // Preempt the current process
                currentProcess = readyQueue.poll(); // Get the new shortest remaining time process
            }

            // If there's no current process, pick one from the queue
            if (currentProcess == null) {
                currentProcess = readyQueue.poll();
            }

            // Execute the current process if available
            if (currentProcess != null) {
                executeCurrentProcess(currentTime, currentProcess, processorLogs);

                // If the current process finishes, update the completed count
                if (currentProcess.getCurrentBurstTime() == 0) {
                    completedProcessesCount++;
                    currentProcess = null;
                }
            } else {
                // Log idle time
                processorLogs.addLogUnit(null);
            }

            // Increment the time
            currentTime++;
        }

        // Calculate turnaround time and completion time for each process
        for (Process process : processes) {
            process.setTurnaroundTime(process.getBurstTime() + process.getWaitingTime());
            process.setCompletionTime(process.getTurnaroundTime() + process.getArrivalTime());
        }

        return processorLogs;
    }

    // Add processes that have arrived at the current time to the ready queue
    private void addNewArrivingProcessesToQueue(int currentTime, PriorityQueue<Process> readyQueue) {
        while (scheduledProcessesCount < processes.size() &&
                processes.get(scheduledProcessesCount).getArrivalTime() <= currentTime) {
            readyQueue.add(processes.get(scheduledProcessesCount));
            scheduledProcessesCount++;
        }
    }

    // Execute the current process
    private void executeCurrentProcess(int currentTime, Process currentProcess, ProcessorLogs processorLogs) {
        processorLogs.addLogUnit(currentProcess);
        currentProcess.setCurrentBurstTime(currentProcess.getCurrentBurstTime() - 1);
    }
}
