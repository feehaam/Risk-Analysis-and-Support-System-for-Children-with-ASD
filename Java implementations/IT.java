import java.util.*;
public class IT {
    static LinkedList<Double> T = new LinkedList();
    public static void main(String[] args) {
        Random r = new Random();
        LinkedList<Double> D = new LinkedList();
        System.out.println("SI (250ms interval)\tTemperature\tAverage");
        double temp = 28.5;
        T.add(28.5);
        for(int i=1; i<=30; i++){
            System.out.println(i+"\t"+String.format("%.2f", temp)+"\t"+average());
            int x = 1500;
            if(i>17 && i<26) x = 15000;
            if(i > 24) x = -500;
            double dif = ((double) (r.nextInt(500)+x)) / 10000;
            D.add(Double.parseDouble(average()) - temp);
            temp += dif;
            T.add(temp);
        }
        
        for(double x: D)
            System.out.println(x);
    }
    
    static String average(){
        double avg = 0;
        for(int i=0; i<T.size(); i++){
            avg += T.get(i);
        }
        return String.format("%.2f", avg/T.size());
    }
}


/*
TEMP ALL
import java.util.*;
public class IT {
    static LinkedList<Double> T = new LinkedList();
    public static void main(String[] args) {
        Random r = new Random();
        LinkedList<Double> D = new LinkedList();
        System.out.println("SI (250ms interval)\tTemperature\tAverage");
        double temp = 22.13;
        T.add(22.13);
        for(int i=1; i<=30; i++){
            System.out.println(i+"\t"+String.format("%.2f", temp)+"\t"+average());
            int x = 1500;
            if(i>13 && i<22) x = 3000;
            if(i > 24) x = -500;
            double dif = ((double) (r.nextInt(1000)+x)) / 10000;
            D.add(Double.parseDouble(average()) - temp);
            temp += dif;
            T.add(temp);
        }
        
        for(double x: D)
            System.out.println(x);
    }
    
    static String average(){
        double avg = 0;
        for(int i=0; i<T.size(); i++){
            avg += T.get(i);
        }
        return String.format("%.2f", avg/T.size());
    }
}

*/