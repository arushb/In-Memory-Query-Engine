package table;

public class longVector implements Vector {
    private final long[] data;

    public longVector(long[] data){
        this.data = data;
    }

    @Override
    public int getSize(){
        return data.length;
    }

    @Override
    public Vector select(boolean[] keep){
        int count = 0;
        for(boolean b : keep) if(b) count++;
        long[] selected = new long[count];
        int idx = 0;
        for(int i = 0; i < data.length; i++){
            if(keep[i]) selected[idx++]= data[i];
        }
        return new longVector(selected);
    }

    @Override
    public int getInt(int index) {
        throw new UnsupportedOperationException("Not an int vector.");
    }

    @Override
    public float getFloat(int index) {
        throw new UnsupportedOperationException("Not a float vector.");
    }

    @Override
    public double getDouble(int index) {
        throw new UnsupportedOperationException("Not a double vector.");
    }

    @Override
    public String getString(int index) {
        throw new UnsupportedOperationException("Not a String vector.");
    }

    @Override
    public long getLong(int index) {
        return data[index];
    }

    public long[] getRaw() {
        return data;
    }
}