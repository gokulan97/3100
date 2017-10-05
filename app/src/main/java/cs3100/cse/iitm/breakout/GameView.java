package cs3100.cse.iitm.breakout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by gokulan on 10/4/17.
 */

public class GameView extends View {

    private Block[][] blocks;
    private Paint paint;
    private Paddle paddle;
    private AttackBall ball;
    public Rect rect;

    public GameView(Context context)
    {
        super(context);

        paint = new Paint();
        paint.setColor(Color.BLUE);

        rect = new Rect(100, 100, 200, 200);

        paddle = new Paddle();
        ball = new AttackBall();

        blocks = new Block[5][9];
        for(int i=0; i<5; i++) {
            for (int j = 0; j < 9; j++) {
                blocks[i][j] = new Block();
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);

        int x = canvas.getWidth()/10;

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        for(int i=0; i<5; i++)
        {
            int y = 10;
            for(int j=0; j<9; j++)
            {
                blocks[i][j].getRect().set(x, y, x + width/10, y + height/20);
            }
        }
    }

    public AttackBall getBall()
    {return ball;}

    public Paddle getPaddle()
    {return paddle;}

    public Block[][] getBlocks()
    {return blocks;}

}
