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


    String request = client.readStringUntil('\r');
    String response = "<html><style>input{width:500px;height:180px;margin-bottom:40px}}</style>";
    response += "<input type='button' value ='On' onclick='location.href=\"?l=on\"'/><br>";
    response += "<input type='button' value ='Off' onclick='location.href=\"?l=off\"'/>";
    client.flush();

    Serial.println(request);

    if(request.indexOf("?l=on") >= 0){
        digitalWrite(EXTLED, HIGH);
    }else if(request.indexOf("?l=off") >= 0){
        digitalWrite(EXTLED, LOW);
    }

    Serial.println("Conexão recebida");
    client.println("HTTP/1.1 200 OK");
    client.println("Content-Type: text/html");
    client.println("");//Fim do cabecalho http \n\r
    client.println(response);


    delay(10);

}
