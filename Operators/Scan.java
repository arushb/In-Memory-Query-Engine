package Operators;

import table.Table;
import CSVReader.CSVReader;
import java.util.*;
import java.util.concurrent.*;

public class Scan implements Operator{
    private final Table table;
    private final Operator downstream;
    private final int numThreads = 16;
    private final Thread[] threads;

    //private boolean scanComplete = false;

    public Scan(Table table, Operator downstream) {
        this.table = table;
        this.downstream = downstream;
        this.threads = new Thread[numThreads];
    }

    @Override
    public void open(){
        downstream.open();
        int rows = table.getRowCount();

        int chunkSize = rows/numThreads;
        int extra = rows % numThreads;I

        for(int i = 0; i < numThreads; i++){
            int start = i * chunkSize + Math.min(i, extra);
            int end = start + chunkSize + (i < extra ? 1 : 0);

            if(start >= end) continue;

            threads[i] = new Thread(new Runnable(){
                @Override
                public void run(){
                    boolean[] keep = new boolean[rowCount];
                    for(int j = start; j < end; j++){
                        keep[j] = true;
                    }
                    Table chunk = table.select(keep);
                    downstream.consume(chunk);
                }
            });

            threads[i].start();
        }

        for(int i = 0; i < numThreads; i++){
            if(threads[i] != null){
                try{
                    threads[i].join();
                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        }
        downstream.close();

    }

    @Override
    public void close(){
        //
    }

    @Override
    public void consume(Table table){

    }


}