package com.example.virusattack;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import static com.example.virusattack.VirusGame.displayRatioX;
import static com.example.virusattack.VirusGame.displayRatioY;

public class Disinfectant {

    int x, y, width, height;
    Bitmap disinfectant;

    Disinfectant(Resources res) {
        disinfectant = BitmapFactory.decodeResource(res, R.drawable.disinfectant);
        width = disinfectant.getWidth();
        height = disinfectant.getHeight();
        width = (int) (width / 4 * displayRatioX);
        height = (int) (height / 4 * displayRatioY);
        disinfectant = Bitmap.createScaledBitmap(disinfectant, width, height, false);
    }

    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
