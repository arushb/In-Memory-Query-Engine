package table;

public class DoubleVector implements Vector {
    private final double[] data;

    public DoubleVector(double[] data) {
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

        double[] selected = new double[count];
        int idx = 0;
        for (int i = 0; i < data.length; i++) {
            if (keep[i]) selected[idx++] = data[i];
        }

        return new DoubleVector(selected);
    }

    @Override
    public int getInt(int index) {
        throw new UnsupportedOperationException("Not an int vector");
    }

    @Override
    public float getFloat(int index) {
        throw new UnsupportedOperationException("Not a float Vector");
    }

    @Override
    public double getDouble(int index) {
        return data[index];
    }

    @Override
    public String getString(int index) {
        throw new UnsupportedOperationException("Not a String vector");
    }

    @Override
    public long getLong(int index) {
        throw new UnsupportedOperationException("Not a long vector.");
    }

    public double[] getRaw() {
        return data;
    }
}