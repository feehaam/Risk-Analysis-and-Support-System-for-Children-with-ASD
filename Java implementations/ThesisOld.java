import java.io.*;
import java.nio.file.*;
import java.util.Random;

public class ThesisOld {
    
    public static void main(String[] args) throws InterruptedException {
        
        ThesisOld T = new ThesisOld();
        Random r = new Random();
        
        for (int i = 0; i < 10000; i++) {
//            Thread.sleep(100);
            
            T.gasVal = r.nextInt(1500);
            T.irVal = r.nextInt(111111);
            T.tempVal1 = (double) 30 + r.nextInt(10);
            T.tempVal2 = (double) 30 + r.nextInt(10);
            T.presVal = (double) 30 + r.nextInt(100);
            T.altiVal = (double) 30 + r.nextInt(100);
            T.accVal = (double) 30 + r.nextInt(100);
            T.gyroVal = (double) 30 + r.nextInt(100);
            T.anguVal = (double) 30 + r.nextInt(100);
            T.flameVal = 1;
            T.riskAnalysis();
            
        }
        
    }

    //Number of reading from the sensor
    int readingNumber = 1;

    //All nececssery sensor values
    int gasVal = 0;
    
    long irVal = 0;
    
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
    
    int flameVal = 1;

    //Size of level 1 queue. 
    int l1f = 10;

    //Pointers
    int l1s = 0, l1e = 0, l2s = 0, l2e = 0, l3s = 0, l3e = 0;

    //For Gas sensor
    int l1GasQ[] = new int[l1f];
    int l2GasQ[] = new int[10];
    int l3GasQ[] = new int[10];
    int l1GasA = 1, l2GasA = 1, l3GasA = 1;

    //For IR sensor
    long l1IRQ[] = new long[l1f];
    long l2IRQ[] = new long[10];
    long l3IRQ[] = new long[10];
    long l1IRA = 1, l2IRA = 1, l3IRA = 1;

    //For Temp sensor
    double l1TempQ[] = new double[l1f];
    double l2TempQ[] = new double[10];
    double l3TempQ[] = new double[10];
    double l1TempA = 1, l2TempA = 1, l3TempA = 1;

    //For Pres val
    double l1PresQ[] = new double[l1f];
    double l2PresQ[] = new double[10];
    double l3PresQ[] = new double[10];
    double l1PresA = 1, l2PresA = 1, l3PresA = 1;

    //For Alti sensor
    double l1AltiQ[] = new double[l1f];
    double l2AltiQ[] = new double[10];
    double l3AltiQ[] = new double[10];
    double l1AltiA = 1, l2AltiA = 1, l3AltiA = 1;

    //For Acc sensor
    double l1AccQ[] = new double[l1f];
    double l2AccQ[] = new double[10];
    double l3AccQ[] = new double[10];
    double l1AccA = 1, l2AccA = 1, l3AccA = 1;

    //For Gyro sensor
    double l1GyroQ[] = new double[l1f];
    double l2GyroQ[] = new double[10];
    double l3GyroQ[] = new double[10];
    double l1GyroA = 1, l2GyroA = 1, l3GyroA = 1;

    //For Angu sensor
    double l1AnguQ[] = new double[l1f];
    double l2AnguQ[] = new double[10];
    double l3AnguQ[] = new double[10];
    double l1AnguA = 1, l2AnguA = 1, l3AnguA = 1;
    
    int i;
    int p1 = 0, p2 = 0, p3 = 0;
    
