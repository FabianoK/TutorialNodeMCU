#include <ESP8266WiFi.h>

const char* ssid     = "MeuAP";
const char* password = "minhasenha";
IPAddress IP(192,168,200,1);
IPAddress net(255,255,255,0);

void setup() {
  Serial.begin(9600);
  delay(10);
  Serial.print("Iniciando serviço: ");

  WiFi.mode(WIFI_AP);
  WiFi.softAPConfig(IP, IP, net)
  WiFi.softAP(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
}


void loop() {
    Serial.println(WiFi.localIP());
}

