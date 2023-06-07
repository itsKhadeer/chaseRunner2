package com.example.chaserunner2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class myCanvas extends View {
    //paints
    Paint red_brush_fill;
    Paint brown_brush_fill;
    Paint black_brush_fill;
    Paint green_brush_fill;
    Paint lightBlue_brush_fill;
    Paint grey_brush_fill;

    //constants
    Random r = new Random();
    static int ScreenHeight = getScreenHeight();
    public static int Score = 0;
    public static int HighScore = 0;
    static int ScreenWidth =  getScreenWidth();
    static int STAGE_HEIGHT = 300;
    static int JUMP_SPEED = 25;
    static int GRAVITY = 1;
    static int count = 0;
    static boolean IS_JUMPING = false;
    static int SPEED_X = 17;
    static int hitCount = 0;
    static boolean gameOver = false;
    static int speedX = SPEED_X;
    static boolean deathSoundPlayed = false;

    //constants for rocks(3 rocks)
    static int r1Left = ScreenWidth*3/4+30, r1Width = 100, r1Right = r1Left +r1Width,
            r1Top = ScreenHeight-STAGE_HEIGHT+50, r1Bottom = r1Top + 40;// rock 1
    static int r2Left = ScreenWidth/2, r2Width = 100, r2Right = r2Left+r2Width,
            r2Top = ScreenHeight-STAGE_HEIGHT+70, r2Bottom = r2Top + 100;// rock 2
    static int r3Left = ScreenWidth+30, r3Width = 100, r3Right = r3Left+r3Width,
            r3Top = ScreenHeight-STAGE_HEIGHT+100, r3Bottom = r3Top + 70;// rock 3
    //constants for clouds (2 clouds)
    static int c1Left = 0, c1Top = 50, c1Right = c1Left+500, c1Bottom = 300;// cloud 1
    static int c2Left = c1Right + 800, c2Top = 50, c2Right = c2Left+500, c2Bottom = 300;// cloud 2
    Bitmap cloud1;
    Bitmap cloud2;

    // constants for the runner(hero mario)
    Bitmap runner;
    static int runnerLeft = ScreenWidth/2-70,
    runnerTop = ScreenHeight-STAGE_HEIGHT-140,
    runnerRight = ScreenWidth/2+70,
    runnerBottom = ScreenHeight-STAGE_HEIGHT;

    //constants for the chaser(villain bowser)
    Bitmap chaser;
    static int chaserLeft = -550,

    chaserRight = -150,
    chaserTop = ScreenHeight-STAGE_HEIGHT-400,
    chaserBottom = ScreenHeight-STAGE_HEIGHT+10;
    static int jumpSpeedForChaser = 28;
    static boolean chaserIsJumping = false;
    static boolean chaserIsChasing = false;
    static int obstaclePassedCount = 0;
    static int obstacleHit = 0;

    //constants for the obstacles (2 short and tall)
    Bitmap shortObstacle;
    boolean smallObstacleIsComing = true;
    static int shortObstacleLeft = ScreenWidth,
        shortObstacleTop = ScreenHeight-STAGE_HEIGHT-100,
        shortObstacleRight = shortObstacleLeft+100,
        shortObstacleBottom = ScreenHeight-STAGE_HEIGHT+15;
    Bitmap tallObstacle;
    static int tallObstacleLeft = ScreenWidth,
        tallObstacleTop = ScreenHeight-STAGE_HEIGHT-200,
        tallObstacleRight = tallObstacleLeft+100,
        tallObstacleBottom = ScreenHeight-STAGE_HEIGHT+15;
    static int flyingObstacleLeft = 0,
    flyingObstacleRight = flyingObstacleLeft+100,
    flyingObstacleTop = c1Top+300,
    flyingObstacleBottom = flyingObstacleTop + 100;
    static boolean flyingObstacleMovingDown = false;
    static boolean flyingObstacleFell = true;
    Bitmap flyingObstacle;
    boolean flyingObstacleIsComing = true;
    // powerUps 😅😅👍🏽👍🏽
    static int invincibleLeft = ScreenWidth,
    invincibleRight = invincibleLeft+70,
    invincibleBottom = ScreenHeight-STAGE_HEIGHT-350,
    invincibleTop = ScreenHeight-STAGE_HEIGHT-420;
    static int invincibilityTime = 5;
    static boolean poweredUp = false;
    static boolean poweredDown = false;
    static boolean invinciblePowerUp = false;
    static boolean hasTakenPowerUp = false;
    Bitmap mushRoom;

    //sound
    static MediaPlayer jumpSound;
    static MediaPlayer painSound;
    static MediaPlayer deathSound;
    static MediaPlayer heroTheme;
    static MediaPlayer villainTheme;
    static MediaPlayer powerUpSound;
    static MediaPlayer powerDownSound;

    public myCanvas(Context context) {
        super(context);
        cloud1 = BitmapFactory.decodeResource(getResources(), R.drawable.img);
        cloud2 = BitmapFactory.decodeResource(getResources(), R.drawable.img);
        runner = BitmapFactory.decodeResource(getResources(), R.drawable.mario);
        chaser = BitmapFactory.decodeResource(getResources(), R.drawable.bowser);
        mushRoom = BitmapFactory.decodeResource(getResources(), R.drawable.mushroom);
        shortObstacle = BitmapFactory.decodeResource(getResources(), R.drawable.smallobstacle);
        tallObstacle = BitmapFactory.decodeResource(getResources(), R.drawable.tallobstacle);
        flyingObstacle = BitmapFactory.decodeResource(getResources(), R.drawable.flyingobstacle);
        jumpSound = MediaPlayer.create(getContext(), R.raw.jumpsound);
        painSound = MediaPlayer.create(getContext(), R.raw.painsound);
        deathSound = MediaPlayer.create(getContext(), R.raw.deathsound);
        heroTheme = MediaPlayer.create(getContext(), R.raw.herotheme);
        villainTheme = MediaPlayer.create(getContext(), R.raw.villantheme);
        powerUpSound = MediaPlayer.create(getContext(), R.raw.powerupsound);
        powerDownSound = MediaPlayer.create(getContext(), R.raw.powerdownsound);
        heroTheme.start();
        heroTheme.setLooping(true);
        ScreenHeight = getScreenHeight();
        ScreenWidth =  getScreenWidth();
    }


    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        red_brush_fill = new Paint();
        red_brush_fill.setColor(Color.parseColor("#50FF0000"));
        red_brush_fill.setStyle(Paint.Style.FILL);

        lightBlue_brush_fill = new Paint();
        lightBlue_brush_fill.setColor(Color.parseColor("#87CEEB"));
        lightBlue_brush_fill.setStyle(Paint.Style.FILL);


        brown_brush_fill = new Paint();
        brown_brush_fill.setColor(Color.parseColor("#9b7653"));
        brown_brush_fill.setStyle(Paint.Style.FILL);

        black_brush_fill = new Paint();
        black_brush_fill.setColor(Color.BLACK);
        black_brush_fill.setStyle(Paint.Style.FILL);

        green_brush_fill = new Paint();
        green_brush_fill.setColor(Color.GREEN);
        green_brush_fill.setStyle(Paint.Style.FILL);

        grey_brush_fill = new Paint();
        grey_brush_fill.setColor(Color.parseColor("#7F8386"));
        grey_brush_fill.setStyle(Paint.Style.FILL);




        //setBackGround
        Rect backGround = new Rect(0, 0, ScreenWidth, ScreenHeight);
        canvas.drawRect(backGround, lightBlue_brush_fill);

        //setStage
        //rocks
        Rect Stage = new Rect(0, ScreenHeight-STAGE_HEIGHT, ScreenWidth,ScreenHeight);
        Rect Grass = new Rect(0, ScreenHeight-STAGE_HEIGHT, ScreenWidth, ScreenHeight-STAGE_HEIGHT+30);
        Rect Rock1 = new Rect(r1Left,r1Top, r1Right, r1Bottom);
        Rect Rock2 = new Rect(r2Left, r2Top, r2Right, r2Bottom);
        Rect Rock3 = new Rect(r3Left, r3Top, r3Right, r3Bottom);
        canvas.drawRect(Stage, brown_brush_fill);
        canvas.drawRect(Grass, green_brush_fill);
        canvas.drawRect(Rock1, grey_brush_fill);
        canvas.drawRect(Rock2, grey_brush_fill);
        canvas.drawRect(Rock3, grey_brush_fill);
        r1Left -= SPEED_X;
        r1Right -= SPEED_X;
        if(r1Right < 0) {
            r1Left = ScreenWidth;
            r1Right = r1Left+r1Width;
        }
        r2Left -= SPEED_X;
        r2Right -= SPEED_X;
        if(r2Right < 0) {
            r2Left = ScreenWidth;
            r2Right = r2Left+r2Width;
        }
        r3Left -= SPEED_X;
        r3Right -= SPEED_X;
        if(r3Right < 0) {
            r3Left = ScreenWidth;
            r3Right = r3Left+r3Width;
        }
        //clouds
        RectF cloudsL = new RectF(c1Left,c1Top,c1Right,c1Bottom);
        RectF cloudsR = new RectF(c2Left, c2Top, c2Right, c2Bottom);
        canvas.drawBitmap(cloud1,null, cloudsL,null);
        canvas.drawBitmap(cloud2,null, cloudsR, null);
        c1Left -= SPEED_X-SPEED_X*2/3;
        c1Right -= SPEED_X-SPEED_X*2/3;
        if (c1Right < 0) {
            c1Left = ScreenWidth;
            c1Right = c1Left + 500;
        }
        c2Left -= SPEED_X-SPEED_X*2/3;
        c2Right -= SPEED_X-SPEED_X*2/3;
        if (c2Right < 0) {
            c2Left = ScreenWidth;
            c2Right = c2Left + 500;
        }

        //runner hero
        RectF runnerHitBox = new RectF(runnerLeft, runnerTop, runnerRight, runnerBottom);
        canvas.drawBitmap(runner, null,runnerHitBox , null);

        //obstacles
        RectF tallObstacleHitBox = new RectF(tallObstacleLeft, tallObstacleTop, tallObstacleRight, tallObstacleBottom);
        RectF smallObstacleHitBox = new RectF(shortObstacleLeft, shortObstacleTop, shortObstacleRight, shortObstacleBottom);
        RectF flyingObstacleHitBox = new RectF(flyingObstacleLeft, flyingObstacleTop, flyingObstacleRight, flyingObstacleBottom);

        if(smallObstacleIsComing) {

            canvas.drawBitmap(shortObstacle, null,smallObstacleHitBox , null);
            shortObstacleLeft -= SPEED_X;
            shortObstacleRight -= SPEED_X;
            if(chaserRight >= shortObstacleLeft-25 && !gameOver) {
                chaserIsJumping = true;
            }
            if(shortObstacleRight < 0) {
                shortObstacleLeft = ScreenWidth;
                shortObstacleRight = shortObstacleLeft+100;
                if(r.nextInt(2) == 0) {
                    smallObstacleIsComing = false;
                } else {
                    smallObstacleIsComing = true;
                }
                if(r.nextInt(2) == 0 && !flyingObstacleIsComing) {
                    flyingObstacleIsComing = true;
                }
                count++;
                if(count % 4 == 0) if(SPEED_X <= 40) SPEED_X+=1;
                if(chaserIsChasing) obstaclePassedCount ++;
                if (r.nextInt(5) == 0 && !hasTakenPowerUp) {
                    invinciblePowerUp = true;
                } else {
                    invinciblePowerUp = false;
                }
                if(hasTakenPowerUp) {
                    invincibilityTime--;
                }
            }
        }else {
            canvas.drawBitmap(tallObstacle, null, tallObstacleHitBox, null);
            tallObstacleLeft -= SPEED_X;
            tallObstacleRight -= SPEED_X;
            if(chaserRight >= tallObstacleLeft-25 && !gameOver) {
                chaserIsJumping = true;
            }
            if (tallObstacleRight < 0) {
                tallObstacleLeft = ScreenWidth;
                tallObstacleRight = tallObstacleLeft + 100;

                if(r.nextInt(2) == 0) {
                    smallObstacleIsComing = false;
                } else {
                    smallObstacleIsComing = true;
                }
                if(r.nextInt(2) == 0 && !flyingObstacleIsComing) {
                    flyingObstacleIsComing = true;
                }
                if(count % 4 == 0) if(SPEED_X <= 40) SPEED_X+=1;
                if(chaserIsChasing) obstaclePassedCount++ ;
                if (r.nextInt(5) == 0 && !hasTakenPowerUp) {
                    invinciblePowerUp = true;
                } else {
                    invinciblePowerUp = false;
                }
                if(hasTakenPowerUp) {
                    invincibilityTime--;
                }
            }
        }
        canvas.drawBitmap(flyingObstacle,null,flyingObstacleHitBox,null);

        if(flyingObstacleIsComing) {

            flyingObstacleRight-= SPEED_X;
            flyingObstacleLeft -= SPEED_X;
            if(flyingObstacleFell) {
                if (flyingObstacleBottom <= tallObstacleTop) {
                    flyingObstacleMovingDown = true;
                } else if (flyingObstacleBottom >= ScreenHeight - STAGE_HEIGHT) {
                    flyingObstacleMovingDown = false;
                }
                if (flyingObstacleMovingDown) {
                    flyingObstacleTop += 5;
                    flyingObstacleBottom += 5;
                } else {
                    flyingObstacleTop -= 5;
                    flyingObstacleBottom -= 5;
                }
            }


            if(flyingObstacleRight < 0) {
                flyingObstacleLeft = ScreenWidth+800;
                flyingObstacleRight = flyingObstacleLeft+100;
                flyingObstacleTop = c1Top+400;
                flyingObstacleBottom = flyingObstacleTop + 100;

                flyingObstacleIsComing = false;
            }
        }
        // powerUps
        RectF powerUp = new RectF(invincibleLeft,invincibleTop, invincibleRight, invincibleBottom);

        if(invinciblePowerUp) {
            canvas.drawBitmap(mushRoom, null, powerUp, null);
            if(smallObstacleIsComing) {
                invincibleLeft = shortObstacleLeft;
                invincibleRight = shortObstacleRight;

            } else {
                invincibleLeft = tallObstacleLeft;
                invincibleRight = tallObstacleRight;
            }

        }
        //collision detection for powerUp
        if(powerUp.intersect(runnerHitBox)) {
            hasTakenPowerUp = true;
            invinciblePowerUp = false;
            poweredUp = false;
            poweredDown = false;
            invincibleRight = ScreenWidth + 70;
            invincibleLeft = ScreenWidth;
        }

        if(hasTakenPowerUp) {

            if(!poweredUp) powerUpAction();
            if(invincibilityTime <= 0) {
                if(!poweredDown) powerUpToNormal();
            }
        }
        //collision detection
        RectF chaserHitBox = new RectF(chaserLeft, chaserTop, chaserRight, chaserBottom);

        canvas.drawBitmap(chaser, null, chaserHitBox, null);
        if(chaserHitBox.intersect(flyingObstacleHitBox)) {
            flyingObstacleFell = false;
        }
        if(!flyingObstacleFell) flyingObstacleFalls();

        if(runnerHitBox.intersect(smallObstacleHitBox) && !hasTakenPowerUp){
            heroTheme.pause();
            villainTheme.start();
            villainTheme.setLooping(true);
            painSound.start();
            chaserIsChasing = true;
            obstacleHit++;
            if(obstacleHit >= 2) {
                SPEED_X = 0;
                if(runnerBottom >= ScreenHeight-STAGE_HEIGHT) {
                    gameOver = true;
                    obstaclePassedCount = 0;
                    shortObstacleLeft = -100;
                    shortObstacleRight = 0;
                    chaserIsComing();
                }
            } else {
                obstaclePassedCount = 0;
                shortObstacleLeft = -100;
                shortObstacleRight = 0;
            }
        }
        if(runnerHitBox.intersect(tallObstacleHitBox) && !hasTakenPowerUp) {
            SPEED_X = 0;
            heroTheme.pause();
            painSound.start();
            villainTheme.start();

            if(runnerBottom >= ScreenHeight-STAGE_HEIGHT) {
                gameOver = true;
                chaserIsChasing  = true;
                obstaclePassedCount = 0;
                tallObstacleLeft = -70;
                tallObstacleRight = 0;
                chaserIsComing();
            }
        }
        if(runnerHitBox.intersect(flyingObstacleHitBox) && !hasTakenPowerUp && flyingObstacleFell) {

            heroTheme.pause();
            flyingObstacleFell = false;
            villainTheme.start();
            villainTheme.setLooping(true);
            painSound.start();
            chaserIsChasing = true;
            obstacleHit++;
            if(obstacleHit >=2) {
                SPEED_X = 0;
                gameOver = true;
                chaserIsComing();
            }
        }

        //gameOver
        if(gameOver) {

            SPEED_X = 0;
            if(!chaserHitBox.contains(runnerHitBox)) {
                if(chaserRight <= runnerLeft+150) {
                    chaserRight += speedX+10;
                    chaserLeft += speedX+10;
                }
            } else {
                villainTheme.stop();
                if(!deathSoundPlayed) {
                    deathSound.start();
                    deathSoundPlayed = true;
                    JUMP_SPEED = 25;
                    IS_JUMPING = true;
                }
            }
        }

        if(IS_JUMPING) {
            jump();

        }
        black_brush_fill.setTextSize(50);
        if(!gameOver){
            canvas.drawText(String.valueOf("Score: "+Score++),50,100 ,black_brush_fill);
        } else {
            HighScore = Math.max(HighScore,Score);


            canvas.drawText("Score: "+Score,50,100 ,black_brush_fill);
        }
        canvas.drawText(("High Score: "+HighScore), ScreenWidth-500, 100, black_brush_fill);
        if(chaserIsJumping) jumpForChaser();

        if(chaserIsChasing) chaserIsComing();

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!IS_JUMPING) {
            IS_JUMPING = true;
            jumpSound.start();
        }
        return true;
    }
    public static void powerUpAction() {
        if(!powerUpSound.isPlaying()) {
            powerUpSound.start();
        }
        SPEED_X = 0;
        IS_JUMPING = false;
        runnerLeft--;runnerRight++;runnerTop--;
        if(runnerLeft <= ScreenWidth/2-140) {
            SPEED_X = speedX;
            poweredUp = true;
            IS_JUMPING = true;
        }
    }
    public static void powerUpToNormal() {
        SPEED_X = 0;
        if(!powerDownSound.isPlaying()) {
            powerDownSound.start();
        }
        runnerLeft+= 2;runnerRight-=2;runnerTop+=2;
        if(runnerLeft >= ScreenWidth/2-70) {
            poweredDown = true;
            hasTakenPowerUp = false;
            invincibilityTime = 5;
            SPEED_X = speedX;
        }
    }

    public static void jump() {

        runnerTop-= JUMP_SPEED;
        runnerBottom-= JUMP_SPEED;
        JUMP_SPEED -= GRAVITY;
        if(runnerBottom >= ScreenHeight-STAGE_HEIGHT && !gameOver) {
            JUMP_SPEED = 25;
            IS_JUMPING = false;
        }if(gameOver && runnerTop > ScreenHeight) {
            JUMP_SPEED = 25;
            IS_JUMPING = false;
        }
    }
    public static void flyingObstacleFalls() {

        flyingObstacleTop += 30;
        flyingObstacleBottom += 30;
        if(flyingObstacleBottom >= 2*ScreenHeight) {
            flyingObstacleFell = true;
        }


    }
    public static void jumpForChaser() {

        chaserTop-= jumpSpeedForChaser;
        chaserBottom-= jumpSpeedForChaser;
        jumpSpeedForChaser -= GRAVITY;
        if(chaserBottom >= ScreenHeight-STAGE_HEIGHT) {
            jumpSpeedForChaser = 28;
            chaserIsJumping = false;
        }
    }
    public static void chaserIsComing() {
        if((chaserLeft+chaserRight)/2 <= ScreenWidth/4) {
            chaserRight += speedX;
            chaserLeft += speedX;
            SPEED_X = 10;
        } else {
            chaserIsChasing = false;
            SPEED_X = speedX;
        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
