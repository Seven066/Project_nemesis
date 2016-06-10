package com.nemesis;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;


/**
 * @author impaler
 *         <p/>
 *         The Main thread which contains the game loop. The thread must have access to
 *         the surface view and holder to trigger events every game tick.
 */
public class MainThread extends Thread {

    private static final String TAG = MainThread.class.getSimpleName();

    // Surface holder that can access the physical surface
    private SurfaceHolder surfaceHolder;
    // The actual view that handles inputs
    // and draws to the surface
    private MainGamePanel gamePanel;

    // flag to hold game state
    private boolean running;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        Canvas canvas;
        long tickCount = 0L;
        Log.d(TAG, "Starting game loop");
        while (running) {
            tickCount++;
            // update game state
            // render state to the screen
            canvas = null;  // пытаемся заблокировать canvas
                            // для изменение картинки на поверхности
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {      // здесь будет обновляться состояние игры
                    this.gamePanel.update();                                // и формироваться кадр для вывода на экран
                    this.gamePanel.onDraw(canvas);  //Вызываем метод для рисования
                }
            } finally { // в случае ошибки, плоскость не перешла в
                        //требуемое состояние
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
        Log.d(TAG, "Game loop executed " + tickCount + " times");
    }

}
