package Operators;
import table.Table;
import table.Vector;
import java.util.*;


public class Filter implements Operator{
    private final Operator downstream;
    //private final Table table;
    private final ReqdPredicate predicate;
    private boolean[] keep;

    public Filter(ReqdPredicate predicate, Operator downstream){  //was ReqdPredicate<T>
        this.predicate = predicate;
        this.downstream = downstream;
    }

    @Override
    public void open(){
        downstream.open();
    }

    @Override
    public void forward(Table table){
        keep = predicate.evaluate(table);   // Evaluate conditions in parallel
        Table filtered = table.select(keep);         // Late materialization using boolean mask
        downstream.forward(filtered);

    }

    @Override
    public void close(){
        downstream.close();
    }

    @Override
    public boolean[] getMask(){
        return keep;
    }

    public static class ReqdPredicate{
        private final int numThreads = 16;

        public interface Condition{
            boolean test(Table table, int row);
        }

        private final Condition[] conditions;

        public ReqdPredicate(Condition... conditions){
            this.conditions = conditions;

        }

        public boolean[] evaluate(Table table){
            int rows = table.getRowCount();

            boolean[] keep = new boolean[rows];
            Thread[] threads = new Thread[numThreads];

            for(int i = 0; i < rows; i++){
                keep[i] = true;
            }

            int chunkSize = rows/numThreads;
            int extra = rows % numThreads;

            for(int i = 0; i < numThreads; i++){
                int start = i * chunkSize + Math.min(i, extra);
                int end = start + chunkSize + (i < extra ? 1 : 0);

                if(start >= end) continue;

                threads[i] = new Thread(new Runnable(){
                    @Override
                    public void run(){
                        for(int j = start; j < end; j++) {
                            for(Condition condition : conditions){
                                if(!condition.test(table, j)){
                                    keep[j] = false;
                                    break;
                                }
                            }
                        }
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

            return keep;




        }

    }



}