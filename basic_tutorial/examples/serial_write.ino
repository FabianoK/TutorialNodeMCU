int start = 0;

void setup() {
  Serial.begin(9600);
  Serial.println("Iniciando");
  Serial.print("Connectando com");
}

void loop() {

  if(start < 100){
    Serial.print(".");
    start++;
  }else if(start == 100){
    Serial.println("\nConnected");
    start++;
  }

  delay(1000);
}
