class Brick{
  float brickLength;
  float x, speed, size;
  float y = height + 250;
  boolean left;

  public Brick(float brickSpeed){
    speed = brickSpeed;
    brickLength = int(random(200, 900));
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