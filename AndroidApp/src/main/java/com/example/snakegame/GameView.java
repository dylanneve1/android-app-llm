package com.example.snakegame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        gameView = new GameView(this);
        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}

class GameView extends SurfaceView implements Runnable {
    private Thread thread;
    private boolean isPlaying;
    private Paint paint;

    public GameView(MainActivity context) {
        super(context);
        paint = new Paint();
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            control();
        }
    }

    private void update() {
        // Update game logic here
    }

    private void draw() {
        Canvas canvas = getHolder().lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            // Draw game elements here
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }
}
private int snakeLength;
private int[] snakeX, snakeY;
private int foodX, foodY;

public GameView(MainActivity context) {
    super(context);
    paint = new Paint();
    snakeLength = 1;
    snakeX = new int[100];
    snakeY = new int[100];
    foodX = generateFood();
    foodY = generateFood();
}

private int generateFood() {
    return (int) (Math.random() * (getWidth() / snake_block)) * snake_block;
}

private void update() {
    // Move the snake
    for (int i = snakeLength; i > 0; i--) {
        snakeX[i] = snakeX[i - 1];
        snakeY[i] = snakeY[i - 1];
    }
    snakeX[0] += x1_change;
    snakeY[0] += y1_change;

    // Check for food collision
    if (snakeX[0] == foodX && snakeY[0] == foodY) {
        snakeLength++;
        foodX = generateFood();
        foodY = generateFood();
    }
}
private void draw() {
    Canvas canvas = getHolder().lockCanvas();
    if (canvas != null) {
        canvas.drawColor(Color.BLACK);
        drawSnake(canvas);
        drawFood(canvas);
        getHolder().unlockCanvasAndPost(canvas);
    }
}

private void drawSnake(Canvas canvas) {
    for (int i = 0; i < snakeLength; i++) {
        canvas.drawRect(snakeX[i], snakeY[i], snakeX[i] + snake_block, snakeY[i] + snake_block, paint);
    }
}

private void drawFood(Canvas canvas) {
    paint.setColor(Color.RED);
    canvas.drawRect(foodX, foodY, foodX + snake_block, foodY + snake_block, paint);
}
private void update() {
    // Move the snake
    for (int i = snakeLength; i > 0; i--) {
        snakeX[i] = snakeX[i - 1];
        snakeY[i] = snakeY[i - 1];
    }
    snakeX[0] += x1_change;
    snakeY[0] += y1_change;

    // Check for food collision
    if (snakeX[0] == foodX && snakeY[0] == foodY) {
        snakeLength++;
        foodX = generateFood();
        foodY = generateFood();
    }

    // Check for collision with walls or self
    if (snakeX[0] < 0 || snakeX[0] >= getWidth() || snakeY[0] < 0 || snakeY[0] >= getHeight()) {
        gameOver();
    }
    for (int i = 1; i < snakeLength; i++) {
        if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
            gameOver();
        }
    }
}

private void gameOver() {
    isPlaying = false;
    // Handle game over logic here
}
@Override
public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
        float x = event.getX();
        float y = event.getY();
        if (x < getWidth() / 2) {
            x1_change = -snake_block;
            y1_change = 0;
        } else {
            x1_change = snake_block;
            y1_change = 0;
        }
    }
    return true;
}
