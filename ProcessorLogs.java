import java.util.ArrayList;

public class ProcessorLogs {
    private final ArrayList<LogUnit> logs = new ArrayList<>();

    public void addLogUnit(LogUnit logUnit){
        logs.add(logUnit);
    }

    ArrayList<LogUnit> getLogs(){
        return logs;
    }

    @Override
    public String toString(){
        StringBuilder logsString = new StringBuilder();

        for(int i=0 ; i<logs.size() ; i++){
            logsString.append(i).append(" : ").append( logs.get(i).toString() );
        }

        return logsString.toString();
    }
}
