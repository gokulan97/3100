package cs3100.cse.iitm.breakout;

import android.graphics.Rect;

/**
 * Created by Arjun Balgovind on 02-10-2017.
 */

public class Block {

    private Rect rect;
    private boolean isAlive;

    public Block()
    {
        rect = new Rect();
        isAlive = true;
    }

    public void setRect(int X1, int Y1, int X2, int Y2)
    {
        rect.set(X1, Y1, X2, Y2);
    }

    public Rect getRect()
    {
        return rect;
    }

    public boolean checkLife()
    {
        return isAlive;
    }

    public void killBlock()
    {
        isAlive=false;
        rect.setEmpty();
    }
}
