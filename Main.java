import Operators.*;
import table.Table;

public class Main{
    public static void main(String[] args){
        try{
            String filepath = "records.csv";

            String[] projectableColumns = {"patientId", "Age"};

            Operator sink = new Sink(projectableColumns);
            Filter.ReqdPredicate.Condition ageAbove40 = (table, row) -> table.getColumn("Age").getInt(row) > 40;
            Filter.ReqdPredicate.Condition IDBelow5 = (table, row) -> table.getColumn("patientId").getInt(row) < 5;
            Filter.ReqdPredicate predicate = new Filter.ReqdPredicate(ageAbove40, IDBelow5);
            Operator filter = new Filter(predicate, sink);
            Operator scan = new Scan(filepath, filter);


            scan.open();
        }
        catch(Exception e){
            e.printStackTrace();
        }
//        Exception e = new Exception();
//        e.printStackTrace();
    }
}

