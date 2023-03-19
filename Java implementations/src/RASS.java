
import java.util.*;

public class RASS {
    
    //Number of reading from the sensor
    int readingNumber = 1;
    
    //Size of level 1 queue. 
    int l1f = 10;

    //Pointers
    int l1s = 0, l1e = 0, l2s = 0, l2e = 0, l3s = 0, l3e = 0;

    //All nececssery sensor values
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

    //For Gas sensor
    int l1GasQ[] = new int[l1f];
    int l2GasQ[] = new int[10];
    int l3GasQ[] = new int[10];
    // For holding average of each queues
    int l1GasA = 1, l2GasA = 1, l3GasA = 1;

    //For IR sensor
    int l1IRQ[] = new int[l1f];
    int l2IRQ[] = new int[10];
    int l3IRQ[] = new int[10];
    // For holding average of each queues
    int l1IRA = 1, l2IRA = 1, l3IRA = 1;
    
    //For BPM
    int l1BPMQ[] = new int[l1f];
    int l2BPMQ[] = new int[10];
    int l3BPMQ[] = new int[10];
    // For holding average of each queues
    int l1BPMA = 1, l2BPMA = 1, l3BPMA = 1;

    //For Temp sensor
    double l1TempQ[] = new double[l1f];
    double l2TempQ[] = new double[10];
    double l3TempQ[] = new double[10];
    // For holding average of each queues
    double l1TempA = 1, l2TempA = 1, l3TempA = 1;

    //For Pres val
    double l1PresQ[] = new double[l1f];
    double l2PresQ[] = new double[10];
    double l3PresQ[] = new double[10];
    // For holding average of each queues
    double l1PresA = 1, l2PresA = 1, l3PresA = 1;

    //For Alti sensor
    double l1AltiQ[] = new double[l1f];
    double l2AltiQ[] = new double[10];
    double l3AltiQ[] = new double[10];
    // For holding average of each queues
    double l1AltiA = 1, l2AltiA = 1, l3AltiA = 1;

    //For Acc sensor
    double l1AccQ[] = new double[l1f];
    double l2AccQ[] = new double[10];
    double l3AccQ[] = new double[10];
    // For holding average of each queues
    double l1AccA = 1, l2AccA = 1, l3AccA = 1;

    //For Gyro sensor
    double l1GyroQ[] = new double[l1f];
    double l2GyroQ[] = new double[10];
    double l3GyroQ[] = new double[10];
    // For holding average of each queues
    double l1GyroA = 1, l2GyroA = 1, l3GyroA = 1;

    //For Angu sensor
    double l1AnguQ[] = new double[l1f];
    double l2AnguQ[] = new double[10];
    double l3AnguQ[] = new double[10];
    // For holding average of each queues
    double l1AnguA = 1, l2AnguA = 1, l3AnguA = 1;
    
    public static void main(String[] args) {
        Random r = new Random();
        RASS algo = new RASS();
        String file = FFiles.read("C:\\Users\\User\\OneDrive\\Desktop\\Thesis Data\\Sensor data 1.txt");
        String row[] = file.split("\n");
        
        for(int i=0; i<row.length; i++){
            row[i] = row[i].replace("\t", " ");
            String[] values = row[i].split(" ");
            
            for(int j=0; j<values.length; j++){
                if(j==0) algo.readingNumber = Integer.parseInt(values[j]);
                if(j==1) algo.gasVal = Integer.parseInt(values[j]);
                if(j==2) algo.tempVal1 = Double.parseDouble(values[j]);
                if(j==3) algo.presVal = (int) Double.parseDouble(values[j]);
                if(j==4) algo.altiVal = Double.parseDouble(values[j]);
                if(j==5) algo.seaPressVal = (int) Double.parseDouble(values[j]);
                if(j==6) algo.realAltiVal = Double.parseDouble(values[j]);
                if(j==7) algo.irVal = Integer.parseInt(values[j]);
                if(j==8) algo.bpmVal = Integer.parseInt(values[j]);
                if(j==9) algo.tempVal2 = Double.parseDouble(values[j]);
                if(j==10) algo.accVal = Double.parseDouble(values[j]);
                if(j==11) algo.gyroVal = Double.parseDouble(values[j]);
                if(j==12) algo.anguVal = Double.parseDouble(values[j]);
            }
            
            algo.updateValues();
//            algo.printQueus();
            algo.riskAnalysis();
            algo.readingNumber++;
        }
    }
    
