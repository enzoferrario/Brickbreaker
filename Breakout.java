/*
 * File: Breakout.java
 * -------------------
 * This file will eventually implement the game of Breakout.
 *
 * TODO: Update this file with a description of what your program
 * actually does!
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH =
			(WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	private static final int PAUSE_TIME = 33;

	public static final int SIZE = 50;

	public static final int DX = 2;

	public static final double FRICTION = -0.9;

	private double vx;

	private double vy;
	private   static final double BALL_SIZE = 10.0;

	private GRect paddle;

	private GRect paddley;

	private GOval ball;

	private GRect BRICK_NUM;



	private GObject getCollidingObject() {
		if(getElementAt(ball.getX(), ball.getY()) != null) {
			return getElementAt(ball.getX(), ball.getY());

		}else if(getElementAt(ball.getX() + BALL_RADIUS, ball.getY())!=null) {
			return getElementAt(ball.getX() + BALL_RADIUS, ball.getY());

		}else if(getElementAt(ball.getX(), ball.getY()+BALL_RADIUS)!=null) {
			return getElementAt(ball.getX(), ball.getY()+BALL_RADIUS);

		}else if(getElementAt(ball.getX() + BALL_RADIUS, ball.getY()+ BALL_RADIUS)!=null) {
			return getElementAt(ball.getX() + BALL_RADIUS, ball.getY()+ BALL_RADIUS);

		}else {
			return null;
		}

	}


	@SuppressWarnings("unused")
	public void run() {
		/* You fill this in, along with any subsidiary methods */

		setBackground(Color.black);
		RandomGenerator rgen = RandomGenerator.getInstance();

		//paddle x
		paddle = new GRect(0, getHeight() - PADDLE_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.WHITE);
		paddle.setFillColor(Color.BLACK);
		add(paddle);

		//paddle y
		/*paddley= new GRect(getWidth() - PADDLE_OFFSET, 0, PADDLE_HEIGHT, PADDLE_WIDTH);
		paddley.setFilled(true);
		paddley.setColor(Color.WHITE);
		paddley.setFillColor(Color.BLACK);
		add(paddley);*/

		//ball
		ball = new GOval (getWidth()/2 - BALL_SIZE/2, getHeight()/2 - BALL_SIZE, BALL_SIZE, BALL_SIZE);               /* Centers the ball */
		ball.setFilled(true);
		ball.setColor(Color.GRAY);
		ball.setFillColor(Color.BLACK);
		add(ball);

		//bricks
		for(int i=0;i<NBRICKS_PER_ROW;i++) {
			for(int j=0;j<NBRICK_ROWS;j++) {
				BRICK_NUM=new GRect(j*(BRICK_SEP) + j*(BRICK_WIDTH), (BRICK_Y_OFFSET)+i*(BRICK_SEP), BRICK_WIDTH, BRICK_HEIGHT);
				BRICK_NUM.setFilled(false);
				add(BRICK_NUM);

				if(i<=1) {
					BRICK_NUM.setColor(Color.RED);
				}

				if(i>1 && i<=3) {
					BRICK_NUM.setColor(Color.ORANGE);
				}

				if(i>3 && i<=5) {
					BRICK_NUM.setColor(Color.YELLOW);
				}

				if(i>5 && i<=7) {
					BRICK_NUM.setColor(Color.GREEN);
				}

				if(i>7 && i<=9) {
					BRICK_NUM.setColor(Color.CYAN);
				}
			}
		}




		vy+=8;
		vx=rgen.nextDouble(1.0,5.0);
		if(rgen.nextBoolean(0.5)) vx=-vx;

		ball.move(vx, vy);

		addMouseListeners();

		//wall collision
		//lHB = last hit brick
		boolean lHB = false;
		while(true) {
			GObject collider = getCollidingObject();
			ball.move(vx, vy);
			if(collider == paddle&& vy>0) {
				vy=-vy;
				ball.setFillColor(rgen.nextColor());
				lHB=false;
			} else if(collider!=paddle&&collider!=null && lHB != true) {
				vy=-vy;
				ball.setFillColor(rgen.nextColor());
				lHB=true;
				remove(collider);
			} else if(ball.getX()<=0) {
				vx=-vx;
				ball.setFillColor(rgen.nextColor());
				lHB=false;
			} else if(ball.getX()>=getWidth()- BALL_SIZE) {
				vx=-vx;
				ball.setFillColor(rgen.nextColor());
				lHB=false;
			} else if(ball.getY()<=0) {
				vy=-vy;
				ball.setFillColor(rgen.nextColor());
				lHB=false;
			} else if(ball.getY()>=getHeight() - BALL_SIZE) {
				lHB=false;

				waitForClick();{
					ball.setLocation(getWidth()/2, getHeight()/2);
				}
				while(lHB=false) {
					if(NBRICKS_PER_ROW>1) {
						println("GAME OVER - YOU WIN");	
					}else {
						int brickCount=100;
						brickCount=100-1;
						if(100==0) {
							println("GAME OVER - YOU WIN");
						}
					}
				}

			}


			//paddle collision
			/* if(getElementAt(ball.getX(), ball.getY()) != null) {
			vx=-vx;

		}else if(getElementAt(ball.getX() + 2*BALL_RADIUS, ball.getY())!=null) {
			vx=-vx;

		}else if(getElementAt(ball.getX(), ball.getY()+2 * BALL_RADIUS)!=null) {
			vy=-vy;

		}else if(getElementAt(ball.getX() + 2*BALL_RADIUS, ball.getY()+2*BALL_RADIUS)!=null) {
			vy=-vy;
			 */
			pause(PAUSE_TIME);
		}
	}

	//mouse movement
	@Override public void mouseMoved(MouseEvent e) {

		double x = e.getX() - paddle.getWidth();
		double y = getHeight() - PADDLE_OFFSET;


		if(x>0 && x < getWidth() - paddle.getWidth()) {
			paddle.setLocation(x, y);
		}}
	/*@Override public void mouseMovedY(MouseEvent e) {
		double xx = getHeight()- PADDLE_OFFSET;
		double yy = e.getY() - paddle.getHeight();


		if(yy>0 && yy < getHeight() - paddle.getHeight()) {
			paddle.setLocation(xx, yy);
		}*/
	}
	

