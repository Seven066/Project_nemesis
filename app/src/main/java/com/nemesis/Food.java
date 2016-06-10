package com.nemesis;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;

/**
 * Created by Axel on 03.12.2015.
 */
public class Food {

    private int x;// координата X
    private int y;// координата Y
    private Bitmap bitmap;
    private Hero hero;
    public static final int RECT = 10;

    public Food(Bitmap bitmap, int x, int y, Hero hero) {
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
        this.hero = hero;
    }

    private static final String TAG = "FOOD TAG: ";

    public boolean checkEated() {
        if (((hero.getX() - x) * (hero.getX() - x) + (hero.getY() - y) * (hero.getY() - y)) < 2 * RECT * RECT) {
            Log.d(TAG, "FOOD EATED");
            //TODO change to layout height and width
            x = (int) (300*Math.random()) + 20;
            y = (int) (300*Math.random()) + 20;
            return true;
        } else {
            return false;
        }

    }

    public void draw(Canvas canvas) {
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
        } else {
            Paint paint = new Paint();
            paint.setColor(Color.BLUE);
            canvas.drawRect(x - RECT, y - RECT, x + RECT, y + RECT, paint);
        }
    }

    public int getX() {
        return x;
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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

}
