const int solPin = 7;

void setup() {
  pinMode(solPin, OUTPUT);
  digitalWrite(solPin, HIGH);
}

void loop() {
  
  digitalWrite(solPin, HIGH);
  delay(10000);
}
