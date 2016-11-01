package com.nemesis;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.DialogInterface;

/**
 * Created by Alex on 01.11.2016.
 * E-mail: cahek4293@gmail.com
 */

public class GameOverDialog extends DialogFragment {
    private int score;
    public GameOverDialog(int score) {
        this.score = score;
    }
}
