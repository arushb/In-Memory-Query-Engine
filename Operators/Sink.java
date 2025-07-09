package Operators;

import table.Table;

import java.util.ArrayList;
import java.util.List;

public class Sink implements Operator{
    private final String[] projectableColumns;
    public Sink(String[] projectableColumns){
        this.projectableColumns = projectableColumns;
    }

    @Override
    public void open(){

    }

    @Override
    public boolean consume() {
        Table projected = table.
    }

    @Override
    public Table next() {
        if(!hasNext()) return null;
        Table table = child.next();
        printTable(table);
        return table;
    }

    @Override
    public void close() {
        child.close();
        opened = false;
    }

    private void printTable(Table table){
        List<String> columnNames = new ArrayList<>(table.getColumnNames());
        int rowCount = table.getRowCount();

        //headers
        for(String str : columnNames){
            System.out.print(str + "\t");
        }

        //print values
        System.out.println();
        for(int i = 0; i < rowCount; i++){
            for(String str : columnNames){
                System.out.print(table.getColumn(str).get(i) + "\t");
            }
            System.out.println();
        }

    }

    public boolean[] getMask(){
        return null;
    }


}
