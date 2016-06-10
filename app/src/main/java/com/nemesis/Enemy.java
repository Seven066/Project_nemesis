package com.nemesis;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.RelativeLayout;

/**
 * Created by Fourp on 03.12.2015.
 * E-mail: 065@t-sk.ru
 */
public class Enemy {

    public static final int RECT = 10;
    private Bitmap bitmap;// картинка с героем
    private int x;// координата X
    private int y;// координата Y
    private int destinationX;// координата X точки назначения
    private int destinationY;// координата Y точки назначения
    private Speed speed;//скорость и направление
    private boolean touched;    // if droid is touched/picked up

    public Hero(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.speed = new Speed();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        if (bitmap == null)
            return RECT;
        else
            return bitmap.getWidth();
    }

    public int getHeight() {
        if (bitmap == null)
            return RECT;
        else
            return bitmap.getHeight();
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public void draw(Canvas canvas) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
        } else {
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            canvas.drawRect(x - RECT, y - RECT, x + RECT, y + RECT, paint);
        }
    }

    public void update() {

        speed.setxDirection((x > destinationX) ? speed.DIRECTION_LEFT : speed.DIRECTION_RIGHT);
        speed.setyDirection((y > destinationY) ? speed.DIRECTION_LEFT : speed.DIRECTION_RIGHT);
        speed.setXv(Math.abs((x - destinationX) / getWidth()));
        speed.setYv(Math.abs((y - destinationY) / getHeight()));
        x += (speed.getXv() * speed.getxDirection());
        y += (speed.getYv() * speed.getyDirection());

    }

    public void handleActionDown(int eventX, int eventY) {
        destinationX = eventX;
        destinationY = eventY;
    }
}