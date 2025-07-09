package Operators;
import table.Table;
import table.Vector;
import java.util.*;


public class Filter implements Operator{
    private final Operator child;
    private boolean filterComplete;
    private final ReqdPredicate predicate;
    private boolean[] keep;

     public Filter(Operator child, ReqdPredicate predicate){  //was ReqdPredicate<T>
         this.child = child;
         this.predicate = predicate;
     }

    @Override
    public void open(){
        child.open();
        filterComplete = false;
    }

    @Override
    public boolean hasNext(){
        return (!filterComplete && child.hasNext());
    }

    @Override
    public Table next(){
        if(filterComplete) return null;
        Table table = child.next();

        //noinspection unchecked
        Vector<?> column = table.getColumn(predicate.getColumnName());
        int rowCount = column.size();

        keep = new boolean[rowCount];
        for(int i = 0; i < rowCount; i++){
            keep[i] = predicate.test(column, i);
        }

        filterComplete = true;
        return table.select(keep);

    }

    @Override
    public void close(){
        child.close();
        keep = null;
        filterComplete = true;
    }

    @Override
    public boolean[] getMask(){
        return keep;
    }

}