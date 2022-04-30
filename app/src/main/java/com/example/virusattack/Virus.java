package com.example.virusattack;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import static com.example.virusattack.VirusGame.displayRatioX;
import static com.example.virusattack.VirusGame.displayRatioY;

public class Virus {

    public int speed = 20;
    public boolean dead = true;
    int x = 0, y, width, height;
    Bitmap virus;

    Virus(Resources res) {
        virus = BitmapFactory.decodeResource(res, R.drawable.virus);
        width = virus.getWidth();
        height = virus.getHeight();
        width = (int) (width / 8 * displayRatioX);
        height = (int) (height / 8 * displayRatioY);
        virus = Bitmap.createScaledBitmap(virus, width, height, false);
        y = -height;
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
