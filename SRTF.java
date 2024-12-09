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

        Comparator<Process> priorityComparator = Comparator.comparingInt(Process::age);
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(priorityComparator);

        int currentTime = 0;
        Process currentProcess = null;

        while (completedProcessesCount < processes.size()) {

            for (Process process : readyQueue) {
                process.setWaitingTime(process.getWaitingTime() + 1);
            }

            addNewArrivingProcessesToQueue(currentTime, readyQueue);

            PriorityQueue<Process> updatedQueue = new PriorityQueue<>(Comparator.comparingInt(Process::age));
            updatedQueue.addAll(readyQueue);
            readyQueue = updatedQueue;

            if (currentProcess != null && !readyQueue.isEmpty() &&
                    currentProcess.getCurrentBurstTime() > readyQueue.peek().getCurrentBurstTime()) {
                readyQueue.add(currentProcess); // Preempt the current process
                currentProcess = readyQueue.poll(); // Get the new shortest remaining time process
            }

            if (currentProcess == null) {
                currentProcess = readyQueue.poll();
            }

            if (currentProcess != null) {
                executeCurrentProcess(currentTime, currentProcess, processorLogs);

                if (currentProcess.getCurrentBurstTime() == 0) {
                    completedProcessesCount++;
                    currentProcess = null;
                }
            } else {
                processorLogs.addLogUnit(null);
            }

            currentTime++;
        }

        for (Process process : processes) {
            process.setTurnaroundTime(process.getBurstTime() + process.getWaitingTime());
            process.setCompletionTime(process.getTurnaroundTime() + process.getArrivalTime());
        }

        return processorLogs;
    }

    private void addNewArrivingProcessesToQueue(int currentTime, PriorityQueue<Process> readyQueue) {
        while (scheduledProcessesCount < processes.size() &&
                processes.get(scheduledProcessesCount).getArrivalTime() <= currentTime) {
            readyQueue.add(processes.get(scheduledProcessesCount));
            scheduledProcessesCount++;
        }
    }

    private void executeCurrentProcess(int currentTime, Process currentProcess, ProcessorLogs processorLogs) {
        processorLogs.addLogUnit(currentProcess);
        currentProcess.setCurrentBurstTime(currentProcess.getCurrentBurstTime() - 1);
    }
}
