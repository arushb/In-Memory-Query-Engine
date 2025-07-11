package table;

public class IntVector implements Vector {
    private final int[] data;

    public IntVector(int[] data){
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
        int[] selected = new int[count];
        int idx = 0;
        for(int i = 0; i < data.length; i++){
            if(keep[i]) selected[idx++]= data[i];
        }
        return new IntVector(selected);
    }

    @Override
    public int getInt(int index){
        return data[index];
    }

    @Override
    public float getFloat(int index){
        throw new UnsupportedOperationException("Not a float vector.");
    }

    @Override
    public double getDouble(int index){
        throw new UnsupportedOperationException("Not a double vector.");
    }

    @Override
    public String getString(int index){
        throw new UnsupportedOperationException("Not a String vector.");
    }

    @Override
    public long getLong(int index) {
        throw new UnsupportedOperationException("Not a long vector.");
    }

    public int[] getRaw() {
        return data;
    }
}