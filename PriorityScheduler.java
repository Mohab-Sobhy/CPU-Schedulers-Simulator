import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PriorityScheduler extends Scheduler {
    private int completedProcessesCount = 0;
    private int scheduledProcessesCount = 0;

    public PriorityScheduler(List<Process> processes) {
        super(processes);
    }

    @Override
    public ProcessorLogs simulate() {

        ProcessorLogs processorLogs = new ProcessorLogs();
        Comparator<Process> priorityComparator = Comparator.comparingInt(Process::getPriority);
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(priorityComparator);



        int currentTime = 0;
        Process currentProcess = null;

        while (completedProcessesCount < processes.size()) {

            for (Process process : readyQueue) {
                process.setWaitingTime( process.getWaitingTime()+1 );
            }

            addNewArrivingProcessesToQueue(currentTime, readyQueue);

            if (currentProcess == null || currentProcess.getCurrentBurstTime() == 0) {
                completedProcessesCount = handleProcessCompletion(currentTime, currentProcess);
                currentProcess = readyQueue.poll();
                completedProcessesCount = handleNewProcessStart(currentTime, currentProcess, processorLogs);
            }

            if (currentProcess != null) {
                executeCurrentProcess(currentTime, currentProcess, processorLogs);
            }

            currentTime++;
        }

        // calc
        for(Process process : processes) {
            process.setTurnaroundTime(process.getBurstTime() + process.getWaitingTime());
            process.setCompletionTime(process.getTurnaroundTime() + process.getArrivalTime());
        }


        return processorLogs;
    }

    private void addNewArrivingProcessesToQueue(int currentTime, PriorityQueue<Process> readyQueue) {
        while (scheduledProcessesCount < processes.size() && processes.get(scheduledProcessesCount).getArrivalTime() <= currentTime) {
            readyQueue.add(processes.get(scheduledProcessesCount));
            scheduledProcessesCount++;
        }
    }

    private int handleProcessCompletion(int currentTime, Process currentProcess) {
        if (currentProcess != null && currentProcess.getCurrentBurstTime() == 0) {
            completedProcessesCount++;
        }
        return completedProcessesCount;
    }

    private int handleNewProcessStart(int currentTime, Process currentProcess, ProcessorLogs processorLogs) {
        if (currentProcess != null) {
            //start new process
        } else {
            processorLogs.addLogUnit( null );
        }
        return completedProcessesCount;
    }

    private void executeCurrentProcess(int currentTime, Process currentProcess , ProcessorLogs processorLogs) {
        processorLogs.addLogUnit( currentProcess );
        currentProcess.setCurrentBurstTime(currentProcess.getCurrentBurstTime() - 1);
    }
}
