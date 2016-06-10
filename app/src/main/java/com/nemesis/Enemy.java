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
//COMMIT TEST
public class Enemy {

    public static final int RECT = 10;
    private Bitmap bitmap;// картинка с героем
    private int x;// координата X
    private int y;// координата Y
    private int destinationX;// координата X точки назначения
    private int destinationY;// координата Y точки назначения
    private Speed speed;//скорость и направление
    private int Vk; //коофициент на скорость
    private boolean touched;    // if droid is touched/picked up
    private Hero hero;

    public Enemy(Bitmap bitmap, int x, int y, Hero hero) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.hero = hero;
        this.speed = new Speed();
        Vk = 5;
    }
    public Enemy(Bitmap bitmap, int x, int y, int Vk, Hero hero) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.hero = hero;
        this.speed = new Speed();
        this.Vk = Vk;
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
        destinationX = hero.getX();
        destinationY = hero.getY();

        float sinA, cosA, hypotenuse;
        hypotenuse = (float) Math.sqrt((x - destinationX) * (x - destinationX) + (y - destinationY) * (y - destinationY));

        if (hypotenuse > 1) {
            sinA = Math.abs(y - destinationY) / hypotenuse;
            cosA = Math.abs(x - destinationX) / hypotenuse;
        } else {
            sinA = 0;
            cosA = 0;
        }

        speed.setxDirection((x > destinationX) ? speed.DIRECTION_LEFT : speed.DIRECTION_RIGHT);
        speed.setyDirection((y > destinationY) ? speed.DIRECTION_LEFT : speed.DIRECTION_RIGHT);
        speed.setXv(cosA);
        speed.setYv(sinA);
        x += (Vk * speed.getXv() * speed.getxDirection());
        y += (Vk * speed.getYv() * speed.getyDirection());

    }

    public void handleActionDown(int eventX, int eventY) {
        destinationX = eventX;
        destinationY = eventY;
    }
}