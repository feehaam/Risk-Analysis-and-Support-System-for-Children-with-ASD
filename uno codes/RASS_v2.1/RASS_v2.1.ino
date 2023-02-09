//LIBRARIES
#include <Wire.h>
//Motions er jonno
  #include <MPU6050_tockn.h>
//Bio er jonno
  #include "MAX30105.h"
  #include "heartRate.h"
//BMP er jonno
  #include <Adafruit_BMP085.h>
  #define seaLevelPressure_hPa 1013.25


//OBJECTS
  //Motion er object banaye nite hobe
  MPU6050 mpu6050(Wire);
  //Bio er object banaye nite hobe
  MAX30105 particleSensor;
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
  
void updateValues() {
    tempVal = (tempVal1 + tempVal2) / 2;

    //Updating level 1 queues
    l1GasQ[l1e] = gasVal;
    l1IRQ[l1e] = irVal;
    l1TempQ[l1e] = tempVal;
    l1PresQ[l1e] = presVal;
    l1AltiQ[l1e] = altiVal;
    l1AccQ[l1e] = accVal;

    l1e++;
    if (l1e >= l1f) {
        l1e = 0;
    }
    if (l1e == l1s) {
        l1s++;
    }
    if (l1s == l1f) {
        l1s = 0;
    }

    int sumGas = 0;
    int sumIR = 0;
    int sumTemp = 0;
    int sumPres = 0;
    int sumAlti = 0;
    int sumAcc = 0;
    int sumGyro = 0;
    int sumAngu = 0;

    int i = l1s;
    while (i != l1e) {
        sumGas += l1GasQ[i];
        sumIR += l1IRQ[i];
        sumTemp += l1TempQ[i];
        sumPres += l1PresQ[i];
        sumAlti += l1AltiQ[i];
        sumAcc += l1AccQ[i];
        i++;
        if (i == l1f) {
            i = 0;
        }
    }
    if (l1s < l1e) {
        l1GasA = sumGas / (l1e - l1s);
        l1IRA = sumIR / (l1e - l1s);
        l1TempA = sumTemp / (l1e - l1s);
        l1PresA = sumPres / (l1e - l1s);
        l1AltiA = sumAlti / (l1e - l1s);
        l1AccA = sumAcc / (l1e - l1s);
    } else {
        l1GasA = sumGas / l1f;
        l1IRA = sumIR / l1f;
        l1TempA = sumTemp / l1f;
        l1PresA = sumPres / l1f;
        l1AltiA = sumAlti / l1f;
        l1AccA = sumAcc / l1f;
    }

    //Updating level 2 queues
    if (readingNumber % l1f == 0) {
        l2GasQ[l2e] = l1GasA;
        l2IRQ[l2e] = l1IRA;
        l2TempQ[l2e] = l1TempA;
        l2PresQ[l2e] = l1PresA;
        l2AltiQ[l2e] = l1AltiA;
        l2AccQ[l2e] = l1AccA;

        l2e++;
        if (l2e >= 10) {
            l2e = 0;
        }
        if (l2e == l2s) {
            l2s++;
        }
        if (l2s == 10) {
            l2s = 0;
        }
        // 0,0 - 0,9 - (0,10>0,0>1,0) 2,1 - 9,8 - (10,9>0,9) - 1,0 
        sumGas = 0;
        sumIR = 0;
        sumTemp = 0;
        sumPres = 0;
        sumAlti = 0;
        sumAcc = 0;
        sumGyro = 0;
        sumAngu = 0;
        i = l2s;
        while (i != l2e) {
            sumGas += l2GasQ[i];
            sumIR += l2IRQ[i];
            sumTemp += l2TempQ[i];
            sumPres += l2PresQ[i];
            sumAlti += l2AltiQ[i];
            sumAcc += l2AccQ[i];
            i++;
            if (i == 10) {
                i = 0;
            }
        }
        if (l2s < l2e) {
            l2GasA = sumGas / (l2e - l2s);
            l2IRA = sumIR / (l2e - l2s);
            l2TempA = sumTemp / (l2e - l2s);
            l2PresA = sumPres / (l2e - l2s);
            l2AltiA = sumAlti / (l2e - l2s);
            l2AccA = sumAcc / (l2e - l2s);
        } else {
            l2GasA = sumGas / 10;
            l2IRA = sumIR / 10;
            l2TempA = sumTemp / 10;
            l2PresA = sumPres / 10;
            l2AltiA = sumAlti / 10;
            l2AccA = sumAcc / 10;
        }

        //Updating level 3 queues
        if (readingNumber % (l1f * 10) == 0) {
            l3GasQ[l3e] = l2GasA;
            l3IRQ[l3e] = l2IRA;
            l3TempQ[l3e] = l2TempA;
            l3PresQ[l3e] = l2PresA;
            l3AltiQ[l3e] = l2AltiA;
            l3AccQ[l3e] = l2AccA;

            l3e++;
            if (l3e >= 10) {
                l3e = 0;
            }
            if (l3e == l3s) {
                l3s++;
            }
            if (l3s == 10) {
                l3s = 0;
            }
            sumGas = 0;
            sumIR = 0;
            sumTemp = 0;
            sumPres = 0;
            sumAlti = 0;
            sumAcc = 0;
            sumGyro = 0;
            sumAngu = 0;
            i = l3s;
            while (i != l3e) {
                sumGas += l3GasQ[i];
                sumIR += l3IRQ[i];
                sumTemp += l3TempQ[i];
                sumPres += l3PresQ[i];
                sumAlti += l3AltiQ[i];
                sumAcc += l3AccQ[i];
                i++;
                if (i == 10) {
                    i = 0;
                }
            }
            if (l3s < l3e) {
                l3GasA = sumGas / (l3e - l3s);
                l3IRA = sumIR / (l3e - l3s);
                l3TempA = sumTemp / (l3e - l3s);
                l3PresA = sumPres / (l3e - l3s);
                l3AltiA = sumAlti / (l3e - l3s);
                l3AccA = sumAcc / (l3e - l3s);
            } else {
                l3GasA = sumGas / 10;
                l3IRA = sumIR / 10;
                l3TempA = sumTemp / 10;
                l3PresA = sumPres / 10;
                l3AltiA = sumAlti / 10;
                l3AccA = sumAcc / 10;
            }
        }
    }
}

