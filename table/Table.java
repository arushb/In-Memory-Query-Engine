package table;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class Table{
    private final Map<String, Vector> columns;
    private final int rowCount;

    //constructor
    public Table(Map<String, Vector> columns){
        this.columns = columns;
        this.rowCount = columns.values().iterator().next().getSize();
    }

    //get Column names in a set
    public Set<String> getColumnNames(){
        return columns.keySet();
    }

    //get Column data for the named column
    public Vector getColumn(String name){
        return columns.get(name);
    }

    public int getRowCount(){
        return rowCount;
    }

    public Table select(boolean[] keep) {
        Map<String, Vector> filtered = new HashMap<>();
        for (String col : columns.keySet()) {
            filtered.put(col, columns.get(col).select(keep));
        }
        return new Table(filtered);
    }
}