    int i;
    int p1 = 0, p2 = 0, p3 = 0;
    void updateValues() {
        
        tempVal = (tempVal1 + tempVal2) / 2;

        // Updating level 1 queues. 
        l1GasQ[p1] = gasVal;
        l1IRQ[p1] = irVal;
        l1BPMQ[p1] = bpmVal;
        l1TempQ[p1] = tempVal;
        l1PresQ[p1] = presVal;
        l1AltiQ[p1] = altiVal;
        l1AccQ[p1] = accVal;
        l1GyroQ[p1] = gyroVal;
        l1AnguQ[p1] = anguVal;
        p1++;
        if (p1 == l1f) {
            p1 = 0;
        }
        
        int sumGas = 0;
        int sumIR = 0;
        int sumBPM = 0;
        double sumTemp = 0;
        double sumPres = 0;
        double sumAlti = 0;
        double sumAcc = 0;
        double sumGyro = 0;
        double sumAngu = 0;
        
        if (l1e == l1f) {
            l1e = -1;
        }
        l1e++;
        if (l1e == l1s) {
            l1s++;
        }
        if (l1e == l1s) {
            l1s++;
        }
        if (l1s == l1f) {
            l1s = 0;
            l1e = 10;
        }
        
        i = l1s;
        int vals = 0;
        while (i != l1e) {
            vals++;
            /////// SUM PART ///
            sumGas += l1GasQ[i];
            sumIR += l1IRQ[i];
            sumBPM += l1BPMQ[i];
            sumTemp += l1TempQ[i];
            sumPres += l1PresQ[i];
            sumAlti += l1AltiQ[i];
            sumAcc += l1AccQ[i];
            sumGyro += l1GyroQ[i];
            sumAngu += l1AnguQ[i];
            ////////////////////
            i++;
            if (i == l1e) {
                break;
            } else if (i == l1f) {
                i = 0;
            }
        }
        if (l1s > l1e) {
            vals++;
            /////// LAST SUM ///
            sumGas += l1GasQ[l1e];
            sumIR += l1IRQ[l1e];
            sumBPM += l1BPMQ[l1e];
            sumTemp += l1TempQ[l1e];
            sumPres += l1PresQ[l1e];
            sumAlti += l1AltiQ[l1e];
            sumAcc += l1AccQ[l1e];
            sumGyro += l1GyroQ[l1e];
            sumAngu += l1AnguQ[l1e];
            ////////////////////
        }
        
        l1GasA = sumGas / vals;
        l1IRA = sumIR / vals;
        l1BPMA = sumBPM / vals;
        l1TempA = sumTemp / vals;
        l1PresA = sumPres / vals;
        l1AltiA = sumAlti / vals;
        l1AccA = sumAcc / vals;
        l1GyroA = sumGyro / vals;
        l1AnguA = sumAngu / vals;

        //Updating level 2 queues
        if (readingNumber % l1f == 0) {
            l2GasQ[p2] = l1GasA;
            l2IRQ[p2] = l1IRA;
            l2BPMQ[p2] = l1BPMA;
            l2TempQ[p2] = l1TempA;
            l2PresQ[p2] = l1PresA;
            l2AltiQ[p2] = l1AltiA;
            l2AccQ[p2] = l1AccA;
            l2GyroQ[p2] = l1GyroA;
            l2AnguQ[p2] = l1AnguA;
            p2++;
            if (p2 == 10) {
                p2 = 0;
            }
            
            sumGas = 0;
            sumIR = 0;
            sumBPM = 0;
            sumTemp = 0;
            sumPres = 0;
            sumAlti = 0;
            sumAcc = 0;
            sumGyro = 0;
            sumAngu = 0;
            
            if (l2e == 10) {
                l2e = -1;
            }
            l2e++;
            if (l2e == l2s) {
                l2s++;
            }
            if (l2e == l2s) {
                l2s++;
            }
            if (l2s == 10) {
                l2s = 0;
                l2e = 10;
            }
            
            i = l2s;
            vals = 0;
            while (i != l2e) {
                vals++;
                /////// SUM PART ///
                sumGas += l2GasQ[i];
                sumIR += l2IRQ[i];
                sumBPM += l2BPMQ[i];
                sumTemp += l2TempQ[i];
                sumPres += l2PresQ[i];
                sumAlti += l2AltiQ[i];
                sumAcc += l2AccQ[i];
                sumGyro += l2GyroQ[i];
                sumAngu += l2AnguQ[i];
                ////////////////////
                i++;
                if (i == l2e) {
                    break;
                } else if (i == 10) {
                    i = 0;
                }
            }
            if (l2s > l2e) {
                vals++;
                /////// LAST SUM ///
                sumGas += l2GasQ[l2e];
                sumIR += l2IRQ[l2e];
                sumBPM += l2BPMQ[l2e];
                sumTemp += l2TempQ[l2e];
                sumPres += l2PresQ[l2e];
                sumAlti += l2AltiQ[l2e];
                sumAcc += l2AccQ[l2e];
                sumGyro += l2GyroQ[l2e];
                sumAngu += l2AnguQ[l2e];
                ////////////////////
            }
            
            l2GasA = sumGas / vals;
            l2IRA = sumIR / vals;
            l2BPMA = sumBPM / vals;
            l2TempA = sumTemp / vals;
            l2PresA = sumPres / vals;
            l2AltiA = sumAlti / vals;
            l2AccA = sumAcc / vals;
            l2GyroA = sumGyro / vals;
            l2AnguA = sumAngu / vals;

            //Updating level 3 queues
            if (readingNumber % (l1f * 10) == 0) {
                l3GasQ[p3] = l2GasA;
                l3IRQ[p3] = l2IRA;
                l3BPMQ[p3] = l2BPMA;
                l3TempQ[p3] = l2TempA;
                l3PresQ[p3] = l2PresA;
                l3AltiQ[p3] = l2AltiA;
                l3AccQ[p3] = l2AccA;
                l3GyroQ[p3] = l2GyroA;
                l3AnguQ[p3] = l2AnguA;
                p3++;
                if (p3 == 10) {
                    p3 = 0;
                }
                
                sumGas = 0;
                sumIR = 0;
                sumBPM = 0;
                sumTemp = 0;
                sumPres = 0;
                sumAlti = 0;
                sumAcc = 0;
                sumGyro = 0;
                sumAngu = 0;
                
                if (l3e == 10) {
                    l3e = -1;
                }
                l3e++;
                if (l3e == l3s) {
                    l3s++;
                }
                if (l3e == l3s) {
                    l3s++;
                }
                if (l3s == 10) {
                    l3s = 0;
                    l3e = 10;
                }
                
                i = l3s;
                vals = 0;
                while (i != l3e) {
                    vals++;
                    /////// SUM PART ///
                    sumGas += l3GasQ[i];
                    sumIR += l3IRQ[i];
                    sumBPM += l3BPMQ[i];
                    sumTemp += l3TempQ[i];
                    sumPres += l3PresQ[i];
                    sumAlti += l3AltiQ[i];
                    sumAcc += l3AccQ[i];
                    sumGyro += l3GyroQ[i];
                    sumAngu += l3AnguQ[i];
                    ////////////////////
                    i++;
                    if (i == l3e) {
                        break;
                    } else if (i == 10) {
                        i = 0;
                    }
                }
                if (l3s > l3e) {
                    vals++;
                    /////// LAST SUM ///
                    sumGas += l3GasQ[l3e];
                    sumIR += l3IRQ[l3e];
                    sumBPM += l3BPMQ[l3e];
                    sumTemp += l3TempQ[l3e];
                    sumPres += l3PresQ[l3e];
                    sumAlti += l3AltiQ[l3e];
                    sumAcc += l3AccQ[l3e];
                    sumGyro += l3GyroQ[l3e];
                    sumAngu += l3AnguQ[l3e];
                    ////////////////////
                }
                
                l3GasA = sumGas / vals;
                l3IRA = sumIR / vals;
                l3BPMA = sumBPM / vals;
                l3TempA = sumTemp / vals;
                l3PresA = sumPres / vals;
                l3AltiA = sumAlti / vals;
                l3AccA = sumAcc / vals;
                l3GyroA = sumGyro / vals;
                l3AnguA = sumAngu / vals;
            }
        }
    }
    
