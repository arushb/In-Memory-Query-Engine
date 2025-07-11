package Operators;

import table.Table;
import table.Vector;
import table.IntVector;
import table.DoubleVector;
import table.FloatVector;
import table.StringVector;
import table.longVector;


import java.util.ArrayList;
import java.util.List;

public class Sink implements Operator{
    private final String[] projectableColumns;
    public Sink(String[] projectableColumns){
        this.projectableColumns = projectableColumns;
    }

    @Override
    public void open(){
        for(String col : projectableColumns){
            System.out.print(col + "\t");
        }
        System.out.println();


    }

    @Override
    public void forward(Table table){
        Table projected = table.projectColumns(projectableColumns);
        int rows = projected.getRowCount();



        synchronized (this){
            for(int i = 0; i < rows; i++){
                for(String col : projectableColumns){
                    Vector vector = projected.getColumn(col);
                    if(vector instanceof IntVector){
                        System.out.print(((IntVector) vector).getInt(i) + "\t");
                    }else if(vector instanceof StringVector){
                        System.out.print(vector.getString(i) + "\t");
                    }else{
                        System.out.print("?\t");
                    }
                }
                System.out.println();
            }
        }
    }

    @Override
    public void close() {

    }
//
//    public boolean[] getMask(){
//        return null;
//    }


}
