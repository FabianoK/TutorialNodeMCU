#include <ESP8266WiFi.h>

const char* ssid     = "MeuAP";
const char* password = "minhasenha";
IPAddress IP(192,168,200,1);
IPAddress net(255,255,255,0);
int EXTLED = 5;

WiFiServer server(80);

void setup() {
  Serial.begin(9600);
  delay(10);

  WiFi.mode(WIFI_AP);
  WiFi.softAPConfig(IP, IP, net);
  WiFi.softAP(ssid, password);
  delay(1000);
  server.begin();

  pinMode(LED_BUILTIN, OUTPUT);
  pinMode(EXTLED, OUTPUT);

}

void loop() {
    WiFiClient client = server.available();
    if(!client)
        return;

    while(!client.available())
        delay(1);
    Serial.println("Conexão recebida");
    client.println("HTTP/1.1 200 OK");
    client.println("Content-Type: text/html");
    client.println("");//Fim do cabecalho http \n\r
    client.println("<html><meta charset='utf-8'/><h1>Olá cliente</h1></html>");

    delay(10);

}