    void riskAnalysis() {
        if(readingNumber < 100) return;
        
        /*
        Critical conditions
        if temperature > 45 degree alarm
        if gas > 400 alarm
        if accelartion > 6.0 alarm
        if angular rotation > 1500 alarm (free fall)
        if bpm > 130 or bpm < 60 alarm
        
        Other parameters doesn't have a specific critical limit
        */
        
        if(tempVal > 45){
            risk("Got into extreme high temperature!");
        }
        if(gasVal > 400) {
            risk("Got into dangerous air!");
            //In MQ135, CO, CO2, Methene can be detected. 
            //Its detection range is 10-1000ppm
            //While 250+ppm is considered harmful for long time stay
            //Abouve 400ppm in dangerous
            //750ppm is considered immediate harmful. 
        }
        if(accVal > 6){
            risk("Falling from above!");
        }
        if(anguVal > 1500) {
            risk("Falling too fast!");
        }
        if(bpmVal < 60 || bpmVal > 130){
            risk("Inapropriate heart beat!");
        }
        
        //If temperature increase, High temperature
        //a) If acceleration high, Got into fire, trying to escape
        //b) If acceleration low, Got into fire, body not moving.
        //If gas toxicity > 400, Entering a harmful or polluted location
        if(Math.abs(getVal(l1TempQ, l1e) - getVal(l2TempQ, l2e)) >= 4){
            risk("Got into high temperature");
            if(l1AccA - getVal(l2AccQ, l2e - 1) >= .4){
                risk("Entering a fire and attempting to escape");
            }
            else if(l1AccA - getVal(l2AccQ, l2e - 1) <= -.4){
                risk("Body not moving after entering a fire");
            }
        }
        if(gasVal > 400) {
            risk("Got into very harmful location!");
        }
        else if(l1GasA - getVal(l2GasQ, l2e - 1) > 30){
            risk("Entering a harmful or polluted location!");
        }
        
        //If high change in pressure, High change in pressure
        //a) If altitude decreases than 10 sec average, Fall into hole.
        //b) If temperature decreases than 10 sec average, Drawn into water.
        //c) If altitude doen't change than 10 sec average, Got into high pressure environment
        if (Math.abs(l1PresA - getVal(l2PresQ, l2e-1)) > 24) {
            risk("High change in pressure.");
            //Pressure changes around 12pa by chane of 1m in altitude from sea level.
            //Higher we go, it decreases, Lower we go, it increases. 
            if (Math.abs((l1AltiA - getVal(l2AltiQ, l2e))) > 1.5) {
                risk("Fallen into hole.");
            }
        }
        if (Math.abs((l1TempA - getVal(l2TempQ, l2e))) > 1) {
            System.out.println("Watering...");
            System.out.println(getVal(l2AltiQ, l2e)+" vs "+l1AltiA);
            if(getVal(l2AltiQ, l2e) - l1AltiA > 1.5)
                risk("Drawn into water.");
        }
        //If high change in altitude,
        //a) If pressure high, Drawn into water.
        //b) If acceleration increase, Fall from high location
        //c) If angular rotation increase, Fall from high location
        if (Math.abs(l1AltiA - getVal(l2AltiQ, l2e)) > 1.9) {
            risk("High change in altitude");
            if (Math.abs(l1PresA - getVal(l2PresQ, l2e-1)) > 12.5) {
                risk("Drawn into water.");
            }
            if (Math.abs(l1AccA - getVal(l2AccQ, l2e-1)) > 0.25) {
                risk("Fall from high location");
            }
            if(Math.abs(l1AnguA - getVal(l2AnguQ, l2e-1)) > 1000) {
                risk("Fall from high location");
            }
        }
        
        //If heart rate falls down
        //a) If heart rate is too low, machine trown off
        //b) Acceleration increase, Feared
        //c) Acceleration decrease, Accident
        if(l1BPMA < 10){
            risk("Device left off");
        }
        else if(l1BPMA - getVal(l2BPMQ, l2e-1) < -10){
            risk("Heart rate falling down.");
            if (l1AccA - getVal(l2AccQ, l2e-1) < 0.25) {
                risk("Accident resulting in unconsciousness");
            }
        }
        //If heart rate increase
        //b) Acceleration increase, Feared
        //c) Acceleration decrease, Accident
        else if(l1BPMA - getVal(l2BPMQ, l1e-1) > 10){
            risk("Fear.");
            if (l1AccA - getVal(l2AccQ, l2e-1) > 0.25) {
                risk("Accident causing fear, moving fast");
            }
            else if (l1AccA - getVal(l2AccQ, l2e-1) < 0.25) {
                risk("Fear and stuck in a location");
            }
        }
    } 
    
