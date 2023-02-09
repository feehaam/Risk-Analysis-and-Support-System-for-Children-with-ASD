//LIBRARIES
#include <Wire.h>
//Motions er jonno
  #include <MPU6050_tockn.h>
//BMP er jonno
  #include <Adafruit_BMP085.h>
  #define seaLevelPressure_hPa 1013.25


//OBJECTS
  //Motion er object banaye nite hobe
  MPU6050 mpu6050(Wire);
  //Bio er object banaye nite hobe
  //BMP er object
  Adafruit_BMP085 bmp;

//INITIALS
  int readingNumber = 1;
  //Bio er initials
  const byte RATE_SIZE = 4; //Increase this for more averaging. 4 is good.
  byte rates[RATE_SIZE]; //Array of heart rates
  byte rateSpot = 0;
  long lastBeat = 0; //Time at which the last beat occurred
  float beatsPerMinute;
  int beatAvg;
  //Smoke er initials
  float env = 0.0;


//VALUES
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
#define l1f 10

//Pointers
int l1s = 0, l1e = 0, l2s = 0, l2e = 0, l3s = 0, l3e = 0;

  //For Gas sensor
  int l1GasQ[l1f];
  int l2GasQ[10];
  int l3GasQ[10];
  int l1GasA = 1, l2GasA = 1, l3GasA = 1;

  //For IR sensor
  int l1IRQ[l1f];
  int l2IRQ[10];
  int l3IRQ[10];
  int l1IRA = 1, l2IRA = 1, l3IRA = 1;

  //For Temp sensor
  int l1TempQ[l1f];
  int l2TempQ[10];
  int l3TempQ[10];
  int l1TempA = 1, l2TempA = 1, l3TempA = 1;

  //For Pres val
  double l1PresQ[l1f];
  double l2PresQ[10];
  double l3PresQ[10];
  double l1PresA = 1, l2PresA = 1, l3PresA = 1;

  //For Alti sensor
  double l1AltiQ[l1f];
  double l2AltiQ[10];
  double l3AltiQ[10];
  double l1AltiA = 1, l2AltiA = 1, l3AltiA = 1;

  //For Acc sensor
  double l1AccQ[l1f];
  double l2AccQ[10];
  double l3AccQ[10];
  double l1AccA = 1, l2AccA = 1, l3AccA = 1;
  
int i;
    int p1 = 0, p2 = 0, p3 = 0;

    void getVal(int ar[]){
      Serial.println(ar[0]);
    }

    void updateValues() {

        tempVal = (tempVal1 + tempVal2) / 2;

        // Updating level 1 queues. 
        l1GasQ[p1] = gasVal;
        l1IRQ[p1] = irVal;
        l1TempQ[p1] = tempVal;
        l1PresQ[p1] = presVal;
        l1AltiQ[p1] = altiVal;
        l1AccQ[p1] = accVal;
        //l1GyroQ[p1] = gyroVal;
        //l1AnguQ[p1] = anguVal;
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
            //sumGyro += l1GyroQ[i];
            //sumAngu += l1AnguQ[i];
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
            //sumGyro += l1GyroQ[l1e];
            //sumAngu += l1AnguQ[l1e];
            ////////////////////
        }

        l1GasA = sumGas / vals;
        l1IRA = sumIR / vals;
        l1TempA = sumTemp / vals;
        l1PresA = sumPres / vals;
        l1AltiA = sumAlti / vals;
        l1AccA = sumAcc / vals;
        //l1GyroA = sumGyro / vals;
        //l1AnguA = sumAngu / vals;

        //Updating level 2 queues
        if (readingNumber % l1f == 0) {
            l2GasQ[p2] = l1GasA;
            l2IRQ[p2] = l1IRA;
            l2TempQ[p2] = l1TempA;
            l2PresQ[p2] = l1PresA;
            l2AltiQ[p2] = l1AltiA;
            l2AccQ[p2] = l1AccA;
            //l2GyroQ[p2] = l1GyroA;
            //l2AnguQ[p2] = l1AnguA;
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
                //sumGyro += l2GyroQ[i];
                //sumAngu += l2AnguQ[i];
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
                //sumGyro += l2GyroQ[l2e];
                //sumAngu += l2AnguQ[l2e];
                ////////////////////
            }

            l2GasA = sumGas / vals;
            l2IRA = sumIR / vals;
            l2TempA = sumTemp / vals;
            l2PresA = sumPres / vals;
            l2AltiA = sumAlti / vals;
            l2AccA = sumAcc / vals;
            //l2GyroA = sumGyro / vals;
            //l2AnguA = sumAngu / vals;

            //Updating level 3 queues
            if (readingNumber % (l1f * 10) == 0) {
                l3GasQ[p3] = l2GasA;
                l3IRQ[p3] = l2IRA;
                l3TempQ[p3] = l2TempA;
                l3PresQ[p3] = l2PresA;
                l3AltiQ[p3] = l2AltiA;
                l3AccQ[p3] = l2AccA;
                //l3GyroQ[p3] = l2GyroA;
                //l3AnguQ[p3] = l2AnguA;
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
                    //sumGyro += l3GyroQ[i];
                    //sumAngu += l3AnguQ[i];
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
                    //sumGyro += l3GyroQ[l3e];
                    //sumAngu += l3AnguQ[l3e];
                    ////////////////////
                }

                l3GasA = sumGas / vals;
                l3IRA = sumIR / vals;
                l3TempA = sumTemp / vals;
                l3PresA = sumPres / vals;
                l3AltiA = sumAlti / vals;
                l3AccA = sumAcc / vals;
                //l3GyroA = sumGyro / vals;
                //l3AnguA = sumAngu / vals;

            }
        }
        getVal(l1GasQ);
    }


