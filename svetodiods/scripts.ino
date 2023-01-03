/////////////////// SCRIPTS /////////////////////

void flashLights(){
  one_color_all(0,0,0);
  FastLED.show();
  delay(500);
  setMemorizedColor();
  FastLED.show(); 
  delay(500);  
}

void rainbow(){
  byte counter;
  for (int i = 0; i < LED_NUM; i++ ) {   
    leds[i] = CHSV(counter + i * 2, 255, 255); 
  }
  counter++;       
  FastLED.show();
  delay(thisdelay); 
}

  void police() {                  
  thishue = 0;
  thathue = (thishue + 160) % 255;
  for (int x = 0 ; x < 5; x++ ) {
    for (int i = 0 ; i < (LED_NUM / 2); i++ ) {
      leds[i] = CHSV(thishue, thissat, 255);
    }
    LEDS.show(); delay(thisdelay);
    one_color_all(0, 0, 0);
    LEDS.show(); delay(thisdelay);
  }
  for (int x = 0 ; x < 5; x++ ) {
    for (int i = (LED_NUM / 2) ; i < LED_NUM; i++ ) {
      leds[i] = CHSV(thathue, thissat, 255);
    }
    LEDS.show(); delay(thisdelay);
    one_color_all(0, 0, 0);
    LEDS.show(); delay(thisdelay);
  }
}

void ryslan(){
  int j = 0;
  for (int i = 0 + j ; i <= 17 + j; i++ ) {
    leds[i].setRGB(0, 154, 255);
  }
  for (int i = 18  + j  ; i <= 20  + j ; i++ ) {
    leds[i].setRGB(255, 17, 38);
  }
  for (int i = 21  + j  ; i <= 38  + j ; i++ ) {
    leds[i].setRGB(255, 255, 255);
  }
  for (int i = 39 + j  ; i <= 41 + j; i++ ) {
    leds[i].setRGB(255, 17, 38);
  }
  for (int i = 42 + j ; i <= 60  + j ; i++ ) {
    leds[i].setRGB(30, 255, 58);
  }  
  LEDS.show();
}

void random_burst() {    
    int r = random(255);
    int g = random(255);
    int b = random(255);
    one_color_all( r, g, b);
    memorizing(r, g, b);
    LEDS.show();
}

void one_color_all(int cred, int cgrn, int cblu) {
  for (int i = 0 ; i < LED_NUM; i++ ) {
    leds[i].setRGB( cred, cgrn, cblu);
  }
}