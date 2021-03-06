package com.nemesis;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * @author impaler
 *         This is the main surface that handles the ontouch events and draws
 *         the image to the screen.
 */
public class MainGamePanel extends SurfaceView implements
        SurfaceHolder.Callback {

    private static final String TAG = MainGamePanel.class.getSimpleName();

    private MainThread thread;

    Activity activity;

    Hero hero;
    Enemy enemy;
    Food food;
    GameOverAnimation gameOverAnimation;

    private static final int GAME = 1;
    private static final int GAMEOVERSCREEN = 2;
    private static final int MENUSCREEN = 3;

    private int gameStatus;

    public MainGamePanel(Context context, Activity activity) {
        super(context);
        this.activity = activity;
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        // create hero
        hero = new Hero(null, 50, 50);
        enemy = new Enemy(null, 400, 50, hero);
        food = new Food(dm, null, 400, 400, hero);
        gameOverAnimation = new GameOverAnimation();
        gameStatus = GAME;
        // create the game loop thread
        thread = new MainThread(getHolder(), this);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // at this point the surface is created and
        // we can safely start the game loop
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        // завершаем работу потока
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // если не получилось, то будем пытаться еще и еще
            }
        }
        Log.d(TAG, "Thread was shut down cleanly");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (gameStatus) {
            case GAME:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // delegating event handling to the droid
                    hero.handleActionDown((int) event.getX(), (int) event.getY());

                    // check if in the lower part of the screen we exit
                    if (event.getY() > getHeight() - 50) {
                        thread.setRunning(false);
                        ((Activity) getContext()).finish();
                    } else {
                        Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    // the gestures
                    // the droid was picked up and is being dragged
                    hero.handleActionDown((int) event.getX(), (int) event.getY());

                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // touch was released
                    if (hero.isTouched()) {
                        hero.setTouched(false);
                    }
                }
                break;
            case GAMEOVERSCREEN:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    gameOverAnimation.setTouch(true);
                }
                if (event.getAction() == MotionEvent.ACTION_UP && gameOverAnimation.isTouch()) {
                    gameOverAnimation.setTouch(false);
                    surfaceDestroyed(getHolder());
                    activity.recreate();
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (gameStatus) {
            case GAME:
                canvas.drawColor(Color.BLACK);
                hero.draw(canvas);
                enemy.draw(canvas);
                food.draw(canvas);
                break;
            case GAMEOVERSCREEN:
                gameOverAnimation.draw(canvas);
                gameOverAnimation.setScore(hero.getScore());
                break;
            default:
                break;
        }
    }

    /**
     * This is the game update method. It iterates through all the objects
     * and calls their update method if they have one or calls specific
     * engine's update method.
     */
    public void update() {
        switch (gameStatus) {
            case GAME:
                // check collision with right wall if heading right
                if (hero.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
                        && hero.getX() + hero.getWidth() / 2 >= getWidth()) {
                    hero.getSpeed().toggleXDirection();
                }
                // check collision with left wall if heading left
                if (hero.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
                        && hero.getX() - hero.getWidth() / 2 <= 0) {
                    hero.getSpeed().toggleXDirection();
                }
                // check collision with bottom wall if heading down
                if (hero.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
                        && hero.getY() + hero.getHeight() / 2 >= getHeight()) {
                    hero.getSpeed().toggleYDirection();
                }
                // check collision with top wall if heading up
                if (hero.getSpeed().getyDirection() == Speed.DIRECTION_UP
                        && hero.getY() - hero.getHeight() / 2 <= 0) {
                    hero.getSpeed().toggleYDirection();
                }
                //food check
                if (food.checkEated()) {
                    hero.incScore();
                    enemy.incSpeed();
                    Log.d(TAG, "Score: " + String.valueOf(hero.getScore()));
                }
                // Update the lone droid
                hero.update();
                enemy.update();

                if (enemy.checkCatch()) {
                    Log.d(TAG, "Вас сожрали");
                    gameStatus = GAMEOVERSCREEN;
                }
                break;
            case GAMEOVERSCREEN:
                gameOverAnimation.update();
                break;
            default:
                break;
        }
    }
}
