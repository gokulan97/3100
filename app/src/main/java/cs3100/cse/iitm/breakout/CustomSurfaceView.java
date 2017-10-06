package cs3100.cse.iitm.breakout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    public Paint[] paints;
    public Paddle paddle;
    public AttackBall ball;
    public GameMechanics mechanics;

    public int win = -1;

    public int paddleMove;
    public int width, height;

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

        paddleMove = 0;

        paints = new Paint[7];

        for(int i=0; i<7; i++)
        {
            paints[i] = new Paint();
        }

        paints[0].setColor(Color.MAGENTA);
        paints[1].setColor(Color.GRAY);
        paints[2].setColor(Color.CYAN);
        paints[3].setColor(Color.YELLOW);
        paints[4].setColor(Color.RED);

        paints[5].setColor(Color.BLUE);

        paints[6].setColor(Color.GREEN);

        blocks = new Block[5][9];
        ball = new AttackBall();
        paddle = new Paddle();

        width = getResources().getDisplayMetrics().widthPixels;
        height = getResources().getDisplayMetrics().heightPixels;

        Log.e("WH", width + "" + height);
        ball.setBall(width/2, height/2, width/40, (int)(Math.sqrt((width/50)*(height/100))), 0);
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

    }

    public int update() {
        if(mechanics.isWin())
        {
            //add code for user win
            Log.e("WIN", "You win!!");
            win = 1;
            return 1;
        }
        else {
            paddle.move(paddleMove, width);
            ball.moveBall(width);
            boolean b = mechanics.collision_paddle_handler();
            if (!b) {
                //add code for user lost
                win = 0;
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
                canvas.drawRect(blocks[i][j].getRect(), paints[i]);
            }
        }

        canvas.drawRect(paddle.paddleBox, paints[6]);
        canvas.drawRoundRect(ball.ballShape, ball.getRadius(), ball.getRadius(), paints[5]);
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
            int status=-1;
            Canvas canvas = null;
            while (run) {
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        if(canvas != null) {
                            Log.d("NULL", "canvas is not null");
                            mSurfaceView.onDraw(canvas);
                        }

                        status=mSurfaceView.update();
                        if(status==1)
                        {
                            stopThread();

                        }
                        else if(status==0)
                        {
                            stopThread();

                        }
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
            Intent i = new Intent(mSurfaceView.getContext() , FinishActivity.class);
            i.putExtra("status", (status==0)?"lose":"win");
            i.putExtra("score", String.valueOf(mechanics.Score));
            mSurfaceView.getContext().startActivity(i);

        }
    }
}
