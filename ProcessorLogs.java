import java.util.ArrayList;

public class ProcessorLogs {
    private final ArrayList<String> logs = new ArrayList<>();

    public void addLogUnit(String processName){
        logs.add(processName);
    }

    ArrayList<String> getLogs(){
        return logs;
    }

    @Override
    public String toString(){
        StringBuilder logsString = new StringBuilder();

        for(int i=0 ; i<logs.size() ; i++){
            logsString.append(i).append(" : ").append( logs.get(i) ).append('\n');
        }

        return logsString.toString();
    }
}