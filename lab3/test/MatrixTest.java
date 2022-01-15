import static org.junit.Assert.*;

public class MatrixTest {

    @org.junit.Test
    public void testMatrix1(){
        Matrix m = new Matrix(2, 3);
        assertEquals(2, m.rows);
        assertEquals(3, m.cols);
    }

    @org.junit.Test
    public void testMatrix2(){
        double[][] d = new double[][] {{2,4},{1,4,7,2},{9}};
        Matrix m = new Matrix(d);
        double[][] mArray = m.asArray();

        assertEquals(3, m.rows);
        assertEquals(4, m.cols);
        assertEquals(m.rows, mArray.length);

        for(int i = 0; i< mArray.length; ++i){
            assertEquals(m.cols, mArray[i].length);

            for(int j = d[i].length; j < mArray[i].length; ++j){
                assertEquals(0, mArray[i][j],0.001);
            }
        }
    }

    @org.junit.Test
    public void asArray() {
        double[][] d = new double[][]{{2,6,8},{1,0,-2}};
        Matrix m = new Matrix(d);
        double [][] mArray = m.asArray();
        for (int i=0; i<d.length; ++i){
            assertArrayEquals(d[i], mArray[i], 0.01);
        }
    }

    @org.junit.Test
    public void getNegativeRowIndex() {
        Matrix m = new Matrix(new double[][] {{1,4,6},{56,1,9}});

        try{
            m.get(-1, 0);
            fail("Exception not thrown");
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @org.junit.Test
    public void getNegativeColumnIndex() {
        Matrix m = new Matrix(new double[][] {{1,4,6},{56,1,9}});

        try{
            m.get(1, -1);
            fail("Exception not thrown");
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @org.junit.Test
    public void getRowIndexOutOfBounds() {
        Matrix m = new Matrix(new double[][] {{1,4,6},{56,1,9}});

        try{
            m.get(3, 2);
            fail("Exception not thrown");
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @org.junit.Test
    public void getColumnIndexOutOfBounds() {
        Matrix m = new Matrix(new double[][] {{1,4,6},{56,1,9}});

        try{
            m.get(1, 4);
            fail("Exception not thrown");
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @org.junit.Test
    public void getValuesEqual() {
        Matrix m = new Matrix(new double[][] {{1,4,6},{56,1,9}});
        assertEquals(56, m.get(1,0), 0.01);
    }

    @org.junit.Test
    public void setNegativeRowIndex() {
        Matrix m = new Matrix(new double[][] {{1,4,6},{56,1,9}});

        try{
            m.set(-1, 0, -1);
            fail("Exception not thrown");
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @org.junit.Test
    public void setNegativeColumnIndex() {
        Matrix m = new Matrix(new double[][] {{1,4,6},{56,1,9}});

        try{
            m.set(1, -1, 2);
            fail("Exception not thrown");
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @org.junit.Test
    public void setRowIndexOutOfBounds() {
        Matrix m = new Matrix(new double[][] {{1,4,6},{56,1,9}});

        try{
            m.set(3, 2, 0);
            fail("Exception not thrown");
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @org.junit.Test
    public void setColumnIndexOutOfBounds() {
        Matrix m = new Matrix(new double[][] {{1,4,6},{56,1,9}});

        try{
            m.set(1, 4, 89);
            fail("Exception not thrown");
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @org.junit.Test
    public void setValuesEqual() {
        Matrix m = new Matrix(new double[][] {{1,4,6},{56,1,9}});
        m.set(1,0, 0.21);
        assertEquals(0.21, m.get(1,0), 0.01);
    }

    @org.junit.Test
    public void testToString() {
        Matrix m = new Matrix(new double[][]{{2,6,8},{1,0,-2}});
        String mString = m.toString();

        int countLeftBracket = mString.length() - mString.replaceAll("\\[", "").length();
        int countComa = mString.length() - mString.replaceAll(",", "").length();

        assertEquals(3,countLeftBracket);
        assertEquals(5,countComa);
    }

    @org.junit.Test
    public void reshape() {
        Matrix m = new Matrix(2,3);
        try{
            m.reshape(3,4);
            fail("Exception not thrown");
        }catch(Exception e){
            assertTrue(true);
        }
    }

    @org.junit.Test
    public void shape() {
        Matrix m = new Matrix(2,3);
        int[] size = m.shape();
        assertArrayEquals(new int[]{2,3}, size);
    }

    @org.junit.Test
    public void addMatrix() {
        Matrix m = new Matrix(new double[][] {{1,4,6},{56,1,9}});
        assertEquals(0, (m.add(m.mul(-1))).frobenius(), 0.01);
    }

    @org.junit.Test
    public void addScalar() {
        double[][] d = new double[][] {{1,4,6},{56,1,9}};
        Matrix m = new Matrix(d);
        m = m.add(12);

        double[][] mArray = m.asArray();
        for (int i = 0; i<d.length; ++i){
            for(int j = 0; j < d[i].length; ++j) {
                assertEquals(d[i][j]+12, mArray[i][j], 0.01);
            }
        }
    }

    @org.junit.Test
    public void subMatrix() {
        Matrix m = new Matrix(new double[][] {{1,4,6},{56,1,9}});
        assertEquals(0, (m.sub(m)).frobenius(), 0.01);
    }

    @org.junit.Test
    public void subScalar() {
        double[][] d = new double[][] {{1,4,6},{56,1,9}};
        Matrix m = new Matrix(d);
        m = m.sub(12);

        double[][] mArray = m.asArray();
        for (int i = 0; i<d.length; ++i){
            for(int j = 0; j < d[i].length; ++j) {
                assertEquals(d[i][j]-12, mArray[i][j], 0.01);
            }
        }
    }

    @org.junit.Test
    public void mulMatrix() {
        double[][] d1 = new double[][] {{1,4,6},{56,1,9}};
        double[][] d2 = new double[][] {{-2,2,-0.5},{0.25,6,2}};
        Matrix m1 = new Matrix(d1);
        m1 = m1.mul(new Matrix(d2));

        double[][] mArray = m1.asArray();
        for (int i = 0; i<d1.length; ++i){
            for(int j = 0; j < d1[i].length; ++j) {
                assertEquals(d1[i][j]*d2[i][j], mArray[i][j], 0.01);
            }
        }
    }

    @org.junit.Test
    public void mulScalar() {
        double[][] d = new double[][] {{1,4,6},{56,1,9}};
        Matrix m = new Matrix(d);
        m = m.mul(-1);

        double[][] mArray = m.asArray();
        for (int i = 0; i<d.length; ++i){
            for(int j = 0; j < d[i].length; ++j) {
                assertEquals(d[i][j]*-1, mArray[i][j], 0.01);
            }
        }
    }

    @org.junit.Test
    public void divMatrix() {
        Matrix m = new Matrix(new double[][] {{1,4,6},{56,1,9}});
        assertEquals(Math.sqrt(m.rows*m.cols), (m.div(m)).frobenius(), 0.01);
    }

    @org.junit.Test
    public void divScalar() {
        double[][] d = new double[][] {{1,4,6},{56,1,9}};
        Matrix m = new Matrix(d);
        m = m.div(2);

        double[][] mArray = m.asArray();
        for (int i = 0; i<d.length; ++i){
            for(int j = 0; j < d[i].length; ++j) {
                assertEquals(d[i][j]/2, mArray[i][j], 0.01);
            }
        }
    }

    @org.junit.Test
    public void dotInappropriateShape() {
        Matrix m1 = new Matrix(2,3);
        Matrix m2 = new Matrix(2,2);
        try{
            m1.dot(m2);
            fail("Exception not thrown");
        }catch (Exception e){
            assertTrue(true);
        }
    }

    @org.junit.Test
    public void dotAppropriateShape() {
        Matrix m1 = new Matrix(new double[][] {{1, 4, 6}, {12, 1, 9}});
        Matrix m2 = new Matrix(new double[][] {{3, 2}, {-4, 0}, {8, 2}});
        double[][] mArray = m1.dot(m2).asArray();
        double[][] ddot = new double[][] {{35, 14}, {104, 42}};
        for (int i = 0; i<ddot.length; ++i) {
            assertArrayEquals(ddot[i], mArray[i], 0.01);
        }
    }

    @org.junit.Test
    public void frobenius() {
        double[][] d = new double[][] {{1,4,6},{56,1,9}};
        Matrix m = new Matrix(d);
        double dFrob = 0;
        for(var row: d){
            for(var v: row){
                dFrob += v*v;
            }
        }
        assertEquals(Math.sqrt(dFrob), m.frobenius(), 0.01);
    }

    @org.junit.Test
    public void random() {
        Matrix m = Matrix.random(3, 2);
        assertArrayEquals(new int[]{3,2}, m.shape());
    }

    @org.junit.Test
    public void eye() {
        Matrix m = Matrix.eye(6);
        assertEquals(Math.sqrt(6), m.frobenius(), 0.01);
    }

    @org.junit.Test
    public void det() {
        Matrix m = new Matrix(new double [][]{{1,2,1,0},{0,3,1,1},{1,0,3,1},{3,1,2,0}});
        assertEquals(10, m.det(), 0.01);
    }

    @org.junit.Test
    public void inv() {
        Matrix m = new Matrix(new double [][]{{1,2,1,0},{0,3,1,1},{1,0,3,1},{3,1,2,0}});
        Matrix vector = new Matrix(new double[][]{{4, 2, -1, 8}});

        assertArrayEquals(vector.asArray()[0], vector.dot(m.dot(m.inv())).asArray()[0], 0.01);
    }

}