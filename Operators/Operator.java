package Operators;

import table.Table;

public interface Operator{
    void open();
    void consume(Table table);
    void close();

    //gets the mask
    default boolean[] getMask() {
        return null;
    }
}