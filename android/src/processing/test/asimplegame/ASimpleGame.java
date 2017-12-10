package processing.test.asimplegame;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ASimpleGame extends PApplet {

Player p;
ArrayList<Brick> b; 
float jumpSpeed, jumpCount, gravity, brickSpeed, fallSpeed, playerSize, brickSize;
int score, highScore;
boolean gameOver, start, colorChange;
int resetIndex, colorVal;

public void setup(){
  
  //size(500, 1000);
  //PFont font = ("AppleSDGothicNeo-Thin-48.vlw");
  //textFont(font);
  
  //Set framerate to 58
  frameRate(60);
  
  
  //Set initial variables
  jumpSpeed = 0;
  fallSpeed = 3.5f;
  jumpCount = 0;
  score = 0;
  brickSpeed = -20;
  resetIndex = -1;
  colorVal = 0;
  colorChange = false;
  
  
  //Load the user's highscore
  String[] highScores = loadStrings("score.txt");
  if (highScores != null)
    highScore = Integer.valueOf(highScores[0]);
  
  
  //Create objects
  p = new Player();
  b = new ArrayList<Brick>();
  b.add(new Brick(brickSpeed));
  playerSize = p.getSize();
  brickSize = b.get(0).getSize();

  gameOver = false;
  start = false;
}

public void draw(){
  background(0);
  textAlign(CENTER);
  text(highScore, width / 2, 300);
  textSize(200);
  text(score, width / 2, 200);

  textSize(70);
  showBrick();
  p.show(colorVal);
  
  changeColor();

  //System.out.println(frameRate );

  if (!gameOver){
    if(hitDetect()){
      jumpSpeed = 0;
      gravity = brickSpeed;
      if (p.isLeft())
        p.setX(brickSize + playerSize/2);
      else
        p.setX(width - (brickSize + playerSize/2));
      jumpCount = 0;
    }
    else{
      gravity = fallSpeed;
      if (p.isLeft())
        jumpSpeed = -30;
      else
        jumpSpeed = 30;
    }
    
    
      
    if (start){
      p.jump(jumpSpeed);
      p.fall(gravity); 
    }

    if (p.bound()){
      jumpSpeed = 0;
      p.setX(width / 2);
      p.setY((height / 2) - 100);
      gameOver = true; 
      start = false;
      //colorVal = 5;
      
      if (score > highScore){
        String[] scoreString = new String[1];
        scoreString[0] = score + "";
        saveStrings("score.txt", scoreString);
        highScore = score;
      }
      
      score = 0;
    }
    
      
  }
}

public void showBrick(){
  for(int i = 0; i < b.size(); i++){ 
    if(b.size() < 5){
      if (b.get(b.size()-1).edgePassed())
        b.add(new Brick(brickSpeed));
        b.get(b.size()-1).resetBrick(resetIndex);
    }
        
      if (b.get(i).bound()){
        if (resetIndex >= 0){
          b.get(i).resetBrick(b.get(resetIndex).getY() + b.get(resetIndex).getLength());
        }
        else
          b.get(i).resetBrick(height);
        
        //System.out.println(b.size());
        
        if (start)
          score++;
        resetIndex = i;
      }
      b.get(i).show();
      b.get(i).move();
    }
}

public void changeColor(){
  if (colorVal >= 255)
    colorChange = true;
  else if (colorVal <= 0)
    colorChange = false;
  
  if (!colorChange)
    colorVal++;
  else
    colorVal--; 
}

public boolean hitDetect(){
  for (int i = 0; i < b.size(); i++){
      if (p.getY() >= b.get(i).getY() && p.getY() <= b.get(i).getY() + b.get(i).getLength()){
        if (p.isLeft()){
          if (p.getX() < brickSize + playerSize && b.get(i).getSide())
            return true;
        }
        else if (!p.isLeft()){
          if (p.getX() > width - (brickSize + playerSize) && !b.get(i).getSide())
            return true;
        }
      } 
  }
  return false;
}

public void mousePressed(){
    if (!start){
      start = true;
      gameOver = false;
      p.setDir(false);
      jumpCount = 0;
    }
      
    if (p.isLeft() && jumpCount < 2){
      jumpSpeed = 0;
      p.swapSide();
    }
      
    else if (!p.isLeft() && jumpCount < 2){
      jumpSpeed = 0; 
      p.swapSide();
    }
    
    jumpCount++;
}
class Brick{
  float brickLength;
  float x, speed, size;
  float y = height + 250;
  boolean left;

  public Brick(float brickSpeed){
    speed = brickSpeed;
    brickLength = PApplet.parseInt(random(200, 900));
    float side = random(2);
    size = 50;
    if (side < 1)
      left = true;
      
    else 
      left = false;
  }
  
  public void move(){
    y+= speed;
    //lerp(y, speed, 0.5);
  }
  
  public float getSpeed(){
    return speed; 
  }
  
  public float getSize(){
    return size; 
  }
  
  public void setSpeed(float num){
     speed = num;
  }
  
  public boolean edgePassed(){
    if (y + (brickLength) < height)
      return true;
    return false;
  }
  
  public boolean bound(){
    if(y + brickLength + 50 < 0)
      return true;
    return false;
  }
  
  public float getX(){
    return x;
  }
  
  public float getY(){
    return y; 
  }
  
  public float getLength(){
    return brickLength;
  }
  
  public boolean getSide(){
    return left;
  }
  
  public void resetBrick(float aheadPos){
    y = aheadPos + 250;
    brickLength = brickLength = random(200, 900);

    float side = random(2);
    if (side < 1)
      left = true;
    else 
      left = false;
    
  }

  public void show(){
    //float side = random(2);
    //System.out.println(side + "");
    fill(255);
    stroke(255);
    
    if (left){
      rect(0, y, size, brickLength);
      x = 0;
    }
    else{
      rect(width - size, y, size, brickLength);
      x = width - size;
    }
  }
}
public class Coin{
  float x, y, size;
  boolean move;
  
  public Coin(){
    move = true;
    size  = 200;
    x = width / 2;
    y = height + 400;
  }
  
  public void move(){
    y -= 10; 
  }
  
  public void resetY(){
    y = height + 400;
  }
  
  public boolean bound(){
    if (y < 0 - (size)){
      y = height + 400;
      return true;
    }
    return false;
  }
  
  public void setMove(boolean b){
    move = b; 
  }
  
  public void show(){
    //fill(255);
    ellipse(x, y, size, size);
    fill(400);
    stroke(255);
  }
}
class Player {
  float size;
  float x, y;
  boolean left, moving;

  public Player() {
    size = 70;
    x = width / 2;
    y = (height / 2) - 100;
    left = true;
    moving = false;
  }

  public void jump(float speed) {
    x += speed;
  }
  
  public void fall(float gravity){
    y += gravity;  
  }
  
  public void swapSide(){
    if (left) 
      left = false;
    else
      left = true;
  }
  
  public boolean isLeft(){
    return left;
  }
  
  public void setDir(boolean dir){
    left = dir; 
  }
  
  public boolean isMoving(){
    return moving;
  }

  public boolean bound() {
    if (x < 0 - (size / 2)) {
      x = size / 2;
      left = true;
      moving = false;
      return true;
    } else if (x > width + (size/2)) {
      x = width - (size / 2);
      left = false; 
      moving = false;
      return true;
    } else if (y < 0 - size/2){
      y = size / 2;
      return true;
    } else if (y > height + size/2){
      y = height - (size / 2);
      moving = false;
      return true;
    }
    
    moving = true;
    return false;
  }
  
  public float getX(){
    return x;
  }
  
  public float getY(){
    return y; 
  }
  
  public void setX(float num){
    x = num;
  }
  
  public void setY(float num){
    y = num;  
  }  
  
  public float getSize(){
    return size;
  }

  public void show(int c) {
    //noFill();
    //stroke(255,255, 89);
    //strokeWeight(5);
    noFill();
    stroke(255, c, 0);
    strokeWeight(5);
    ellipse(x, y, size, size);
   
  }
}
  public void settings() {  fullScreen();  smooth(4); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ASimpleGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
