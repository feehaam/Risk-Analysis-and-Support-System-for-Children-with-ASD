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
  //Bio er initials
  const byte RATE_SIZE = 4; //Increase this for more averaging. 4 is good.
  byte rates[RATE_SIZE]; //Array of heart rates
  byte rateSpot = 0;
  long lastBeat = 0; //Time at which the last beat occurred
  float beatsPerMinute;
  int beatAvg;
  //Smoke er initials
  float env = 0.0;

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

int readingNumber = 1;

void loop() {
  Serial.print("\n=============READING #");
  Serial.print(readingNumber);
  Serial.println("=============");
  
  motion();
  bio();
  envi1();
  envi2();
  readingNumber++;
  delay(300);
}

//Environment (BMP) sensor values
void envi1(){
  Serial.println("\n  BMP readings");
  Serial.print("    -> Temperature = ");
    Serial.print(bmp.readTemperature());
    Serial.println(" *C");
    
    Serial.print("    -> Pressure = ");
    Serial.print(bmp.readPressure());
    Serial.println(" Pa");

    Serial.print("    -> Altitude = ");
    Serial.print(bmp.readAltitude());
    Serial.println(" meters");

    Serial.print("    -> Pressure at sealevel = ");
    Serial.print(bmp.readSealevelPressure());
    Serial.println(" Pa");

    Serial.print("    -> Real altitude = ");
    Serial.print(bmp.readAltitude(seaLevelPressure_hPa * 100));
    Serial.println(" meters");
}

//Bio sensor values
void bio(){
  Serial.println("\n  Bio readings");
  long irValue = particleSensor.getIR();

  if (checkForBeat(irValue) == true)
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
  Serial.println(irValue);
  Serial.print("    -> BPM=");
  Serial.println(beatsPerMinute);
  Serial.print("    -> Avg BPM=");
  Serial.println(beatAvg);

  if (irValue < 50000)
    Serial.print("    -> Place your finger!");

  Serial.println();
}


//Motion sensor values
void motion(){
  mpu6050.update();
  Serial.println("\n  Bio readings");
  Serial.print("    -> Temp : ");
  Serial.println(mpu6050.getTemp());
  Serial.print("    -> AccX : ");
  Serial.print(mpu6050.getAccX());
  Serial.print(" | AccY : ");
  Serial.print(mpu6050.getAccY());
  Serial.print(" | AccZ : ");
  Serial.println(mpu6050.getAccZ());

  Serial.print("    -> GyroX : ");
  Serial.print(mpu6050.getGyroX());
  Serial.print(" | GyroY : ");
  Serial.print(mpu6050.getGyroY());
  Serial.print(" | GyroZ : ");
  Serial.println(mpu6050.getGyroZ());

  Serial.print("    -> AccAngleX : ");
  Serial.print(mpu6050.getAccAngleX());
  Serial.print(" | AccAngleY : ");
  Serial.println(mpu6050.getAccAngleY());

  Serial.print("    -> GyroAngleX : ");
  Serial.print(mpu6050.getGyroAngleX());
  Serial.print(" | GyroAngleY : ");
  Serial.print(mpu6050.getGyroAngleY());
  Serial.print(" | GyroAngleZ : ");
  Serial.println(mpu6050.getGyroAngleZ());
  
  Serial.print("    -> AngleX : ");
  Serial.print(mpu6050.getAngleX());
  Serial.print(" | AngleY : ");
  Serial.print(mpu6050.getAngleY());
  Serial.print(" | AngleZ : ");
  Serial.println(mpu6050.getAngleZ());
}

void envi2(){
  Serial.println("\n  Gas readings");
  int sensorValue = analogRead(0); // read analog input pin 0
  int digitalValue = digitalRead(2);
  Serial.print("    -> Air quality = "); 
  Serial.print(sensorValue);
  Serial.println(" PPM"); 
}
