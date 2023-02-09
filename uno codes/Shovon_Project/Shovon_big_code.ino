
#include <WiFi.h>
#include <Arduino.h>
#include <analogWrite.h>
#include <Wire.h>
#include <Adafruit_MPU6050.h>


Adafruit_MPU6050 mpu;
Adafruit_Sensor *mpu_temp, *mpu_accel, *mpu_gyro;
const char *ssid = "Green house"; // WiFi ssid
const char *pass = "00001111"; // WiFi Password
WiFiServer server(80);





double air_quality;
const int potPin = 32; //air sensor at pin 32
#define baz 27 //buzzer at pin 27

int xsample = 0;
int ysample = 0;

#define samples 50
#define maxVal 0.40
#define minVal -0.40

sensors_event_t accel;
sensors_event_t gyro;
sensors_event_t temp;


void setup(void) {
  Serial.begin(115200);

  //wifi connect and ip setup
  
  IPAddress LOCAL_IP(192, 168, 0, 150);
  IPAddress gateway(192, 168, 0, 1);
  IPAddress subnet(255, 255, 255, 0);
  WiFi.config(LOCAL_IP, gateway, subnet);
  while (!Serial)
    delay(10); 
  Serial.println("Connecting to ");
  Serial.println(ssid);
  WiFi.begin(ssid, pass);
  while (WiFi.status() != WL_CONNECTED)
  {
    delay(550);
    Serial.print(".");

  }



  //set baz pin as output
  pinMode(baz, OUTPUT);
  
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.print("IP Address: ");
  Serial.println(WiFi.localIP());

  //starts esp server
  server.begin();
  Serial.println("Adafruit MPU6050 test!");

  if (!mpu.begin()) {
    Serial.println("Failed to find MPU6050 chip");
    while (1) {
      delay(50);
    }
  }
  

  Serial.println("MPU6050 Found!");
  mpu_temp = mpu.getTemperatureSensor(); //storing Temperature value in mpu_temp
  mpu_temp->printSensorDetails();

  mpu_accel = mpu.getAccelerometerSensor();
  mpu_accel->printSensorDetails();

  mpu_gyro = mpu.getGyroSensor();    
  mpu_gyro->printSensorDetails();


  //calibrating acceleration value
  mpu_accel->getEvent(&accel);
  for (int i = 0; i < samples; i++)
  {
    xsample += analogRead(accel.acceleration.x);
    ysample += analogRead(accel.acceleration.y);
  }

  xsample /= samples;
  ysample /= samples;


  delay(5000);

}

void loop() {
  delay(1000);
  /* Get a new normalized sensor event */
  mpu_temp->getEvent(&temp);
  mpu_accel->getEvent(&accel);
  mpu_gyro->getEvent(&gyro);
  
  
  //checking for accelaration value
  float xValue = xsample - accel.acceleration.x;
  float yValue = ysample - accel.acceleration.y;
  int m = 0;
  Serial.print("\t\tTemperature ");
  Serial.print(temp.temperature);
  Serial.println(" deg C");

  /* Display the results (acceleration is measured in m/s^2) */
  Serial.print("\t\tAccel X: ");
  Serial.print(accel.acceleration.x);
  Serial.print("\tY: ");
  Serial.print(accel.acceleration.y);
  Serial.print("\tZ: ");
  Serial.print(accel.acceleration.z);
  Serial.println(" m/s^2 ");

  /* Display the results (rotation is measured in rad/s) */
  Serial.print("\t\tGyro X: ");
  Serial.print(gyro.gyro.x );
  Serial.print("\tY: ");
  Serial.print(gyro.gyro.y);
  Serial.print("\tZ: ");
  Serial.print(gyro.gyro.z);
  Serial.println(" radians/s ");
  Serial.println();

  //analog value of gas sensor
  air_quality = ((analogRead(potPin) / 4096.0) * 200.0);


  //checking if the accelaration differ 0.40
  if (xValue < minVal || xValue > maxVal  || yValue < minVal || yValue > maxVal)
  {
    digitalWrite(baz, HIGH);
    //Serial.println("earthquake");
    delay(2000);
    m = 1;
  }

  else
  {
    digitalWrite(baz, LOW);
    m = 0;
  }

  Serial.print("\t\tAir quality: ");
  Serial.println(air_quality);



  //// pushing html data in server

  WiFiClient client = server.available();
  if ( client.connected()) { // loop while the client's connected
    // HTTP headers always start with a response code (e.g. HTTP/1.1 200 OK)
    client.println("HTTP/1.1 200 OK");
    client.println("Content-type:text/html");
    client.println("Connection: close");
    client.println("Refresh: 1"); // update the page after 3 sec
    client.println();



    // Display the HTML web page
    client.println("<!DOCTYPE html><html>");
    client.println("<head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
    client.println("<link rel=\"icon\" href=\"data:,\">");

    // Web Page Heading
    client.println("<body><h1 style=\"color:orange;\"> Air Quality Measurement and Earthquake Detection </h1>");

    client.println("<body><p style=\"color:blue;\"> Pollution Content(in percentage) = " + String(air_quality) + " %" + " </p>");
    if (air_quality <= 20.0 ) {
      client.println("<body><p style=\"color:green;\"> Normal </p>");
    } else if (air_quality > 20.0 && air_quality < 70.0) {
      client.println("<body><p style=\"color:purple;\"> Medium </p>");
    } else {
      client.println("<body><p style=\"font-size:200%; color:red\"> Danger!!! </p>");
    }
    client.println("<body><p style=\"color:blue;\"> Temperature = " + String(temp.temperature) + " C" + " </p>");

    if (m == 1) {
      digitalWrite(baz, HIGH);
      //client.println("<body><button disable style=\"font-size:200%;color:white;background-color:red; padding:5px\"> Earthquake "  " </button>");
    }
    client.println("<body><p style=\"color:blue;\"> X axis value = " + String(accel.acceleration.x) + " " + " </p>");
    client.println("<body><p style=\"color:blue;\"> Y axis value = " + String(accel.acceleration.y) + " " + " </p>");
    client.println("<body><p style=\"color:blue;\"> Z axis value = " + String(accel.acceleration.z) + " " + " </p>");
    client.println("</body></html>");
  }



}
