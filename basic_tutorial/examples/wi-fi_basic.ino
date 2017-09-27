#include <ESP8266WiFi.h>

const char* ssid     = "ssid";
const char* password = "senha_do_ssid";

void setup() {
  Serial.begin(9600);
  delay(10);
  Serial.print("Conectando com: ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi conectado");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}


void loop() {

}

