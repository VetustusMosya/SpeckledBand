#include <SoftwareSerial.h>
#include "FastLED.h"
#include <EEPROM.h>


#define STR_ADDR 1
#define LED_PIN 3     // пин
#define LED_NUM 60    // количество светодиодов
CRGB leds[LED_NUM];


int brightness = 0;
int red = 255;
int green = 255;
int blue = 255;

String input;
String color;
String script;
int thisdelay = 40;         //-FX LOOPS DELAY VAR
int thishue = 0;             //-FX LOOPS DELAY VAR
int thathue = 0;
int thissat = 255;  

SoftwareSerial mySerial(0, 1);

void setup(){
  Serial.begin(9600);
  Serial.setTimeout(4);
  pinMode(LED_PIN, OUTPUT);
  FastLED.addLeds<WS2812, LED_PIN, GRB>(leds, LED_NUM);
  FastLED.setBrightness(brightness);
  setMemorizedColor();
}

void loop()
{
  if (Serial.available()) 
  {     
    input = Serial.readString();
    Serial.println(input);
  
    if (input.startsWith("0")){      
      script="";
      colorConverter(input);
    } else if (input.startsWith("s")){      
      script="";
      startScript(input);
    } else{     
      setBrightness(input);
      // setMemorizedColor();
    }
  } else {
      if (script == "police"){
      police();
      } else if(script == "flashing"){
      flashLights();
    }
  }
}


void startScript(String value){
    value.remove(0, 1);
    int i = value.toInt();
    switch (i) {
    case 1:
      rainbow(); 
      break;
    case 2:
      ryslan();  
      break;
    case 3:
      script="police";
      police();  
      break;
    case 4:
      random_burst();  
      break;
    case 5:
      script="flashing";
      flashLights();  
      break;
    }
  }

void memorizing(int r, int g, int b){
  EEPROM.put(0, r);
  EEPROM.put(2, g);
  EEPROM.put(4, b);
}

void setMemorizedColor(){
  int r;
  int g;
  int b;
  EEPROM.get(0, r);
  EEPROM.get(2, g);
  EEPROM.get(4, b);
  one_color_all( r, g, b);
}

void colorConverter(String value){
  unsigned long rgb = (long) strtol(value.c_str(), NULL, 0);
  int red = rgb >> 16 ;
  int green = (rgb & 0x00ff00) >> 8;
  int blue = (rgb & 0x0000ff);
  rgb = 0;

  rgb |= red <<16;
  rgb |= blue <<8;
  rgb |= green;

  // Serial.println(red);  Serial.println(green);  Serial.println(blue);

  for (int i = 0; i < LED_NUM; i++) 
  {
    leds[i].setRGB(red, green, blue);
  }  
  FastLED.show();
  memorizing(red, green, blue);
}

void setBrightness(String value){
  brightness = value.toInt();
  FastLED.setBrightness(brightness);
  FastLED.show();
}