void printGas(){
  Serial.print("\nLevel 1 GasQ: ");
  for(int i=0; i<l1f; i++){
    Serial.print(l1GasQ[i]);
    Serial.print(" ");
  }
  Serial.print("\nLevel 2 GasQ: ");
  for(int i=0; i<10; i++){
    Serial.print(l2GasQ[i]);
    Serial.print(" ");
  }
  Serial.print("\nLevel 3 GasQ: ");
  for(int i=0; i<10; i++){
    Serial.print(l3GasQ[i]);
    Serial.print(" ");
  }
  Serial.println();
  Serial.println("Average: ");
  Serial.print(l1GasA);
  Serial.print(" ");
  Serial.print(l2GasA);
  Serial.print(" ");
  Serial.print(l3GasA);
  Serial.println();

}

void printTemp(){
  Serial.print("\nLevel 1 TempQ: ");
  for(int i=0; i<l1f; i++){
    Serial.print(l1TempQ[i]);
    Serial.print(" ");
  }
  Serial.print("\nLevel 2 TempQ: ");
  for(int i=0; i<10; i++){
    Serial.print(l2TempQ[i]);
    Serial.print(" ");
  }
  Serial.print("\nLevel 3 TempQ: ");
  for(int i=0; i<10; i++){
    Serial.print(l3TempQ[i]);
    Serial.print(" ");
  }
  Serial.println();
  Serial.println("Average: ");
  Serial.print(l1TempA);
  Serial.print(" ");
  Serial.print(l2TempA);
  Serial.print(" ");
  Serial.print(l3TempA);
  Serial.println();
}

void printAlti(){
  Serial.print("\nLevel 1 AltiQ: ");
  for(int i=0; i<l1f; i++){
    Serial.print(l1AltiQ[i]);
    Serial.print(" ");
  }
  Serial.print("\nLevel 2 AltiQ: ");
  for(int i=0; i<10; i++){
    Serial.print(l2AltiQ[i]);
    Serial.print(" ");
  }
  Serial.print("\nLevel 3 AltiQ: ");
  for(int i=0; i<10; i++){
    Serial.print(l3AltiQ[i]);
    Serial.print(" ");
  }
  Serial.println();
  Serial.println("Average: ");
  Serial.print(l1AltiA);
  Serial.print(" ");
  Serial.print(l2AltiA);
  Serial.print(" ");
  Serial.print(l3AltiA);
  Serial.println();

}

void printAcc(){
  Serial.print("\nLevel 1 AccQ: ");
  for(int i=0; i<l1f; i++){
    Serial.print(l1AccQ[i]);
    Serial.print(" ");
  }
  Serial.print("\nLevel 2 AccQ: ");
  for(int i=0; i<10; i++){
    Serial.print(l2AccQ[i]);
    Serial.print(" ");
  }
  Serial.print("\nLevel 3 AccQ: ");
  for(int i=0; i<10; i++){
    Serial.print(l3AccQ[i]);
    Serial.print(" ");
  }
  Serial.println();
  Serial.println("Average: ");
  Serial.print(l1AccA);
  Serial.print(" ");
  Serial.print(l2AccA);
  Serial.print(" ");
  Serial.print(l3AccA);
  Serial.println();

}