    void updateValues() {
        
        tempVal = (tempVal1 + tempVal2) / 2;

        // Updating level 1 queues. 
        l1GasQ[p1] = gasVal;
        l1IRQ[p1] = irVal;
        l1TempQ[p1] = tempVal;
        l1PresQ[p1] = presVal;
        l1AltiQ[p1] = altiVal;
        l1AccQ[p1] = accVal;
        l1GyroQ[p1] = gyroVal;
        l1AnguQ[p1] = anguVal;
        p1++;
        if (p1 == 10) {
            p1 = 0;
        }
        
        int sumGas = 0;
        long sumIR = 0;
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
//        System.out.println("Reading no: " + readingNumber);
        updateValues();
//
//        // Gas
//        System.out.print("Level 1 GASQ: ");
//        for (int x : l1GasQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.print("Level 2 GasQ: ");
//        for (int x : l2GasQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.print("Level 3 GasQ: ");
//        for (int x : l3GasQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.println("Averages: " + l1GasA + ", " + l2GasA + ", " + l3GasA);
//
//        // IR
//        System.out.print("\nLevel 1 IRQ: ");
//        for (long x : l1IRQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.print("Level 2 IRQ: ");
//        for (long x : l2IRQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.print("Level 3 IRQ: ");
//        for (long x : l3IRQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.println("Averages: " + l1IRA + ", " + l2IRA + ", " + l3IRA);
//
//        // Temp
//        System.out.print("\nLevel 1 TEMPQ: ");
//        for (double x : l1TempQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.print("Level 2 TempQ: ");
//        for (double x : l2TempQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.print("Level 3 TempQ: ");
//        for (double x : l3TempQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.println("Averages: " + l1TempA + ", " + l2TempA + ", " + l3TempA);
//
//        // Temp
//        System.out.print("\nLevel 1 PresQ: ");
//        for (double x : l1PresQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.print("Level 2 PresQ: ");
//        for (double x : l2PresQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.print("Level 3 PrespQ: ");
//        for (double x : l3PresQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.println("Averages: " + l1PresA + ", " + l2PresA + ", " + l3PresA);
//
//        // Alti
//        System.out.print("\nLevel 1 ALTIQ: ");
//        for (double x : l1AltiQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.print("Level 2 AltiQ: ");
//        for (double x : l2AltiQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.print("Level 3 AltiQ: ");
//        for (double x : l3AltiQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.println("Averages: " + l1AltiA + ", " + l2AltiA + ", " + l3AltiA);
//
//        // Acc
//        System.out.print("\nLevel 1 ACCQ: ");
//        for (double x : l1AccQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.print("Level 2 AccQ: ");
//        for (double x : l2AccQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.print("Level 3 AccQ: ");
//        for (double x : l3AccQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.println("Averages: " + l1AccA + ", " + l2AccA + ", " + l3AccA);
//
//        // Gyro
//        System.out.print("\nLevel 1 GYROQ: ");
//        for (double x : l1GyroQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.print("Level 2 GyroQ: ");
//        for (double x : l2GyroQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.print("Level 3 GyroQ: ");
//        for (double x : l3GyroQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.println("Averages: " + l1GyroA + ", " + l2GyroA + ", " + l3GyroA);
//
//        // Angu
//        System.out.print("\nLevel 1 ANGUQ: ");
//        for (double x : l1AnguQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.print("Level 2 AnguQ: ");
//        for (double x : l2AnguQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.print("Level 3 AnguQ: ");
//        for (double x : l3AnguQ) {
//            System.out.print(x + " ");
//        }
//        System.out.println("");
//        System.out.println("Averages: " + l1AnguA + ", " + l2AnguA + ", " + l3AnguA);
//        System.out.println("\nUpdated values: " + l1e + ", " + l2e + ", " + l3e + "\n");
        detect();
//        System.out.println("======================================================");
        readingNumber++;
    } 
   
    int recheck = 0;
    int readingCase = 0;
    double delayTime = 100;
    
