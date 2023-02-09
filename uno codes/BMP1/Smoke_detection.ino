
float env = 0.0;

void setup (){
Serial.begin (9600);
pinMode(11, OUTPUT);
int t = 10;
while(t-->0){
    float gas = analogRead(A3);
    gas = gas/1023 * 100;
    env += gas;
    delay(250);
  }

  env /= 10;
  
  Serial.print("Limit is: ");
  Serial.println(env);
}


void loop() {
  float gas = analogRead(A3);
  gas = gas/1023 * 100;
  
  Serial.println (gas);
  delay(500);

  if(gas>=env)
  {
    digitalWrite(11, HIGH);
    delay(500);
    digitalWrite(11, LOW);
  }
}
