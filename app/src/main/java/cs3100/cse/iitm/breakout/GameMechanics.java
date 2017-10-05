package cs3100.cse.iitm.breakout;

/**
 * Created by Arjun Balgovind on 03-10-2017.
 */

public class GameMechanics {

    private AttackBall ballObj;
    private Paddle paddleObj;
    private Block[][] blockList;
    private int bHeight;
    private int bWidth;
    public int Score;
    private int blockScore;

    public GameMechanics(AttackBall ball, Paddle paddle, Block[][] blocks, int H, int W)
    {
        ballObj = ball;
        paddleObj = paddle;
        blockList = blocks;
        bHeight = H;
        bWidth = W;
        blockScore = 100;
        Score = 0;
    }

    //Return region for normal collision(1,2,3,4) and 5 for corner
    public int collision_ball_paddle()
    {
        if(ballObj.getX()<=paddleObj.paddleBox.right&&ballObj.getX()>=paddleObj.paddleBox.left)
        {
            if(ballObj.getY()+ballObj.getRadius()>=paddleObj.paddleBox.top) {
                if(ballObj.getPrev_x()>paddleObj.paddleBox.right||ballObj.getPrev_x()<paddleObj.paddleBox.left)
                {
                    if(ballObj.getPrev_y()<paddleObj.paddleBox.top)
                        return -1;
                }
                ballObj.setY(paddleObj.paddleBox.top-ballObj.getRadius());
                return getRegion();
            }
            else
                return 0;
        }
        else
        {
            if(ballObj.getX()>paddleObj.paddleBox.right)
            {
                int corner_distance=(ballObj.getX()-paddleObj.paddleBox.right)*(ballObj.getX()-paddleObj.paddleBox.right)+
                        (ballObj.getY()-paddleObj.paddleBox.top)*(ballObj.getY()-paddleObj.paddleBox.top);
                if(ballObj.getY()>paddleObj.paddleBox.top)
                {
                    return -1;
                }

                else if(corner_distance<=ballObj.getRadius()*ballObj.getRadius())
                    return 5;
                else
                    return 0;
            }
            else if(ballObj.getX()<paddleObj.paddleBox.left)
            {
                int corner_distance=(ballObj.getX()-paddleObj.paddleBox.left)*(ballObj.getX()-paddleObj.paddleBox.left)+
                        (ballObj.getY()-paddleObj.paddleBox.top)*(ballObj.getY()-paddleObj.paddleBox.top);
                if(ballObj.getY()>paddleObj.paddleBox.top)
                {
                    return -1;
                }

                else if(corner_distance<=ballObj.getRadius()*ballObj.getRadius())
                    return 5;
                else
                    return 0;
            }
        }
        return 0;
    }

    private int getRegion()
    {
        int paddle_width=paddleObj.paddleBox.right-paddleObj.paddleBox.left;
        if(ballObj.getX()<paddleObj.paddleBox.left+(paddle_width/4))
            return 1;
        else if(ballObj.getX()<paddleObj.paddleBox.left+(2*paddle_width/4))
            return 2;
        else if(ballObj.getX()<paddleObj.paddleBox.left+(3*paddle_width/4))
            return 3;
        else
            return 4;
    }

    public boolean collision_paddle_handler()
    {
        int case_val=collision_ball_paddle();
        if(case_val==0)
            return true;
        else if(case_val==1)
        {
            ballObj.setSpeeds(210); //hspeed and vspeed -ve
        }
        else if(case_val==2)
        {
            ballObj.setSpeeds(240); //hspeed and vspeed -ve
        }
        else if(case_val==3)
        {
            ballObj.setSpeeds(300); //hspeed +ve and vspeed -ve
        }
        else if(case_val==4)
        {
            ballObj.setSpeeds(330); //hspeed +ve and vspeed -ve
        }
        else if(case_val == -1)
        {
            return false;
        }
        else
        {
            ballObj.sethSpeed(-ballObj.gethSpeed());
            ballObj.setvSpeed(-ballObj.getvSpeed());
        }
        return true;
    }

