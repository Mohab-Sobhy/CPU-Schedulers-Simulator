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

        // استمر حتى تنتهي جميع العمليات
        while (completedProcessesCount < processes.size()) {

            for (Process process : readyQueue) {
                process.setWaitingTime( process.getWaitingTime()+1 );
            }

            // إضافة العمليات التي وصلت في الوقت الحالي إلى قائمة الانتظار
            addNewArrivingProcessesToQueue(currentTime, readyQueue);

            // إذا لم تكن هناك عملية حالياً، اختر العملية التالية من قائمة الانتظار
            if (currentProcess == null || currentProcess.getCurrentBurstTime() == 0) {
                completedProcessesCount = handleProcessCompletion(currentTime, currentProcess);
                currentProcess = readyQueue.poll();
                completedProcessesCount = handleNewProcessStart(currentTime, currentProcess, processorLogs);
            }

            // تنفيذ العملية الحالية إذا كانت موجودة
            if (currentProcess != null) {
                executeCurrentProcess(currentTime, currentProcess, processorLogs);
            }

            // تحديث الوقت
            currentTime++;
        }

        // print the summary of scheduling
        System.out.print("Processes execution order: ");
        for(String processName:ProcessesExecutionOrder){
            System.out.print(processName + " , ");
        }
        System.out.println();

        for(Process process : processes){
            process.setTurnaroundTime( process.getBurstTime()+process.getWaitingTime() );
            process.setCompletionTime( process.getTurnaroundTime()+process.getArrivalTime() );

            System.out.println(process.getName() + "Waiting Time -> " + " : " + process.getWaitingTime());
            System.out.println(process.getName() + "Turnaround Time -> " + " : " + process.getTurnaroundTime());

            totalWaitingTime += process.getWaitingTime();
            totalTurnaroundTime += process.getTurnaroundTime();
        }

        System.out.println("Average Waiting Time: " + totalWaitingTime/totalProcesses);
        System.out.println("Average Turnaround Time: " + totalTurnaroundTime/totalProcesses);


        return processorLogs;
    }

    // إضافة العمليات التي وصلت في الوقت الحالي إلى قائمة الانتظار
    private void addNewArrivingProcessesToQueue(int currentTime, PriorityQueue<Process> readyQueue) {
        while (scheduledProcessesCount < processes.size() && processes.get(scheduledProcessesCount).getArrivalTime() <= currentTime) {
            readyQueue.add(processes.get(scheduledProcessesCount));
            scheduledProcessesCount++;
        }
    }

    // التعامل مع انتهاء العملية الحالية
    private int handleProcessCompletion(int currentTime, Process currentProcess) {
        if (currentProcess != null && currentProcess.getCurrentBurstTime() == 0) {
            completedProcessesCount++;
        }
        return completedProcessesCount;
    }

    // التعامل مع بدء العملية الجديدة
    private int handleNewProcessStart(int currentTime, Process currentProcess, ProcessorLogs processorLogs) {
        if (currentProcess != null) {
            ProcessesExecutionOrder.add( currentProcess.getName() );
        } else {
            processorLogs.addLogUnit( null );
        }
        return completedProcessesCount;
    }

    // تنفيذ العملية الحالية
    private void executeCurrentProcess(int currentTime, Process currentProcess , ProcessorLogs processorLogs) {
        processorLogs.addLogUnit( currentProcess.getName() );
        currentProcess.setCurrentBurstTime(currentProcess.getCurrentBurstTime() - 1);
    }
}