void riskAnalysis(){
  updateValues();
  Serial.print("\nLevel 1 GASQ: ");
  for(int i=0; i<l1f; i++){
    Serial.print(l1GasQ[i]+" ");
  }
  Serial.print("\nLevel 2 GASQ: ");
  for(int i=0; i<10; i++){
    Serial.print(l2GasQ[i]+" ");
  }
  Serial.print("\nLevel 3 GASQ: ");
  for(int i=0; i<10; i++){
    Serial.print(l3GasQ[i]+" ");
  }
  Serial.println();
}

void setup() {
  //Output buffer er bandwidth thik kore dite hobe
  Serial.begin(115200);

  Serial.println("SETTING UP...");
  
  // Initialize Bio sensor
  if (!particleSensor.begin(Wire, I2C_SPEED_FAST)) //Use default I2C port, 400kHz speed
  {
    Serial.println("  -> MAX30105 was not found. Please check wiring/power. ");
    while (1);
  }
  Serial.println("  -> Bio sensor found (Place your index finger on the sensor with steady pressure.");
  particleSensor.setup(); //Configure sensor with default settings
  particleSensor.setPulseAmplitudeRed(0x0A); //Turn Red LED to low to indicate sensor is running
  particleSensor.setPulseAmplitudeGreen(0); //Turn off Green LED

  // Motion setup
  Wire.begin();
  mpu6050.begin();
  mpu6050.calcGyroOffsets(true);
  Serial.println("  -> Motion sensor has started.");

  // Inititalize BMP sensor
  if (!bmp.begin()) {
    Serial.println("  -> Could not find a valid BMP085 sensor.");
    while (1) {}
  }
  else{
    Serial.println("  -> Found BMP085 sensor - starated.");
  }

  //Gas setup
  pinMode(13, OUTPUT);
  pinMode(2, INPUT);
  Serial.println("  -> Found Gas sensor - starated.");

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
  bio();
  envi1();
  envi2();
  riskAnalysis();
  readingNumber++;
  
  delay(50);
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

//Bio sensor values
void bio(){
  Serial.println("\n  Bio readings");
  irVal = particleSensor.getIR();

  if (checkForBeat(irVal) == true)
  {
    //We sensed a beat!
    long delta = millis() - lastBeat;
    lastBeat = millis();

    beatsPerMinute = 60 / (delta / 1000.0);

    if (beatsPerMinute < 255 && beatsPerMinute > 20)
    {
      rates[rateSpot++] = (byte)beatsPerMinute; //Store this reading in the array
      rateSpot %= RATE_SIZE; //Wrap variable

      //Take average of readings
      beatAvg = 0;
      for (byte x = 0 ; x < RATE_SIZE ; x++)
        beatAvg += rates[x];
      beatAvg /= RATE_SIZE;
    }
  }

  Serial.print("    -> IR=");
  Serial.println(irVal);
  Serial.print("    -> BPM=");
  Serial.println(beatsPerMinute);
  Serial.print("    -> Avg BPM=");
  Serial.println(beatAvg);

  if (irVal < 50000)
    Serial.print("    -> Place your finger!");

  Serial.println();
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
  Serial.print("    -> ABS ACC: ");
  Serial.println(gyroVal);
  
  anguVal = abs(mpu6050.getAngleX()) + abs(mpu6050.getAngleY()) + abs(mpu6050.getAngleZ());
  Serial.print("    -> ABS ACC: ");
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
