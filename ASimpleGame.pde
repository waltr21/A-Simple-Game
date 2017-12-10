Player p;
ArrayList<Brick> b;
float jumpSpeed, jumpCount, gravity, brickSpeed, fallSpeed, playerSize, brickSize;
int score, highScore;
boolean gameOver, start, colorChange;
int resetIndex, colorVal;

void setup(){
  fullScreen();
  //size(500, 1000);
  //PFont font = ("AppleSDGothicNeo-Thin-48.vlw");
  //textFont(font);

  //Set framerate to 58
  frameRate(60);
  smooth(4);

  //Set initial variables
  jumpSpeed = 0;
  fallSpeed = 3.5;
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

void draw(){
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

void showBrick(){
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

void changeColor(){
  if (colorVal >= 255)
    colorChange = true;
  else if (colorVal <= 0)
    colorChange = false;

  if (!colorChange)
    colorVal++;
  else
    colorVal--;
}

boolean hitDetect(){
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

void mousePressed(){
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