    void printQueus(){
        System.out.println("Reading no: " + readingNumber);

        // Gas
        System.out.print("Level 1 GASQ: ");
        for (int x : l1GasQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.print("Level 2 GasQ: ");
        for (int x : l2GasQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.print("Level 3 GasQ: ");
        for (int x : l3GasQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.println("Averages: " + l1GasA + ", " + l2GasA + ", " + l3GasA);

        // IR
        System.out.print("\nLevel 1 IRQ: ");
        for (long x : l1IRQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.print("Level 2 IRQ: ");
        for (long x : l2IRQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.print("Level 3 IRQ: ");
        for (long x : l3IRQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.println("Averages: " + l1IRA + ", " + l2IRA + ", " + l3IRA);
        
        // BPM
        System.out.print("\nLevel 1 BPMQ: ");
        for (long x : l1BPMQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.print("Level 2 BPMQ: ");
        for (long x : l2BPMQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.print("Level 3 BPMQ: ");
        for (long x : l3BPMQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.println("Averages: " + l1BPMA + ", " + l2BPMA + ", " + l3BPMA);

        // Temp
        System.out.print("\nLevel 1 TEMPQ: ");
        for (double x : l1TempQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.print("Level 2 TempQ: ");
        for (double x : l2TempQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.print("Level 3 TempQ: ");
        for (double x : l3TempQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.println("Averages: " + l1TempA + ", " + l2TempA + ", " + l3TempA);

        // Temp
        System.out.print("\nLevel 1 PresQ: ");
        for (double x : l1PresQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.print("Level 2 PresQ: ");
        for (double x : l2PresQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.print("Level 3 PrespQ: ");
        for (double x : l3PresQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.println("Averages: " + l1PresA + ", " + l2PresA + ", " + l3PresA);

        // Alti
        System.out.print("\nLevel 1 ALTIQ: ");
        for (double x : l1AltiQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.print("Level 2 AltiQ: ");
        for (double x : l2AltiQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.print("Level 3 AltiQ: ");
        for (double x : l3AltiQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.println("Averages: " + l1AltiA + ", " + l2AltiA + ", " + l3AltiA);

        // Acc
        System.out.print("\nLevel 1 ACCQ: ");
        for (double x : l1AccQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.print("Level 2 AccQ: ");
        for (double x : l2AccQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.print("Level 3 AccQ: ");
        for (double x : l3AccQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.println("Averages: " + l1AccA + ", " + l2AccA + ", " + l3AccA);

        // Gyro
        System.out.print("\nLevel 1 GYROQ: ");
        for (double x : l1GyroQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.print("Level 2 GyroQ: ");
        for (double x : l2GyroQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.print("Level 3 GyroQ: ");
        for (double x : l3GyroQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.println("Averages: " + l1GyroA + ", " + l2GyroA + ", " + l3GyroA);

        // Angu
        System.out.print("\nLevel 1 ANGUQ: ");
        for (double x : l1AnguQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.print("Level 2 AnguQ: ");
        for (double x : l2AnguQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.print("Level 3 AnguQ: ");
        for (double x : l3AnguQ) {
            System.out.print(x + " ");
        }
        System.out.println("");
        System.out.println("Averages: " + l1AnguA + ", " + l2AnguA + ", " + l3AnguA);
        System.out.println("\nUpdated values: " + l1e + ", " + l2e + ", " + l3e + "\n");

        System.out.println("======================================================");
    }
    
    int getVal(int[] Q, int index) {
        if(index > 9) index -= 9;
        if(index < 0) index += 9;
        return Q[index];
    }
    
    double getVal(double[] Q, int index) {
        if(index > 9) index -= 9;
        if(index < 0) index += 9;
        return Q[index];
    }
    
    Set<String> riskList = new HashSet<String>();
    void risk(String msg) {
        System.out.println("Risk: "+msg+" @reading #"+readingNumber);
        if(!riskList.contains(msg)){
              risks += "[>] "+msg+" @reading #"+readingNumber+"\n";
              riskList.add(msg);
        }
    }
    
    String risks = "";
    public String getRisks(){
          String temp = risks;
          risks = "";
          return temp;
    }
}