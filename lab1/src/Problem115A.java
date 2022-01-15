import java.util.Scanner;

public class Problem115A {
    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);

        int n = scan.nextInt();
        int [] employees = new int[n];
        int groups = 0;

        for (int i = 0; i < n; ++i) employees[i] = scan.nextInt();

        for (int i = 0; i< n; ++i){
            int count = 1;
            int temp = i;
            while (employees[temp] != -1){
                temp = employees[temp] -1;
                ++count;
            }
            if (count > groups){
                groups = count;
            }
        }
        System.out.println(groups);

        scan.close();
    }
}