void printPres(){
  Serial.print("\nLevel 1 PresQ: ");
  for(int i=0; i<l1f; i++){
    Serial.print(l1PresQ[i]);
    Serial.print(" ");
  }
  Serial.print("\nLevel 2 PresQ: ");
  for(int i=0; i<10; i++){
    Serial.print(l2PresQ[i]);
    Serial.print(" ");
  }
  Serial.print("\nLevel 3 PresQ: ");
  for(int i=0; i<10; i++){
    Serial.print(l3PresQ[i]);
    Serial.print(" ");
  }
  Serial.println();
  Serial.println("Average: ");
  Serial.print(l1PresA);
  Serial.print(" ");
  Serial.print(l2PresA);
  Serial.print(" ");
  Serial.print(l3PresA);
  Serial.println();

}

void riskAnalysis(){
  updateValues();
  printAcc();
  
  

  

  
  
  
}

void setup() {
  //Output buffer er bandwidth thik kore dite hobe
  Serial.begin(115200);

  Serial.println("SETTING UP...");
  
  // Motion setup
  Wire.begin();
  mpu6050.begin();
  Serial.println("  -> Motion sensor has started.");

  // Inititalize BMP sensor
  if (!bmp.begin()) {
    Serial.println("  -> Could not find a valid BMP085 sensor.");
    while (1) {}
  }
  else{
    Serial.println("  -> BMP085 has started");
  }

  //Gas setup
  pinMode(13, OUTPUT);
  pinMode(2, INPUT);

  delay(2000);
  Serial.println("\n\n====================================================");
  Serial.println("==================SETUP COMPLETE!===================");
  Serial.println("====================================================\n\n");
}

void loop() {
  Serial.print("\n=============READING #");
  Serial.print(readingNumber);
  Serial.println("=============");
  
  motion();
  envi1();
  envi2();
  riskAnalysis();
  readingNumber++;
  
  delay(300);
}

//Environment (BMP) sensor values
void envi1(){
  Serial.println("\n  BMP readings");
  Serial.print("    -> Temperature = ");
    tempVal1 = bmp.readTemperature();
    Serial.print(tempVal1);
    Serial.println(" *C");
    
    Serial.print("    -> Pressure = ");
    presVal = bmp.readPressure();
    Serial.print(presVal);
    Serial.println(" Pa");

    Serial.print("    -> Altitude = ");
    altiVal = bmp.readAltitude();
    Serial.print(altiVal);
    Serial.println(" meters");

    Serial.print("    -> Pressure at sealevel = ");
    seaPressVal = bmp.readSealevelPressure();
    Serial.print(seaPressVal);
    Serial.println(" Pa");

    Serial.print("    -> Real altitude = ");
    realAltiVal = bmp.readAltitude(seaLevelPressure_hPa * 100);
    Serial.print(realAltiVal);
    Serial.println(" meters");
}



//Motion sensor values
void motion(){
  mpu6050.update();
  Serial.println("\n  Motion readings");
  Serial.print("    -> Temp : ");
  tempVal2 = mpu6050.getTemp();
  Serial.println(tempVal2);

  accVal = abs(mpu6050.getAccX()) + abs(mpu6050.getAccY()) + abs(mpu6050.getAccZ());
  Serial.print("    -> ABS ACC: ");
  Serial.println(accVal);

  gyroVal = abs(mpu6050.getGyroX()) + abs(mpu6050.getGyroY()) + abs(mpu6050.getGyroZ());
  Serial.print("    -> ABS GYRO: ");
  Serial.println(gyroVal);
  
  anguVal = abs(mpu6050.getAngleX()) + abs(mpu6050.getAngleY()) + abs(mpu6050.getAngleZ());
  Serial.print("    -> ABS ANGLE: ");
  Serial.println(anguVal);
}

void envi2(){
  Serial.println("\n  Gas readings");
  gasVal = analogRead(0); // read analog input pin 0
  int digitalValue = digitalRead(2);
  Serial.print("    -> Air quality = "); 
  Serial.print(gasVal);
  Serial.println(" PPM"); 
}
