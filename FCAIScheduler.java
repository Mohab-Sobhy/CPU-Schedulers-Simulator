import java.util.*;

public class FCAIScheduler extends Scheduler {
    private int completedProcessesCount = 0;
    private int scheduledProcessesCount = 0;
    private double V1;
    private double V2;
    private final int contextSwitchingTime; // Context switching time
    private final Map<Process, Integer> executedTimeMap = new HashMap<>();

    public FCAIScheduler(List<Process> processes) {
        super(processes);
        this.contextSwitchingTime = 0; // Initialize context switching time
        calculateV1AndV2();
        for (Process process : processes) {
            executedTimeMap.put(process, 0); // Initialize executed time map
        }
    }

    public void calculateV1AndV2() {
        int lastArrivalTime = 0;
        int maxBurstTime = 0;

        lastArrivalTime = processes.getLast().getArrivalTime();
        for (Process p : processes) {
            if (p.getBurstTime() > maxBurstTime) {
                maxBurstTime = p.getBurstTime();
            }
        }

        V1 = (double) lastArrivalTime / 10.0;
        V2 = (double) maxBurstTime / 10.0;
    }

    public double calculateFCAIFactor(Process p) {
        return (10 - p.getPriority())
                + (p.getArrivalTime() / V1)
                + (p.getCurrentBurstTime() / V2);
    }

    @Override
    public ProcessorLogs simulate() {
        ProcessorLogs processorLogs = new ProcessorLogs();

        PriorityQueue<Process> readyQueue = new PriorityQueue<>(
                Comparator.comparingDouble(this::calculateFCAIFactor));

        int currentTime = 0;
        Process currentProcess = null;

        while (completedProcessesCount < processes.size()) {
            addNewArrivingProcessesToQueue(currentTime, readyQueue);

            for (Process p : readyQueue) {
                if (p != currentProcess) {
                    p.setWaitingTime(p.getWaitingTime() + 1);
                }
            }

            if (currentProcess != null) {
                int executedTime = executedTimeMap.get(currentProcess);
                double executionLimit = Math.ceil(currentProcess.getQuantum() * 0.4);

                if (currentProcess.getCurrentBurstTime() <= 0) {
                    completedProcessesCount++;
                    executedTimeMap.put(currentProcess, 0); // Reset executed time
                    currentProcess = null;

                    addContextSwitchingTime(processorLogs, contextSwitchingTime);
                    currentTime += contextSwitchingTime;
                } else if (executedTime >= executionLimit) {
                    currentProcess.setQuantum(currentProcess.getQuantum() + 2); // Increment quantum
                    readyQueue.add(currentProcess);
                    executedTimeMap.put(currentProcess, 0); // Reset executed time
                    currentProcess = null;

                    addContextSwitchingTime(processorLogs, contextSwitchingTime);
                    currentTime += contextSwitchingTime;
                }
            }

            if (currentProcess == null && !readyQueue.isEmpty()) {
                currentProcess = readyQueue.poll();
            }

            if (currentProcess != null) {
                executeCurrentProcess(currentTime, currentProcess, processorLogs);
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
        executedTimeMap.put(currentProcess, executedTimeMap.get(currentProcess) + 1); // Increment executed time
    }

    private void addContextSwitchingTime(ProcessorLogs processorLogs, int contextSwitchingTime) {
        for (int i = 0; i < contextSwitchingTime; i++) {
            processorLogs.addLogUnit(null); // Log idle time during context switching
        }
    }
}