    void detect() {
        if (readingNumber < 10) {
            return;
        }

//        1. If average of absolute acceleration is high
//        a) Wait for next 10 sec average
//        b) If previous average was high, reset wait
//        c) If acceleration falls down after wait
//            i) If acceleration matches 1 min average,
//            ii) If accleration is too low, PROBLEM[1]
//        d) If altitude difference is high comparing to last 1 min avg, PROBLEM[2]
//            i) Wait 5 sec, if accelaration drops, PROBLEM[2.2]
        if (Math.abs(getVal(l1AccQ, l1e) - getVal(l1AccQ, l1e - 1)) > 4) {
            recheck = (int) (readingNumber + (1.0 / delayTime) * 10) + 1;
            readingCase = 1;
            
            if (getVal(l2AltiQ, l2e) - l2AltiA > 1.5) {
                risk("Fall from high location");
                recheck = (int) (readingNumber + (1.0 / delayTime) * 10) + 1;
                readingCase = 2;
            }
        }
        
        if (recheck == readingNumber && readingCase == 1) {
            if (getVal(l1AccQ, l1e) - l1AccA < 2) {
                recheck = 0;
                readingCase = 0;
            }
            if (getVal(l1AccQ, l1e) - l1AccA < -2) {
                if (readingNumber > 99 || getVal(l3AccQ, l3e) - getVal(l2AccQ, l2e) > .5) {
                    risk("Fall from somewhere, body not moving");
                }
            }
            recheck = 0;
            readingCase = 0;
        }
        
        if (recheck == readingNumber && readingCase == 2) {
            if (readingNumber > 199) {
                if (getVal(l3AltiQ, l3e) - getVal(l2AltiQ, l2e) > 1.5) {
                    risk("Fall from high location");
                }
            }
            recheck = 0;
            readingCase = 0;
        }

        //3. If heart rate falls down
        //a) If average of 1 min acceleration and current doesn't match, PROBLEM[7]
        //    i) Wait 30 sec, if 30 sec acceleration is low, PROBLEM[7.2]
        //    ii) Wait 30 sec, if heart rate falls down, PROBLEM[7.3]
        if (l1IRA - getVal(l1IRQ, l1e) > 10) {
            recheck = (int) (readingNumber + (1.0 / delayTime) * 30) + 1;
            readingCase = 3;
        }
        if (recheck == readingNumber && readingCase == 3) {
            if (readingNumber > 199 && getVal(l3AccQ, l3e) - getVal(l2AccQ, l2e) > 0.25) {
                risk("Accident");
                recheck = (int) (readingNumber + (1.0 / delayTime) * 30) + 1;
                readingCase = 4;
            }
        }
        
        if (recheck == readingNumber && readingCase == 3) {
            if (getVal(l3AccQ, l3e) - getVal(l3AccQ, l3e - 1) > 0.25) {
                risk("Accident");
            }
            if(getVal(l1IRQ, l1e) < 30000){
                risk("Accident and devicec left off");
            }
            if (getVal(l3IRQ, l3e) - getVal(l2IRQ, l2e) > 10000) {
                risk("Accident and becoming senseless");
            }
            
            recheck = 0;
            readingCase = 0;
        }

        //5. If high change in altitude,
        //a) If pressure high, PROBLEM[10]
        //b) Wait 5 sec
        //    i) If acceleration drops, PROBLEM[11]
        if ((getVal(l1AltiQ, l1e - 1) - getVal(l1AltiQ, l1e)) > 1.9) {
            if ((getVal(l2PresQ, l2e) - getVal(l2PresQ, l1e - 1)) > 12.5) {
                risk("Drawn into water.");
            }
            if ((getVal(l2AccQ, l2e - 1) - getVal(l2AccQ, l2e)) > 0.25) {
                risk("Fall from high location");
            }
        }

        //6. If high change in pressure
        //a) If altitude decreases than 10 sec average, PROBLEM[12]
        //b) If temperature decreases than 10 sec average, PROBLEM[12]
        if (readingNumber > 20 && (Math.abs(getVal(l1PresQ, l1e) - getVal(l1PresQ, l1e - 1)) > 12.5)) {
            //Pressure changes around 12pa by chane of 1m in altitude from sea level.
            //Higher we go, it decreases, Lower we go, it increases. 
            if ((getVal(l2AltiQ, l2e) - getVal(l2AltiQ, l2e - 1)) > 1.9) {
                risk("Drawn into water.");
            }
            if (Math.abs((getVal(l2TempQ, l2e - 1) - getVal(l2TempQ, l2e))) > 1.5) {
                risk("Drawn into water.");
            }
        }

        // 7. If air quality is poor, PROBLEM[13]
        // [13] Got into harmfull and polluted location.
        int gasVal = getVal(l2GasQ, l2e);
        if (gasVal > 700) {
            //In MQ135, CO, CO2, Methene can be detected. 
            //Its detection range is 10-1000ppm
            //While 250+ppm is considered harmful for long time stay
            //750ppm is considered immediate harmful. 
            risk("Got into harmfull and polluted location.");
        }

        //8. If temperature increase
        //a) If acceleration high, PROBLEM[14]
        //b) If acceleration low, PROBLEM[15]
        if ((getVal(l2TempQ, l2e) - getVal(l2TempQ, l2e - 1)) >= 5) {
            
        }
        if (readingNumber > 99) {
            if ((getVal(l3TempQ, l3e) - getVal(l3TempQ, l3e - 1)) >= 2) {
                if ((getVal(l2AccQ, l2e) - getVal(l2AccQ, l2e - 1)) > 0.5) {
                    risk("Got into fire, trying to escape.");
                }
                if ((getVal(l2AccQ, l2e - 1) - getVal(l2AccQ, l2e)) > 0.25) {
                    risk("Got into fire, body not moving.");
                }
            }
        }
        
    }
    
