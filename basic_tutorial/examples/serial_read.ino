String data;
int porta = 0;

void setup() {
  Serial.begin(9600);
  delay(500);
  Serial.write("Digite algo:\n");

}

void loop() {
  if (Serial.available() > 0) {

    data = Serial.readString();
    Serial.print("Digitado: ");
    Serial.println(data);

    Serial.println("Digite um número: ");
    while(Serial.available() <= 0){}
    porta = Serial.readString().toInt();
    Serial.print(porta, HEX);
    Serial.write("\nDigite algo:\n");
  }
  delay(10);
}

