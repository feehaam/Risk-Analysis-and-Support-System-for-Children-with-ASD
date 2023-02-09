
void setup()
{
  Serial.begin(115200); // sets the serial port to 9600
  pinMode(A5, INPUT);
}

void loop()
{
  int sensorValue = analogRead(A5); // read analog input pin 0
  int digitalValue = digitalRead(A5);
  Serial.print("Air quality (Harmful gas) = "); 
  Serial.print(sensorValue);
  Serial.println(" PPM"); 
  delay(100);
}
