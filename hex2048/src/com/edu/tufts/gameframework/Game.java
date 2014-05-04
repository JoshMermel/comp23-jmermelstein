package com.edu.tufts.gameframework;

import com.edu.tufts.gameframework.Gameboard;
//import android.util.Log;


import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.view.MotionEvent;

/**
 * Actual game.
 * 
 * @author www.gametutorial.net
 */

public class Game {
	
	public static int screenWidth;
	public static int screenHeight;
	public static float screenDensity;
	
	private static final int SWIPE_MIN_DISTANCE = screenWidth/20;
	
	private Gameboard gameboard;
	private float downX, downY, upX, upY;
	private boolean isClassic, gameover;
	
	public Game(int screenWidth, int screenHeight, Resources resources, Boolean isClassic){
		Game.screenWidth = screenWidth;
		Game.screenHeight = screenHeight;
		Game.screenDensity = resources.getDisplayMetrics().density;
		
		this.LoadContent(resources);
		this.isClassic = isClassic;
		
		if (gameboard == null){
			gameboard = new Gameboard(screenWidth, screenHeight, screenWidth/12, isClassic);
		}
		
		this.ResetGame();
	}
	

	/**
	 * Load files.
	 */
	private void LoadContent(Resources resources){

	}

	
	/**
	 * For (re)setting some game variables before game can start.
	 */
	private void ResetGame(){
		gameboard.reset();
		gameboard.addRandomTile(isClassic);
    	gameboard.addRandomTile(isClassic);
	}
	
	
	/**
	 * Game update method.
	 * 
	 * @param gameTime Elapsed game time in milliseconds.
	 */
	public void Update(long gameTime) {
		gameboard.update();

	}
	
	
	/**
	 * Draw the game to the screen.
	 * 
	 * @param canvas Canvas on which we will draw.
	 */
	public void Draw(Canvas canvas) {
		Paint paint = new Paint();
        paint.setColor(Color.parseColor("#BBADA0"));
		canvas.drawPaint(paint);
		gameboard.Draw(canvas);
		Paint scorepaint = new Paint();
		scorepaint.setColor(Color.BLACK); 
		scorepaint.setTextSize(40); 
		
        scorepaint.setTextAlign(Align.CENTER);
		canvas.drawText("Score: " + gameboard.score, screenWidth/2, screenHeight/4, scorepaint);
		if(gameover){
			canvas.drawText("Game over!", screenWidth/2, screenHeight/8, scorepaint);
		} else if(gameboard.victory){
   	 		canvas.drawText("Hooray, you win!", screenWidth/2, screenHeight/8, scorepaint);
   	 	}
	}
	
	
    /**
     * When touch on screen is detected.
     * 
     * @param event MotionEvent
     */
    public void touchEvent_actionDown(MotionEvent event){
    	downX = event.getX();
        downY = event.getY();
    }
    
    /**
     * When moving on screen is detected.
     * 
     * @param event MotionEvent
     */
    public void touchEvent_actionMove(MotionEvent event){
    	
    }
    
    /**
     * When touch on screen is released.
     * 
     * @param event MotionEvent
     */
    public void touchEvent_actionUp(MotionEvent event){
    	upX = event.getX();
        upY = event.getY();

        float delX = downX - upX;
        float delY = downY - upY;
        
        // check the swipe was long enough to count
        if ((delX * delX) + (delY * delY) > SWIPE_MIN_DISTANCE * SWIPE_MIN_DISTANCE ){ 
        	if(Math.abs(delY/delX) < 0.57 && delX > 0){
        		gameboard.slideLeft();
        		if(gameboard.moved){
        			gameboard.addRandomTile(isClassic);
        			gameboard.moved = false;
        			gameover = gameboard.noMoves();
        		}
        	} else if(Math.abs(delY/delX) < 0.57 && delX < 0){
        		gameboard.slideRight();
        		if(gameboard.moved){
        			gameboard.addRandomTile(isClassic);
        			gameboard.moved = false;
        			gameover = gameboard.noMoves();
        		}
        	} else if(delY/delX < 11.4 && delY/delX > 0.7 && delX < 0){
        		gameboard.slideDR();
        		if(gameboard.moved){
        			gameboard.addRandomTile(isClassic);
        			gameboard.moved = false;
        			gameover = gameboard.noMoves();
        		}
        	} else if(delY/delX < 11.4 && delY/delX > 0.7 && delX > 0){
        		gameboard.slideUL();
        		if(gameboard.moved){
        			gameboard.addRandomTile(isClassic);
        			gameboard.moved = false;
        			gameover = gameboard.noMoves();
        		}
        	}else if(delY/delX < -0.7 && delY/delX > -11.4 && delX > 0){
        		gameboard.slideDL();
        		if(gameboard.moved){
        			gameboard.addRandomTile(isClassic);
        			gameboard.moved = false;
        			gameover = gameboard.noMoves();
        		}
        	}else if(delY/delX < -0.7 && delY/delX > -11.4 && delX < 0){
        		gameboard.slideUR();
        		if(gameboard.moved){
        			gameboard.addRandomTile(isClassic);
        			gameboard.moved = false;
        			gameover = gameboard.noMoves();
        		}
        	}
        }
    }
    
}
