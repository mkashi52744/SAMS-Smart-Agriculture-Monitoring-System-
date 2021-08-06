#include <ESP8266WiFi.h>
#include <FirebaseESP8266.h>                          
#define FIREBASE_HOST "https://demo1-c3308-default-rtdb.firebaseio.com/"                          // the project name address from firebase id
#define FIREBASE_AUTH "f3IxtJ4sRP1ai03ysrjstJhK5QnRVZTKGYZcOR2q"            // the secret key generated from firebase
#define WIFI_SSID "Jazz-LTE-F457"
#define WIFI_PASSWORD "00909929"
int value1;
int D1 =4;
int analogPin = A0;
int waterlevel;
int map_low=230;
int map_high=5;
int percentage;
int level;
int percentage_of_water;
FirebaseData firebaseData;
void setup()
{
  Serial.begin(115200);              
  
  pinMode(D1,OUTPUT);
  digitalWrite(D1,LOW);
  WiFi.begin(WIFI_SSID,WIFI_PASSWORD);
  Serial.print("connecting");
  while (WiFi.status()!=WL_CONNECTED)
  {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("connected:");
  Serial.println(WiFi.localIP());
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);   // connect to firebase
  }

void loop() { 
  waterlevel=analogRead(A0);
  percentage_of_water = map(waterlevel, map_low, map_high, 100,0 );
  Serial.print("Water Level :");
  Serial.print(waterlevel);
  Serial.print("| percentage");
  Serial.print(percentage_of_water);
  Serial.println("%");

   if(percentage_of_water>=0 && percentage_of_water<25)
  {Serial.println("Water Level :0 cm");
  level=0;
  }
   else if(percentage_of_water>=25 && percentage_of_water<50)
   {Serial.println("Water Level :1 cm");
   level=1;
   }
   else if(percentage_of_water>=50 && percentage_of_water<75)
   {Serial.println("Water Level :2 cm");
   level=2;
   }
   else if(percentage_of_water>=75 && percentage_of_water<100)
   {Serial.println("Water Level :3 cm");
   level=3;
   }
   else if(percentage_of_water>=100)
   {Serial.println("Water Level :4 cm");
   level=4;
   }
if (Firebase.setString(firebaseData, "/WaterLevel", String(level) )) {    // On successful Write operation, function returns 1  
               Serial.print("Water Level = ");
               Serial.println(level);
               Serial.println("\n");
               delay(500);
     }
if(Firebase.getString(firebaseData,"/Status"))
    {
      if(firebaseData.dataType()=="string")
      {
        value1=firebaseData.stringData().toInt();
        if(value1==0)
        {
          digitalWrite(D1,LOW);
          Serial.println("Motor 1 OFF");
        }
        if(value1==1)
       {
       digitalWrite(D1,HIGH);
       Serial.println("Motor 1 ON");
       delay(500);
       }
      } 
      
    }

}
