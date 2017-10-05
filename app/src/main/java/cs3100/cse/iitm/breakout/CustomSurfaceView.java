package cs3100.cse.iitm.breakout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by gokulan on 10/6/17.
 */

public class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    public DrawThread drawThread;
    public Block[][] blocks;
    public Paint paint;
    public Paddle paddle;
    public AttackBall ball;
    public GameMechanics mechanics;

    public CustomSurfaceView(Context context) {
        super(context);
        initialize();
    }

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public CustomSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void initialize() {

        getHolder().addCallback(this);

        paint = new Paint(Color.GREEN);
        blocks = new Block[5][9];
        ball = new AttackBall();
        paddle = new Paddle();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        Log.e("WH", width + "" + height);
        ball.setBall(width/2, height/2, width/50, 70, 0);
        paddle.setPaddleBox(width/3, (9*height)/10, (2*width)/3, (19*height)/20);

        int y = 10;

        for(int i=0; i<5; i++)
        {
            int x = width/100;
            for (int j = 0; j < 9; j++)
            {
                blocks[i][j] = new Block();
                blocks[i][j].getRect().set(x, y, x+width/10, y+height/20);
                x += width/10 + width/100;
            }

            y += height/20 + 10;
        }

        mechanics = new GameMechanics(ball, paddle, blocks, height/20, width/10);
    }

    public void startThread() {
        drawThread = new DrawThread(getHolder(), this);
        drawThread.setRunning(true);
        drawThread.start();
    }

    public void stopThread() {
        drawThread.setRunning(false);
        drawThread.stop();
    }

    public int update() {
        if(mechanics.isWin())
        {
            //add code for user win
            Log.e("WIN", "You win!!");
            return 1;
        }
        else {
            paddle.move(0, 1440);
            ball.moveBall(1440, 2200);
            boolean b = mechanics.collision_paddle_handler();
            if (!b) {
                //add code for user lost
                Log.e("LOSE", "You lose!");
                return 0;
            }
            mechanics.collision_blocks();
        }
        return -1;
    }

    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        for(int i=0; i<5; i++)
        {
            for(int j=0; j<9; j++)
            {
                canvas.drawRect(blocks[i][j].getRect(), paint);
            }
        }

        canvas.drawRect(paddle.paddleBox, paint);
        canvas.drawRoundRect(ball.ballShape, ball.getRadius(), ball.getRadius(), paint);
    }

    class DrawThread extends Thread {
        private SurfaceHolder surfaceHolder;
        CustomSurfaceView mSurfaceView;
        private boolean run = false;

        public DrawThread(SurfaceHolder surfaceHolder,
                          CustomSurfaceView mSurfaceView) {
            this.surfaceHolder = surfaceHolder;
            this.mSurfaceView = mSurfaceView;
            run = false;
        }

        public void setRunning(boolean run) {
            this.run = run;
        }

        @Override
        public void run() {
            Canvas canvas = null;
            while (run) {
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        if(canvas != null) {
                            Log.d("NULL", "canvas is not null");
                            mSurfaceView.onDraw(canvas);
                        }

                        int status=mSurfaceView.update();
                        if(status==1)
                        {
                            //Win
                            stopThread();
                        }
                        else if(status==0)
                        {
                            //Loss, display score(mechanics.Score)
                            stopThread();
                        }
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
