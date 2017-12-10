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

  void show(int c) {
    //noFill();
    //stroke(255,255, 89);
    //strokeWeight(5);
    noFill();
    stroke(255, c, 0);
    strokeWeight(5);
    ellipse(x, y, size, size);
   
  }
}