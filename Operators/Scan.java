package Operators;

import table.*;
import table.Vector;
import table.IntVector;
import java.util.*;
import java.io.*;

public class Scan implements Operator{
    private final String filepath;
    private final Operator downstream;
    private final int numThreads = 16;
    private final Thread[] threads;

    //private boolean scanComplete = false;

    public Scan(String filepath, Operator downstream) {
        this.filepath = filepath;
        this.downstream = downstream;
        this.threads = new Thread[numThreads];
    }

    @Override
    public void open(){
        downstream.open();
        Table table = readCSV(filepath);
        int rows = table.getRowCount();
        int chunkSize = rows/numThreads;
        int extra = rows % numThreads;

        for(int i = 0; i < numThreads; i++){
            int start = i * chunkSize + Math.min(i, extra);
            int end = start + chunkSize + (i < extra ? 1 : 0);

            if(start >= end) continue;

            threads[i] = new Thread(new Runnable(){
                @Override
                public void run(){
                    boolean[] keep = new boolean[rows];
                    for(int j = start; j < end; j++){
                        keep[j] = true;
                    }
                    Table chunk = table.select(keep);
                    downstream.forward(chunk);
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
    public void forward(Table table){
        throw new UnsupportedOperationException("Error");
    }

    @Override
    public boolean[] getMask(){
        return null;
    }

    private Table readCSV(String filepath){
        try(BufferedReader reader = new BufferedReader(new FileReader(filepath))){
            String headerLine = reader.readLine();
            if(headerLine == null) throw new RuntimeException("Empty CSV file.");

            String[] headers = headerLine.split(",");
            int numCols = headers.length;

            List<String[]> rows = new ArrayList<>();
            String line;
            while((line = reader.readLine()) != null){
                rows.add(line.split(","));
            }

            int numRows = rows.size();
            Map<String, Vector> columns = new HashMap<>();

            for(int col = 0; col < numCols; col++){
                String name = headers[col].trim();
                String firstVal = rows.get(0)[col].trim();
//
//                if(firstVal.equalsIgnoreCase("true") || firstVal.equalsIgnoreCase("false")){
//                    boolean[] bools = new boolean[numRows];
//                    for(int i = 0; i < numRows; i++){
//                        bools[i] = Boolean.parseBoolean(rows.get(i)[col].trim());
//                    }
//                    columns.put(name, new BooleanVector(bools));
//
//                }
                if(firstVal.matches("-?\\d+")){
                    int[] ints = new int[numRows];
                    for(int i = 0; i < numRows; i++){
                        ints[i] = Integer.parseInt(rows.get(i)[col].trim());
                    }
                    columns.put(name, new IntVector(ints));

                }else{
                    String[] strs = new String[numRows];
                    for(int i = 0; i < numRows; i++){
                        strs[i] = rows.get(i)[col].trim();
                    }
                    columns.put(name, new StringVector(strs));
                }
            }

            return new Table(columns);
        }catch(IOException e){
            throw new RuntimeException("CSV read failed!" + e.getMessage(), e);
        }
    }

}