    int getVal(int[] Q, int index) {
        index--;
        if (index < 0) {
            index = 9;
        }
        return Q[index];
    }
    
    double getVal(double[] Q, int index) {
        index--;
        if (index < 0) {
            index = 9;
        }
        return Q[index];
    }
    
    long getVal(long[] Q, int index) {
        index--;
        if (index < 0) {
            index = 9;
        }
        return Q[index];
    }
    
    void risk(String msg) {
        System.out.println(msg);
    }
    
}


/*
----------------RISK ANALYSIS ALGO-----------

IN EACH LOOP

    Average of absolute acceleration = [|X|+|Y|+|Z|/3] 

    1. If average of absolute acceleration is high
        a) Wait for next 5 sec average
        b) If previous average was high, reset wait
        c) If acceleration falls down after wait
            i) If acceleration matches 5 min average,
            ii) If accleration is too low, PROBLEM[1]
        d) If altitude difference is high comparing to last 1 min avg, PROBLEM[2]
            i) Wait 5 sec, if accelaration drops, PROBLEM[2.2]
    2. If average of absolute acceleration is low
        a) If previous acceleration was high, PROBLEM[3]
        b) If heart rate (10s) falls down than 1 min average, PROBLEM[4]
        c) If heart rate (10s) raises up than 1 min average, PROBLEM[5]
        d) If altitude difference is high comparing to last 5 sec avg, PROBLEM[6]
    3. If heart rate falls down
        a) If average of 1 min acceleration and current doesn't match, PROBLEM[7]
            i) Wait 30 sec, if 30 sec AoAA is low, PROBLEM[7.2]
            ii) Wait 30 sec, if heart rate falls down, PROBLEM[7.3]
    4. If heart rate increases up
        a) If average of 1 min acceleration and current doesn't match, PROBLEM[8]
        b) Wait 10 sec.
            i) If heart rate is has more increased, PROBLEM[9]
            ii) Wait 30 sec, If heart rate is has more increased, PROBLEM[9.2]
    5. If high change in altitude,
        a) If pressure high, PROBLEM[10]
        b) Wait 5 sec
            i) If acceleration drops, PROBLEM[11]
    6. If high change in pressure
        a) If altitude decreases than 10 sec average, PROBLEM[12.1]
        b) If temperature decreases than 10 sec average, PROBLEM[12.2]
    7. If air quality is poor, PROBLEM[13]
    8. If temperature increase
        a) If acceleration high, PROBLEM[14]
        b) If acceleration low, PROBLEM[15]

------------------COUGHT PROBLEMS--------------------
1. Falled from somewhere, body not moving - H
2. Falled from high location - M
2.2. Falled from high location - H
3. Same as problem 1
4. Accident and getting senseless - H
5. Accident and afraid M
6. Same as problem 2.2
7. Accident - L
7.2. Accident and devices left off - U
7.3. Accident and becoming senseless - H
8. Running or afraid or playing - L
9. Afraid - L
9. Cardio or fighting - U
9.2. Afraid and stuck somewere (sitting, standing or threaten) - M
10. Drawn into water. - H
11. Fall from high. - H
12.1. Fallen into a hole. - M
12.2. Same as problem 10.
13. Got into harmfull and polluted location.
14. Got into fire, trying to escape. - H
15. Got into fire, body not moving. - Leathal.

------------------TIME FRAME Q ALGO--------------
Level 1 Queue
    size: 10
    ptrs: 2
    time frame: 300ms
int l1q[] = new int[10], l1s = 0, l1e = 0;
add(n){
    l1q[l1ee] = n;
    l1e++;
    if(l1e==10)
        l1e=0;
    if(l1e==l1s)
        l1s++;
}
l1qavg(n){
    int i=l1s;
    while(i+1!=l1e){
        sum(n)
    }
    avg(sum)
}

Level 2 Queue
    size: 10
    time frame: 3s
if(reading_number % 10 == 0)
    add(l1qAvg);
l2qadd(){
    same as l1...
}

Level 3 Queue
    size: 10
    Time frame: 30s
if(reading_number % 100 == 0)
    add(l2qAvg);
l3qadd(){
    same as l1 and l2 add...



 */