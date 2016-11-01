package com.nemesis;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private static final String TAG = MainActivity.class.getSimpleName();


    MainGamePanel mainGamePanel;

    public static int screen_width;
    public static int screen_height;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // making it full screen and block screen rotation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        final Context context = this;

        Button newGameButton = (Button) findViewById(R.id.newgamebutton);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set our MainGamePanel as the View
                mainGamePanel = new MainGamePanel(context);
                screen_width = mainGamePanel.getWidth();
                screen_height = mainGamePanel.getHeight();
                setContentView(mainGamePanel);
            }
        });

    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Destroying...");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (mainGamePanel != null) {
            mainGamePanel.surfaceDestroyed(mainGamePanel.getHolder());
            this.recreate();
        } else super.onBackPressed();
    }
}