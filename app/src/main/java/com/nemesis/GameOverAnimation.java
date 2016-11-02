package com.nemesis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Fourp on 02.11.2016.
 * E-mail: 065@t-sk.ru
 */

public class GameOverAnimation {
    private int score = 0;
    private int lines = 0;

    public int getScore() {
        return score;
    }

    public boolean isTouch() {
        return touch;
    }

    public void setTouch(boolean touch) {
        this.touch = touch;
    }

    private boolean touch = false;
    Canvas canvas;


    public void draw(Canvas canvas) {
        this.canvas = canvas;
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawRect(0, 0, canvas.getWidth(), lines, paint);
        if (lines >= canvas.getHeight()) {
            paint.setColor(Color.BLACK);
            paint.setTextSize(canvas.getHeight() / 10);
            canvas.drawText("YOU LOSE", (float) 0.1 * canvas.getWidth(), (float) 0.4 * canvas.getHeight(), paint);
            canvas.drawText("Score: " + score, (float) 0.2 * canvas.getWidth(), (float) 0.55 * canvas.getHeight(), paint);
        }
    }

    public void update() {
        if (lines < canvas.getHeight()) {
            lines = lines + 40;
        }
    }

    public void setScore(int score) {
        this.score = score;
    }
}
