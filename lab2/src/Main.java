public class Main {
    public static void main(String []args){
        Matrix m = new Matrix(new double [][]{{1,2,1,0},{0,3,1,1},{1,0,3,1},{3,1,2,0}});
        m.print();

        System.out.println(m.det());
        /*
        m.set(2,4,-3.14);
        System.out.println(m.toString());
        m.reshape(5,3);
        System.out.println(m.toString());
        int [] table = m.shape();
        System.out.println(table[0]);

        System.out.println((m.div(m)).frobenius());

        Matrix eye = Matrix.eye(5);
        System.out.println(eye.frobenius());

        System.out.println(eye.toString());

        Matrix rand = Matrix.random(5,5);
        System.out.println(rand.toString());

        System.out.println(m.dot(n).toString());
        */

    }
}
