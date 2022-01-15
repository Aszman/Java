import java.util.Random;


public class Matrix {
    double[] data;
    int rows;
    int cols;

    Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new double[rows * cols];
    }

    Matrix(double[][] d) {
        this.rows = d.length;
        this.cols = 0;
        for (double[] doubles : d) {
            if (doubles.length > this.cols) {
                this.cols = doubles.length;
            }
        }

        data = new double[rows * cols];
        for (int i = 0; i < d.length; ++i) {
            System.arraycopy(d[i], 0, data, i*this.cols, d[i].length);
        }
    }

    double[][] asArray() {
        double[][] d = new double[this.rows][this.cols];
        for (int i = 0; i < this.rows; ++i) {
            System.arraycopy(data, i*this.cols, d[i], 0, this.cols);
        }
        return d;
    }

    double get(int r, int c) {
        if (r >= this.rows || r < 0) {
            throw new RuntimeException(String.format("Incorrect row's index, should be from range [0, %d)", this.rows));
        } else if (c >= this.cols || c < 0) {
            throw new RuntimeException(String.format("Incorrect column's index, should be from range [0, %d)", this.cols));
        }

        return this.data[r * this.cols + c];
    }

    void set(int r, int c, double value) {
        if (r >= this.rows || r < 0) {
            throw new RuntimeException(String.format("Incorrect row's index, should be from range [0, %d)", this.rows));
        } else if (c >= this.cols || c < 0) {
            throw new RuntimeException(String.format("Incorrect column's index, should be from range [0, %d)", this.cols));
        }

        this.data[r * this.cols + c] = value;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("[");
        for (int i = 0; i < this.rows; ++i) {
            buf.append("[");
            for (int j = 0; j < this.cols; ++j) {
                if (j != 0) {
                    buf.append(", ");
                }

                buf.append(this.data[i * this.cols + j]);
            }
            buf.append("]");
            if (i != this.rows - 1) {
                buf.append("\n");
            }
        }
        buf.append("]");

        return buf.toString();
    }

    void reshape(int newRows, int newCols) {
        if (this.rows * this.cols != newRows * newCols) {
            throw new RuntimeException(String.format("%d x %d matrix can't be reshaped to %d x %d"
                    , this.rows, this.cols, newRows, newCols));
        }
        this.rows = newRows;
        this.cols = newCols;
    }

    int[] shape() {
        return new int[]{this.rows, this.rows};
    }

    Matrix add(Matrix m) {
        if (this.rows != m.rows || this.cols != m.cols) {
            throw new RuntimeException("Matrices have different shapes");
        }

        Matrix newM = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                newM.set(i, j, this.get(i, j) + m.get(i, j));
            }
        }
        return newM;
    }

    Matrix add(double w) {
        Matrix newM = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                newM.set(i, j, this.get(i, j) + w);
            }
        }
        return newM;
    }

    Matrix sub(Matrix m) {
        if (this.rows != m.rows || this.cols != m.cols) {
            throw new RuntimeException("Matrices have different shapes");
        }

        Matrix newM = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                newM.set(i, j, this.get(i, j) - m.get(i, j));
            }
        }
        return newM;
    }

    Matrix sub(double w) {
        Matrix newM = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                newM.set(i, j, this.get(i, j) - w);
            }
        }
        return newM;
    }

    Matrix mul(Matrix m) {
        if (this.rows != m.rows || this.cols != m.cols) {
            throw new RuntimeException("Matrices have different shapes");
        }

        Matrix newM = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                newM.set(i, j, this.get(i, j) * m.get(i, j));
            }
        }
        return newM;
    }

    Matrix mul(double w) {
        Matrix newM = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                newM.set(i, j, this.get(i, j) * w);
            }
        }
        return newM;
    }

    Matrix div(Matrix m) {
        if (this.rows != m.rows || this.cols != m.cols) {
            throw new RuntimeException("Matrices have different shapes");
        }

        Matrix newM = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                newM.set(i, j, this.get(i, j) / m.get(i, j));
            }
        }
        return newM;
    }

    Matrix div(double w) {
        Matrix newM = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                newM.set(i, j, this.get(i, j) / w);
            }
        }

        return newM;
    }

    Matrix dot(Matrix m) {
        if (this.cols != m.rows) {
            throw new RuntimeException("Column's number of first matrix is different from row's number of second matrix");
        }

        Matrix dotM = new Matrix(this.rows, m.cols);
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < m.cols; ++j) {
                double value = 0;
                for (int k = 0; k < this.cols; ++k) {
                    value += this.get(i, k) * m.get(k, j);
                }
                dotM.set(i, j, value);
            }
        }
        return dotM;
    }

    double frobenius() {
        double value = 0;
        for (var d : this.data) {
            value += d * d;
        }

        return Math.sqrt(value);
    }

    public static Matrix random(int rows, int cols) {
        Matrix m = new Matrix(rows, cols);
        Random r = new Random();

        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                m.set(i, j, r.nextDouble());
            }
        }
        return m;
    }

    public static Matrix eye(int n) {
        Matrix m = new Matrix(n, n);

        for (int i = 0; i < n; ++i) {
            m.set(i, i, 1);
        }
        return m;
    }

    double det() {
        if (this.rows != this.cols) {
            throw new RuntimeException("Not square matrix");
        }
        int r = 0;
        int c = 0;
        boolean isNegative = false;

        Matrix tempM = new Matrix(this.rows, this.cols);
        tempM.data = this.data.clone();

        while (r < tempM.rows && c < tempM.cols) {
            int i_max = -1;
            double value_max = 0;
            for (int i = r; i < tempM.rows; ++i) {
                if (value_max < Math.abs(tempM.get(i, c))) {
                    value_max = Math.abs(tempM.get(i, c));
                    i_max = i;
                }
            }

            // column is a null vector
            if (tempM.get(i_max, c) == -1) {
                return 0;
            } else {
                if (r != i_max) {
                    tempM.swapRows(r, i_max);
                    isNegative = !isNegative;
                }

                double pivot = tempM.get(r, c);
                for (int i = r + 1; i < tempM.rows; ++i) {
                    double f = tempM.get(i, c) / pivot;

                    for (int j = c; j < tempM.cols; ++j) {
                        tempM.set(i, j, tempM.get(i, j) - tempM.get(r, j) * f);
                    }
                }
                r += 1;
                c += 1;
            }
        }
        double det = 1;
        for (int i = 0; i < tempM.rows; ++i) {
            det *= tempM.get(i, i);
        }
        if (isNegative) {
            det *= -1;
        }
        return det;
    }

    Matrix inv(){
        if (this.rows != this.cols) {
            throw new RuntimeException("Not square matrix");
        }
        else if(this.det() == 0){
            throw new RuntimeException("Determinant equals 0, inverse matrix doesn't exist");
        }

        Matrix tempM = new Matrix(this.rows, this.cols);
        tempM.data = this.data.clone();

        Matrix invM = Matrix.eye(this.cols);

        int r = 0;
        int c = 0;

        while(r < tempM.rows && c < tempM.cols){
            int i_max = 0;
            double value_max = 0;
            for (int i = r; i< tempM.rows;++i) {
                if (value_max < Math.abs(tempM.get(i, c))) {
                    value_max = Math.abs(tempM.get(i, c));
                    i_max = i;
                }
            }

            if (r != i_max){
                tempM.swapRows(r, i_max);
                invM.swapRows(r, i_max);
            }

            double pivot = tempM.get(r,c);

            for (int i = r + 1; i < tempM.rows; ++i) {
                double f = tempM.get(i,c) / pivot;

                for (int j = 0; j < tempM.cols; ++j) {
                    tempM.set(i, j, tempM.get(i, j) - tempM.get(r, j) * f);
                    invM.set(i,j, invM.get(i,j) - invM.get(r,j) * f);
                }
            }

            for (int j = 0; j < tempM.cols; ++j){
                tempM.set(r,j, tempM.get(r, j)/pivot);
                invM.set(r, j, invM.get(r, j)/pivot);
            }

            r +=1;
            c +=1;
        }
        for (c = this.cols - 1; c > 0; --c){
            for (int i = c - 1; i >= 0; --i){
                double f =  tempM.get(i, c);
                for (int j = 0; j < this.cols; ++j) {
                    tempM.set(i, j, tempM.get(i, j) - tempM.get(c, j) * f);
                    invM.set(i,j, invM.get(i,j) - invM.get(c,j) * f);
                }
            }
        }

        return invM;
    }

    private void swapRows(int h, int i_max) {
        for (int j = 0; j < this.cols; ++j) {
            double tmp = this.get(h, j);
            this.set(h, j, this.get(i_max, j));
            this.set(i_max, j, tmp);
        }
    }

    // Debuging method
    void print() {
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                System.out.printf("%f ", data[i * this.cols + j]);
            }
            System.out.print("\n");
        }
    }
}
