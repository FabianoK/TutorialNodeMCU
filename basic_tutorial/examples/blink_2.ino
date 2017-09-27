int EXTLED = 5; //Aqui devemos usar o GPIO
int count = 0;

void setup() {
  pinMode(LED_BUILTIN, OUTPUT);
  pinMode(EXTLED, OUTPUT);
}

void loop() {

  if(count %2 == 0){
    digitalWrite(EXTLED, HIGH);
    digitalWrite(LED_BUILTIN, LOW);
  }else{
    digitalWrite(EXTLED, LOW);
    digitalWrite(LED_BUILTIN, HIGH);
  }
  count++;
  delay(1000);
}

