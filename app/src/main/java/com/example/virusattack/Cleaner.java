package com.example.virusattack;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import static com.example.virusattack.VirusGame.displayRatioX;
import static com.example.virusattack.VirusGame.displayRatioY;

public class Cleaner {

    int toShoot = 0;
    boolean rising = false;
    int x, y, width, height;
    Bitmap cleaner, infected;

    Cleaner(int screenY, Resources res) {
        cleaner = BitmapFactory.decodeResource(res, R.drawable.cleaner);
        width = cleaner.getWidth();
        height = cleaner.getHeight();
        width = (int) (width / 6 * displayRatioX);
        height = (int) (height / 6 * displayRatioY);
        cleaner = Bitmap.createScaledBitmap(cleaner, width, height, false);

        infected = BitmapFactory.decodeResource(res, R.drawable.infected);
        infected = Bitmap.createScaledBitmap(infected, width, height, false);

        y = screenY / 2;
        x = (int) (64 * displayRatioX);
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }

    Bitmap getInfected () {
        return infected;
    }
}
