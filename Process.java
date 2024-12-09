public class Process {
    private final String name;
    private final int arrivalTime;
    private final int burstTime;
    private final int priority;
    private final String color;
    private int waitingTime;
    private int turnaroundTime;
    private int completionTime;
    private int currentBurstTime;
    private int quantum;
    private int startTime;
    private final int fcaiFactor;

    public Process(String name, int arrivalTime, int burstTime, int priority, String color) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.currentBurstTime = burstTime;
        this.priority = priority;
        this.color = color;
        this.quantum = 2;
        this.fcaiFactor = calculateFcaiFactor();
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public String getColor() {
        return color;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public int getCurrentBurstTime(){
        return currentBurstTime;
    }

    public void setCurrentBurstTime(int currentBurstTime){
        this.currentBurstTime = currentBurstTime;
    }

    public int getQuantum(){
        return quantum;
    }

    public void setQuantum(int quantum){
        this.quantum = quantum;
    }

    public int getFcaiFactor(){
        return fcaiFactor;
    }

    public int calculateFcaiFactor() {
        return (10 - this.getPriority()) + (this.getArrivalTime() / 2) + (this.getCurrentBurstTime() / 2);
    }

    public int age() {
        int wt = getWaitingTime();
        return getBurstTime() - wt;
    }

    @Override
    public String toString() {
        return "Process{name='" + name + "', arrivalTime=" + arrivalTime + ", burstTime=" + burstTime + ", priority=" + priority + ", color='" + color + "', fcaiFactor=" + fcaiFactor + "}";
    }
}