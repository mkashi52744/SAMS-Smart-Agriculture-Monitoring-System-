#include <ESP8266WiFi.h>                                                    // esp8266 library
#include <FirebaseESP8266.h>                                                 // firebase library
#include <DHT.h>                                                            // dht11 temperature and humidity sensor library

#define FIREBASE_HOST "https://demo1-c3308-default-rtdb.firebaseio.com/"                          // the project name address from firebase id
#define FIREBASE_AUTH "f3IxtJ4sRP1ai03ysrjstJhK5QnRVZTKGYZcOR2q"            // the secret key generated from firebase

#define WIFI_SSID "Jazz-LTE-F457"
#define WIFI_PASSWORD "00909929"                                  //password of wifi ssid
FirebaseData firebaseData;
#define DHTPIN 2
// what digital pin we're connected to
#define DHTTYPE DHT11                                                       // select dht type as DHT 11 or DHT22

int analogPin = A0;
int moisture;
int percentage;

int map_low = 888;
int map_high = 443;
int soilMoistureValue;

DHT dht(DHTPIN, DHTTYPE);                                                    

void setup() {
  Serial.begin(9600);
  delay(1000);                
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);                                     //try to connect with wifi
  Serial.print("Connecting to ");
  Serial.print(WIFI_SSID);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("Connected to ");
  Serial.println(WIFI_SSID);
  Serial.print("IP Address is : ");
  Serial.println(WiFi.localIP());                                            //print local IP address
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);                              // connect to firebase
  dht.begin();                                                               //Start reading dht sensor
}

void loop() {
  int fireHumid = dht.readHumidity();                                              // Reading temperature or humidity takes about 250 milliseconds!
  int fireTemp = dht.readTemperature();                                           // Read temperature as Celsius (the default)
   
  if (isnan(fireHumid) || isnan(fireTemp)) {                                                // Check if any reads failed and exit early (to try again).
    Serial.println(F("Failed to read from DHT sensor!"));
    return;
  }
   moisture = analogRead(analogPin);
  /*Serial.print("Raw: ");
  Serial.print(moisture);*/
  Serial.print("Soil Moisture before maping : ");
  Serial.println(moisture);

  percentage = map(moisture, map_low, map_high, 0, 100);

  Serial.print("Soil Moisture: ");
  Serial.print(percentage);

  Serial.println("%");
 
  delay(500);
  if (Firebase.setString(firebaseData, "/Temperature", String(fireTemp) )) {    // On successful Write operation, function returns 1  
               Serial.print("Temperature = ");
               Serial.println(fireTemp);
               Serial.println("\n");
               delay(500);

     }
if (Firebase.setString(firebaseData, "/Humidity", String(fireHumid) )) {    // On successful Write operation, function returns 1  
               Serial.print("Humidity = ");
               Serial.println(fireHumid);
               Serial.println("\n");
               delay(500);
     }
     if (Firebase.setString(firebaseData, "/Moisture", String(percentage) )) {    // On successful Write operation, function returns 1  
               Serial.print("Soil Moisture = ");
               Serial.println(percentage);
               Serial.println("\n");
               delay(500);
     }//setup path and send readings
   
}
