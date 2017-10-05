package cs3100.cse.iitm.breakout;

import android.graphics.RectF;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;

/**
 * Created by Arjun Balgovind on 02-10-2017.
 */

public class AttackBall {
    private int x_centre;
    private int y_centre;
    private int radius;
    private int vSpeed;       //+ve means going up, -ve means going down
    private int hSpeed;       //+ve means going right, -ve means going left
    private int totalSpeed;

    public RectF ballShape;

    public AttackBall()
    {
        ballShape = new RectF();
    }

    public void setBall(int X, int Y, int R, int V, int H)
    {
        x_centre=X;
        y_centre=Y;
        radius=R;
        vSpeed=V;
        hSpeed=H;
        totalSpeed=(int) Math.sqrt(V*V+H*H);
        ballShape.set(X-R, Y-R, X+R, Y+R);
    }

    public int getX()
    {
        return x_centre;
    }

    public int getY()
    {
        return y_centre;
    }

    public int getRadius()
    {
        return radius;
    }

    public void setX(int x_new)
    {
        x_centre=x_new;
        ballShape.set(x_centre-radius,y_centre-radius,x_centre+radius,y_centre+radius);
    }

    public void setY(int y_new)
    {
        y_centre=y_new;
        ballShape.set(x_centre-radius,y_centre-radius,x_centre+radius,y_centre+radius);

    }

    public void setSpeeds(int theta) {
        int total_speed=hSpeed*hSpeed+vSpeed*vSpeed;
        hSpeed= (int) (totalSpeed*Math.cos(Math.toRadians(theta)));
        vSpeed= (int) (totalSpeed*Math.sin(Math.toRadians(theta)));
    }

    public int getvSpeed()
    {
        return vSpeed;
    }

    public int gethSpeed()
    {
        return hSpeed;
    }

    public void setvSpeed(int vspeed) {
        this.vSpeed = vspeed;
    }

    public void sethSpeed(int hspeed) {
        this.hSpeed = hspeed;
    }

    // Function to move the ball and update speeds for a time duration
    public void moveBall(int wall_width, int wall_height)      // Decide a optimum time_frame for each movement
    {
        Log.d("BALL POSITION1", String.valueOf(getX()) + ":" + String.valueOf(getY()));
        y_centre+=(vSpeed);
        x_centre+=(hSpeed);
        ballShape.set(x_centre-radius,y_centre-radius,x_centre+radius,y_centre+radius);


        //Update speeds based on collision
        if(isTouchSidewalls(wall_width))
        {
            hSpeed=-hSpeed;
        }

        if(isTouchTopWall())
        {
            vSpeed=-vSpeed;
        }
        //Should we check if it reaches screen bottom
        Log.d("BALL POSITION", String.valueOf(getX()) + ":" + String.valueOf(getY()));

    }

    // Function to check if the ball is touching the Side Walls
    public boolean isTouchSidewalls(int wall_width)
    {
        if(((x_centre-radius)<=0))      //If ball leaves the screen bound, set back into screen bound
        {
            x_centre=radius;
            ballShape.set(x_centre-radius,y_centre-radius,x_centre+radius,y_centre+radius);

            return true;
        }
        else if(((x_centre+radius)>=wall_width))       //Returns 1 for right wall
        {
            x_centre=wall_width-radius;
            ballShape.set(x_centre-radius,y_centre-radius,x_centre+radius,y_centre+radius);

            return true;
        }
        return false;
    }

    // Function to check if the ball is touching the Side Walls
    public boolean isTouchTopWall()
    {
        if(y_centre-radius<=0)
        {
            y_centre=radius;
            ballShape.set(x_centre-radius,y_centre-radius,x_centre+radius,y_centre+radius);

            return true;
        }
            return false;
    }
}
