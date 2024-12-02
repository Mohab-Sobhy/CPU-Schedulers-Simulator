import java.util.ArrayList;

public class ProcessorLogs {
    private final ArrayList<Process> logs = new ArrayList<>();

    public void addLogUnit(Process process){
        logs.add(process);
    }

    ArrayList<Process> getLogs(){
        return logs;
    }

    @Override
    public String toString(){
        StringBuilder logsString = new StringBuilder();

        for(int i=0 ; i<logs.size() ; i++){

            if(logs.get(i) == null){
                logsString.append("null\n");
            }
            else{
                logsString.append( logs.get(i).getName() ).append('\n');
            }

        }

        return logsString.toString();
    }

}