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
    static int c1Left = ScreenWidth/4, c1Top = 100;// cloud 1
    static int c2Left = ScreenWidth*3/4, c2Top = 100;// cloud 2
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
    static int chaserLeft = -400,
    chaserRight = 0,
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
        tallObstacleRight = tallObstacleLeft+70,
        tallObstacleBottom = ScreenHeight-STAGE_HEIGHT+15;

    //sound
    static MediaPlayer jumpSound;
    static MediaPlayer painSound;
    static MediaPlayer deathSound;
    static MediaPlayer heroTheme;
    static MediaPlayer villainTheme;

    public myCanvas(Context context) {
        super(context);
        cloud1 = BitmapFactory.decodeResource(getResources(), R.drawable.img);
        cloud2 = BitmapFactory.decodeResource(getResources(), R.drawable.img);
        runner = BitmapFactory.decodeResource(getResources(), R.drawable.mario);
        chaser = BitmapFactory.decodeResource(getResources(), R.drawable.bowser);
        shortObstacle = BitmapFactory.decodeResource(getResources(), R.drawable.smallobstacle);
        tallObstacle = BitmapFactory.decodeResource(getResources(), R.drawable.tallobstacle);
        jumpSound = MediaPlayer.create(getContext(), R.raw.jumpsound);
        painSound = MediaPlayer.create(getContext(), R.raw.painsound);
        deathSound = MediaPlayer.create(getContext(), R.raw.deathsound);
        heroTheme = MediaPlayer.create(getContext(), R.raw.herotheme);
        villainTheme = MediaPlayer.create(getContext(), R.raw.villantheme);
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
        canvas.drawBitmap(cloud1,c1Left,c1Top,null);
        canvas.drawBitmap(cloud2, c2Left, c2Top, null);
        c1Left -= SPEED_X-SPEED_X*2/3;
        if (c1Left + cloud1.getWidth() < 0) {
            c1Left = ScreenWidth;
        }
        c2Left -= SPEED_X-SPEED_X*2/3;
        if (c2Left + cloud2.getWidth() < 0) {
            c2Left = ScreenWidth;
        }

        //runner hero
        RectF runnerHitBox = new RectF(runnerLeft, runnerTop, runnerRight, runnerBottom);
        canvas.drawBitmap(runner, null,runnerHitBox , null);

        //obstacles
        RectF tallObstacleHitBox = new RectF(tallObstacleLeft, tallObstacleTop, tallObstacleRight, tallObstacleBottom);
        RectF smallObstacleHitBox = new RectF(shortObstacleLeft, shortObstacleTop, shortObstacleRight, shortObstacleBottom);
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
                Random r = new Random();
                if(r.nextInt(2) == 0) {
                    smallObstacleIsComing = false;
                } else {
                    smallObstacleIsComing = true;
                }
                count++;
                if(count % 4 == 0) if(SPEED_X <= 40) SPEED_X+=1;
                if(chaserIsChasing) obstaclePassedCount ++;
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
                tallObstacleRight = tallObstacleLeft + 70;
                Random r = new Random();
                if(r.nextInt(2) == 0) {
                    smallObstacleIsComing = false;
                } else {
                    smallObstacleIsComing = true;
                }
                if(count % 4 == 0) if(SPEED_X <= 40) SPEED_X+=1;
                if(chaserIsChasing) obstaclePassedCount ++;
            }

        }

        //collision detection
        RectF chaserHitBox = new RectF(chaserLeft, chaserTop, chaserRight, chaserBottom);
        canvas.drawBitmap(chaser, null, chaserHitBox, null);

        if(runnerHitBox.intersect(smallObstacleHitBox)){
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
        if(runnerHitBox.intersect(tallObstacleHitBox)) {
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
