import java.util.Scanner;

public class Problem610A {
    public static void main(String [] args){
        Scanner scan = new Scanner(System.in);
        int stickLength = scan.nextInt();
        int counter = 0;

        if (stickLength % 2 == 0){
            counter = stickLength/4;
            if (stickLength % 4 == 0){
                counter -= 1;
            }
        }
        System.out.println(counter);
        scan.close();
    }
}
