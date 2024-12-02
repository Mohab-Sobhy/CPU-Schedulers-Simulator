public class Process {
    private String name;
    private int arrivalTime;
    private int burstTime;
    private int priority;
    private String color;
    private int waitingTime;
    private int turnaroundTime;
    private int completionTime;
    private boolean isRunning;
    private int currentBurstTime;

    // Constructor
    public Process(String name, int arrivalTime, int burstTime, int priority, String color) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.currentBurstTime = burstTime;
        this.priority = priority;
        this.color = color;
        isRunning = false;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for arrivalTime
    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    // Getter and Setter for burstTime
    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    // Getter and Setter for priority
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    // Getter and Setter for color
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // Getter and Setter for waitingTime
    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    // Getter and Setter for turnaroundTime
    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    // Getter and Setter for completionTime
    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    // Getter and Setter for isRunning
    public boolean getIsRunning(){
        return isRunning;
    }

    public void setIsRunning(boolean isRunning){
        this.isRunning = isRunning;
    }

    // Getter and Setter for CurrentBurstTime
    public int getCurrentBurstTime(){
        return currentBurstTime;
    }

    public void setCurrentBurstTime(int currentBurstTime){
        this.currentBurstTime = currentBurstTime;
    }

    @Override
    public String toString() {
        return "Process{name='" + name + "', arrivalTime=" + arrivalTime + ", burstTime=" + burstTime + ", priority=" + priority + ", color='" + color + "'}";
    }
}