package com.example.virusattack;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {

    private VirusGame virusGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        virusGame = new VirusGame(this, point.x, point.y);
        setContentView(virusGame);
    }

    @Override
    protected void onPause() {
        super.onPause();
        virusGame.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        virusGame.resume();
    }
}