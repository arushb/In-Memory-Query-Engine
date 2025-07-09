import Operators.*;

import Operators.ReqdPredicate;
import table.Table;
//import predicate.ReqdPredicate;
import table.Vector;

import CSVReader.CSVReader;
import java.util.*;

public class Main{
    public static void main(String[] args){
        try{
            Table patients = CSVReader.readCSV("records.csv");

            ReqdPredicate<Integer> ageOver40 = new ReqdPredicate<>("Age", val -> val > 40);

            Operator scan = new Scan(patients);
            Operator filter = new Filter(scan, ageOver40);
            Operator sink = new Sink(filter);

            sink.open();
            while(sink.hasNext()){
                sink.next();
            }
            sink.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

