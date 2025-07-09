package table;
import java.util.List;
import java.util.ArrayList;

public interface Vector{
    int getSize();
    Vector select(boolean[] keep);

    int getInt(int index);
    float getFloat(int index);
    double getDouble(int index);
    String getString(int index);
    long getLong(int index);

}
