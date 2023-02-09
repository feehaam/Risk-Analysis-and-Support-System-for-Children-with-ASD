
import java.util.Random;

public class Datagen {
    public static void main(String[] args) {
        String file = FFiles.read("C:\\Users\\User\\OneDrive\\Desktop\\Thesis Data\\Sensor data 1.txt");
        String row[] = file.split("\n");
        
        int readingNumber = 0;
        int gasVal = 0;
        int irVal = 0;
        int bpmVal = 0;
        double tempVal = 0.0;
        double tempVal1 = 0.0;
        double presVal = 0.0;
        double altiVal = 0.0;
        double seaPressVal = 0.0;
        double realAltiVal = 0.0;
        double tempVal2 = 0.0;
        double accVal = 0.0;
        double gyroVal = 0.0;
        double anguVal = 0.0;
        
        Random r = new Random();
        
        for(int i=0; i<3000; i++){
            row[i] = row[i].replace("\t", " ");
            String[] values = row[i].split(" ");
            
            for(int j=0; j<values.length; j++){
                if(j==0) readingNumber = Integer.parseInt(values[j]);
                if(j==1) gasVal = Integer.parseInt(values[j]);
                if(j==2) tempVal1 = Double.parseDouble(values[j]);
                if(j==3) presVal = Integer.parseInt(values[j]);
                if(j==4) altiVal = Double.parseDouble(values[j]);
                if(j==5) seaPressVal = Integer.parseInt(values[j]);
                if(j==6) realAltiVal = Double.parseDouble(values[j]);
                if(j==7) irVal = Integer.parseInt(values[j]);
                if(j==8) bpmVal = Integer.parseInt(values[j]);
                if(j==9) tempVal2 = Double.parseDouble(values[j]);
                if(j==10) accVal = Double.parseDouble(values[j]);
                if(j==11) gyroVal = Double.parseDouble(values[j]);
                if(j==12) anguVal = Double.parseDouble(values[j]);
            }
            
            gasVal += 21+r.nextInt(4);
            tempVal1 += 4 + (r.nextBoolean() ? .1 : -.1);
            tempVal1 = Double.parseDouble(String.format("%.1f", tempVal1));
            double dif = Math.abs(r.nextDouble()/5) + 25; 
            presVal -= dif * 12.137;
            altiVal -= dif;
            realAltiVal -= dif;
            seaPressVal += r.nextInt(10);
            presVal = Double.parseDouble(String.format("%.0f", presVal));
            seaPressVal = Double.parseDouble(String.format("%.0f", seaPressVal));
            altiVal = Double.parseDouble(String.format("%.1f", altiVal));
            realAltiVal = Double.parseDouble(String.format("%.1f", realAltiVal));
            int d = r.nextInt(2) + 5;
            irVal += 1100 * d;
            bpmVal += d;
            tempVal2 += 3.5 + (r.nextBoolean() ? .1 : -.1);
            tempVal2 = Double.parseDouble(String.format("%.1f", tempVal2));
            accVal += .4 + (r.nextBoolean() ? .1 : -.1);
            accVal = Double.parseDouble(String.format("%.2f", accVal));
            gyroVal += r.nextInt(10) +  r.nextDouble();
            gyroVal = Double.parseDouble(String.format("%.2f", gyroVal));
            anguVal += r.nextInt(50) +  400;
            anguVal = Double.parseDouble(String.format("%.2f", anguVal));
            
            
            
            System.out.println(readingNumber+"\t"+gasVal+"\t"+tempVal1+"\t"+presVal+"\t"+altiVal+"\t"+seaPressVal
            +"\t"+realAltiVal+"\t"+irVal+"\t"+bpmVal+"\t"+tempVal2+"\t"+accVal+"\t"+gyroVal+"\t"+anguVal);
        }
    }
}
//25.54
