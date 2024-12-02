public class LogUnit {
    private  String processName;
    private boolean isRunning;

    LogUnit(String processName , boolean isRunning){
        this.processName = processName;
        this.isRunning = isRunning;
    }

    @Override
    public String toString(){
        return isRunning + " : " + processName + '\n' ;
    }

}
