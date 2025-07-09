package Operators;
import table.Table;
import table.Vector;
import java.util.*;
import java.util.function.Predicate;

public class ReqdPredicate<T> implements Operator{
    private final String columnName;
    private final Predicate<T> condition;
    
    public ReqdPredicate(String columnName, Predicate<T> condition){
        this.columnName = columnName;
        this.condition = condition;
    }

    public String getColumnName(){
        return columnName;
    }
    public Predicate<T> getCondition(){
        return condition;
    }

    public boolean test(Vector<?> column, int rowIndex){
        return condition.test((T)column.get(rowIndex));
    }

    @Override
    public void open() {
        // your logic
    }

    @Override
    public boolean hasNext(){
        return false;
    }

    @Override
    public Table next(){
        return null;
    }

    @Override
    public void close(){
    }
}