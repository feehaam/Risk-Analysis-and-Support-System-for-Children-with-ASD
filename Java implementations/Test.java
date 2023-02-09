
/**
 *
 * @author User
 */
public class Test {
    public static void main(String[] args) {
        lookBack(42);
    }
    
    static void lookBack(int sec){
        int steps = sec * 4;
        int e1 = 8, e2 = 6, e3 = 2;
        if(e1 >= steps){
            System.out.println("In Level 1: "+(e1 - steps));
            return;
        }
        else{
            steps = Math.abs(e1 - steps);
        }
        System.out.println("steps = "+steps);
        steps = steps / 10;
        if(e2 >= steps){
            System.out.println("In Level 2: "+(e2 - steps));
            return;
        }
        else{
            steps = Math.abs(e2 - steps);
        }
        System.out.println("steps = "+steps);
        steps = steps / 10;
        if(e3 >= steps){
            System.out.println("In Level 3: "+(e3 - steps));
            return;
        }
    }
}
