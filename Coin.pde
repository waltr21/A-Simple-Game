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