    // Considering normal reflection on block surfaces
    public void collision_blocks()
    {
        int row_count=blockList.length;
        int col_count=blockList[0].length;
        // 10 is no. of pixels between two blocks vertically
        if(ballObj.getY()-ballObj.getRadius()>((bHeight+10)*col_count))
            // No intersecting objects
            return;
        else
        {
            int index_top = -1+(int)((ballObj.getY()-ballObj.getRadius())/(bHeight+10));
            int index_left = -1+(int)((ballObj.getX()-ballObj.getRadius())/(bWidth*1.1));
            int collideFlag=0;
            for(int i=0;i<row_count;i++)
            {
                for(int j=0;j<col_count;j++)
                {
                    if(blockList[i][j].checkLife())
                    {
                        if(ballObj.getX()<=blockList[i][j].getRect().right&&ballObj.getX()>=blockList[i][j].getRect().left)
                        {
                            if(ballObj.getY()+ballObj.getRadius()>=blockList[i][j].getRect().top&&ballObj.getY()+ballObj.getRadius()<=blockList[i][j].getRect().bottom||ballObj.getY()-ballObj.getRadius()<=blockList[i][j].getRect().bottom&&ballObj.getY()-ballObj.getRadius()>=blockList[i][j].getRect().top)
                            {
                                collideFlag=1;
                                blockList[i][j].killBlock();
                                Score+=blockScore;
                            }
                        }
                        else if(ballObj.getY()<=blockList[i][j].getRect().bottom&&ballObj.getY()>=blockList[i][j].getRect().top)
                        {
                            if(ballObj.getX()+ballObj.getRadius()>=blockList[i][j].getRect().left
                                    &&ballObj.getX()+ballObj.getRadius()<=blockList[i][j].getRect().right
                                    ||ballObj.getX()-ballObj.getRadius()<=blockList[i][j].getRect().right
                                    &&ballObj.getX()-ballObj.getRadius()>=blockList[i][j].getRect().left)
                            {
                                collideFlag=2;
                                blockList[i][j].killBlock();
                                Score+=blockScore;
                            }
                        }
                        else
                        {
                            int corner_distance1=(ballObj.getX()-blockList[i][j].getRect().right)*(ballObj.getX()-blockList[i][j].getRect().right)+
                                        (ballObj.getY()-blockList[i][j].getRect().top)*(ballObj.getY()-blockList[i][j].getRect().top);
                            int corner_distance2=(ballObj.getX()-blockList[i][j].getRect().left)*(ballObj.getX()-blockList[i][j].getRect().left)+
                                    (ballObj.getY()-blockList[i][j].getRect().top)*(ballObj.getY()-blockList[i][j].getRect().top);
                            int corner_distance3=(ballObj.getX()-blockList[i][j].getRect().right)*(ballObj.getX()-blockList[i][j].getRect().right)+
                                    (ballObj.getY()-blockList[i][j].getRect().bottom)*(ballObj.getY()-blockList[i][j].getRect().bottom);
                            int corner_distance4=(ballObj.getX()-blockList[i][j].getRect().left)*(ballObj.getX()-blockList[i][j].getRect().left)+
                                    (ballObj.getY()-blockList[i][j].getRect().bottom)*(ballObj.getY()-blockList[i][j].getRect().bottom);
                            int min_dist = Math.min(Math.min(Math.min(corner_distance1,corner_distance2),corner_distance3),corner_distance4);
                            if(min_dist<=ballObj.getRadius()*ballObj.getRadius())
                            {
                                collideFlag=1;
                                blockList[i][j].killBlock();
                                Score+=blockScore;
                            }
                        }
                    }
                }
            }
            if(collideFlag==1)
            {
                ballObj.setvSpeed(-ballObj.getvSpeed());
            }
            else if(collideFlag==2)
            {
                ballObj.sethSpeed(-ballObj.gethSpeed());
            }
            //return collideFlag;
        }
    }

    public boolean isWin()
    {
        if(Score==blockScore*blockList[0].length*blockList.length)
        {
            return true;
        }
        else
            return false;
    }
}
