package cs3100.cse.iitm.breakout;

import android.graphics.Rect;

/**
 * Created by Arjun Balgovind on 04-10-2017.
 */

public class Paddle {
    public Rect paddleBox;

    public Paddle()
    {
        paddleBox = new Rect();
    }

    public void setPaddleBox(int x, int y, int x0, int y0)
    {
        paddleBox.set(x, y, x0, y0);
    }

    public void move(int delta_x, int wall_width)
    {
        paddleBox.left+=delta_x;
        paddleBox.right+=delta_x;
        if(paddleBox.right>=wall_width)
        {
            int change=paddleBox.right-wall_width;
            paddleBox.right=wall_width;
            paddleBox.left-=change;
        }
        if(paddleBox.left<=0)
        {
            int change=paddleBox.left;
            paddleBox.right-=change;
            paddleBox.left=0;
        }
    }

    public void paddleMotion()
    {
        //Add gravity sensor code
    }




}
