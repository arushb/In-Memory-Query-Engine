package table;

public class StringVector implements Vector {
    private final String[] data;

    public StringVector(String[] data) {
        this.data = data;
    }

    @Override
    public int getSize() {
        return data.length;
    }

    @Override
    public Vector select(boolean[] keep) {
        int count = 0;
        for (boolean b : keep) if (b) count++;

        String[] selected = new String[count];
        int idx = 0;
        for (int i = 0; i < data.length; i++) {
            if (keep[i]) selected[idx++] = data[i];
        }

        return new StringVector(selected);
    }

    @Override
    public int getInt(int index) {
        throw new UnsupportedOperationException("Not an int vector");
    }

    @Override
    public float getFloat(int index) {
        throw new UnsupportedOperationException("Not a float vector");
    }

    @Override
    public double getDouble(int index) {
        throw new UnsupportedOperationException("Not a double vector");
    }

    @Override
    public String getString(int index) {
        return data[index];
    }

    @Override
    public long getLong(int index) {
        throw new UnsupportedOperationException("Not a long vector.");
    }

    public String[] getRaw() {
        return data;
    }
}