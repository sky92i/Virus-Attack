package com.example.virusattack;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VirusGame extends SurfaceView implements Runnable {

    private List<Disinfectant> disinfectants;
    private Virus[] viruses;
    private Paint paint;
    private Cleaner cleaner;
    private GameActivity activity;
    private Random random;
    private Thread thread;
    private boolean playing, gameOver = false;
    private int displayX, displayY, score = 0;
    public static float displayRatioX, displayRatioY;

    public VirusGame(GameActivity activity, int displayX, int displayY) {

        super(activity);
        this.activity = activity;
        this.displayX = displayX;
        this.displayY = displayY;
        displayRatioX = 1920f / displayX;
        displayRatioY = 1080f / displayY;

        cleaner = new Cleaner(displayY, getResources());
        disinfectants = new ArrayList<>();

        paint = new Paint();
        paint.setTextSize(90);
        paint.setColor(Color.YELLOW);

        viruses = new Virus[7];
        int v = 0;
        while (v < 7) {
            Virus virus = new Virus(getResources());
            viruses[v] = virus;
            v++;
        }
        random = new Random();
    }

    @Override
    public void run() {
        while (playing) {
            refresh ();
            draw ();
            sleep ();
        }
    }

    private void refresh () {

        if (cleaner.y < 0)
            cleaner.y = 0;
        if (cleaner.y >= displayY - cleaner.height)
            cleaner.y = displayY - cleaner.height;

        if (cleaner.rising)
            cleaner.y -= 20 * displayRatioY;
        else
            cleaner.y += 20 * displayRatioY;

        List<Disinfectant> used = new ArrayList<>();

        int d = 0;
        while (d < disinfectants.size()) {
            if (disinfectants.get(d).x > displayX)
                used.add(disinfectants.get(d));
            disinfectants.get(d).x += 50 * displayRatioX;

            int v = 0;
            while (v < viruses.length) {
                if (Rect.intersects(viruses[v].getCollisionShape(),
                        disinfectants.get(d).getCollisionShape())) {
                    viruses[v].x = -500;
                    disinfectants.get(d).x = displayX + 500;
                    viruses[v].dead = true;
                    score++;
                }
                v++;
            }
            d++;
        }

        int u = 0;
        while (u < used.size()) {
            disinfectants.remove(used.get(u));
            u++;
        }

        int v = 0;
        while (v < viruses.length) {
            viruses[v].x -= viruses[v].speed;

            if (viruses[v].x + viruses[v].width < 0) {
                if (!viruses[v].dead) {
                    gameOver = true;
                    return;
                }

                int bound = (int) (30 * displayRatioX);
                viruses[v].speed = random.nextInt(bound);

                if (viruses[v].speed < 10 * displayRatioX)
                    viruses[v].speed = (int) (10 * displayRatioX);

                viruses[v].x = displayX;
                viruses[v].y = random.nextInt(displayY - viruses[v].height);
                viruses[v].dead = false;
            }

            if (Rect.intersects(viruses[v].getCollisionShape(), cleaner.getCollisionShape())) {
                gameOver = true;
                return;
            }
            v++;
        }
    }

    private void draw () {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();
            canvas.drawColor(getResources().getColor(R.color.background));

            for (int v = 0; v < viruses.length; v++)
                canvas.drawBitmap(viruses[v].virus, viruses[v].x, viruses[v].y, paint);

            canvas.drawText("Score: " + score, displayX / 2.3f, 134, paint);

            if (gameOver) {
                playing = false;
                canvas.drawBitmap(cleaner.getInfected(), cleaner.x, cleaner.y, paint);
                getHolder().unlockCanvasAndPost(canvas);
                exit ();
                return;
            }

            canvas.drawBitmap(cleaner.cleaner, cleaner.x, cleaner.y, paint);

            for (int d = 0; d < disinfectants.size(); d++)
                canvas.drawBitmap(disinfectants.get(d).disinfectant, disinfectants.get(d).x, disinfectants.get(d).y, paint);

            getHolder().unlockCanvasAndPost(canvas);

        }

    }

    private void sleep () {
        try {
            Thread.sleep(10);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    private void exit() {
        try {
            Thread.sleep(2000);
            activity.finish();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public void resume () {
        playing = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause () {
        try {
            playing = false;
            thread.join();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() < displayX) {
                    cleaner.rising = true;
                    cleaner.toShoot++;
                    newDisinfectant();
                }
                break;
            case MotionEvent.ACTION_UP:
                cleaner.rising = false;
                break;
        }
        return true;
    }

    public void newDisinfectant() {
        Disinfectant disinfectant = new Disinfectant(getResources());
        disinfectant.x = cleaner.x + cleaner.width;
        disinfectant.y = cleaner.y + (cleaner.height / 2);
        disinfectants.add(disinfectant);
    }
}
