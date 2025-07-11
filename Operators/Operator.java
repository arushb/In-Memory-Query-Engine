package Operators;

import table.Table;

public interface Operator{
    void open();
    void forward(Table table);
    void close();

    //gets the mask
    default boolean[] getMask() {
        return null;
    }
}