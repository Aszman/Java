import java.util.Scanner;

public class Fibo {
    public static void main(String [] args) {
        Scanner scan = new Scanner(System.in);
       try {
           int n = scan.nextInt();

           if (n < 1 || n > 46) {
               return;
           }

           int[] tab = new int[n];

           int a = 1;
           int b = 1;

           for (int i = 0; i < tab.length; ++i) {
               tab[i] = a;
               int temp = b;
               b = a + b;
               a = temp;
               System.out.printf("tab[%d] = %d\n", i, tab[i]);
           }
       }catch(Exception e){
           System.out.println("Nie podano wartości całkowitej, wychodzę z programu");
       }
       finally{
           scan.close();
       }
    }
}
