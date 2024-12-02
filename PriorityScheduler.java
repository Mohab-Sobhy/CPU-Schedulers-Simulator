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
    public void schedule() {
        Comparator<Process> priorityComparator = Comparator.comparingInt(Process::getPriority);
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(priorityComparator);

        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        int currentTime = 0;
        Process currentProcess = null;

        // استمر حتى تنتهي جميع العمليات
        while (completedProcessesCount < processes.size()) {
            // إضافة العمليات التي وصلت في الوقت الحالي إلى قائمة الانتظار
            addNewArrivingProcessesToQueue(currentTime, readyQueue);

            // إذا لم تكن هناك عملية حالياً، اختر العملية التالية من قائمة الانتظار
            if (currentProcess == null || currentProcess.getBurstTime() == 0) {
                completedProcessesCount = handleProcessCompletion(currentTime, currentProcess);
                currentProcess = readyQueue.poll();
                completedProcessesCount = handleNewProcessStart(currentTime, currentProcess);
            }

            // تنفيذ العملية الحالية إذا كانت موجودة
            if (currentProcess != null) {
                executeCurrentProcess(currentTime, currentProcess);
            }

            // تحديث الوقت
            currentTime++;
        }
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
        if (currentProcess != null && currentProcess.getBurstTime() == 0) {
            System.out.println("Terminated: " + currentProcess.getName() + " at start of " + currentTime);
            completedProcessesCount++;
        }
        return completedProcessesCount;
    }

    // التعامل مع بدء العملية الجديدة
    private int handleNewProcessStart(int currentTime, Process currentProcess) {
        if (currentProcess != null) {
            System.out.println("Starting: " + currentProcess.getName() + " at start of " + currentTime);
        } else {
            System.out.println("CPU is idle at " + currentTime);
        }
        return completedProcessesCount;
    }

    // تنفيذ العملية الحالية
    private void executeCurrentProcess(int currentTime, Process currentProcess) {
        System.out.println("Running: " + currentProcess.getName() + " at " + currentTime);
        currentProcess.setBurstTime(currentProcess.getBurstTime() - 1);
    }
}
