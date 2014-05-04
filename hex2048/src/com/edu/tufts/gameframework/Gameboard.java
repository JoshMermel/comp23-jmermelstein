package com.edu.tufts.gameframework;

import java.util.Arrays;
import java.util.Random;

import android.graphics.Canvas;

public class Gameboard {
	private Cell[][] board;
	public int score;
	public Boolean moved;
	public Boolean victory;
	private Boolean isClassic;
	public Cell[] r0, r1, r2, r3, r4;
	public Cell[] ul0, ul1, ul2, ul3, ul4;
	public Cell[] dr0, dr1, dr2, dr3, dr4;
	public Cell[] ur0, ur1, ur2, ur3, ur4;
	public Cell[] dl0, dl1, dl2, dl3, dl4;
	public Cell[] n01, n10, n11, n12, n13, n21, n23, n30, n31, n32, n33, n41;
	public Cell[][] neighborhoods;


	
	// width and height are the screen width and height it is begin drawn to.
	public Gameboard(int width, int height, int _radius, Boolean _isClassic) {
		score = 0;
		moved = false;
		this.isClassic = _isClassic;
		board = new Cell[5][];

		board[0] = new Cell[3];
		for (int i = 0; i < 3; i++) {
			board[0][i] = new Cell((2 * i + 3) * (width / 10), (height / 2)
					- (width / 3) + (height / 8), width / 12, this);
		}

		// initialize row 2
		board[1] = new Cell[4];
		for (int i = 0; i < 4; i++) {
			board[1][i] = new Cell((2 * i + 2) * (width / 10), (height / 2)
					- (width / 6) + (height / 8), width / 12, this);
		}

		// initialize row 3
		board[2] = new Cell[5];
		for (int i = 0; i < 5; i++) {
			board[2][i] = new Cell((2 * i + 1) * (width / 10), (height / 2)
					+ (height / 8), width / 12, this);
		}

		// initialize row 4
		board[3] = new Cell[4];
		for (int i = 0; i < 4; i++) {
			board[3][i] = new Cell((2 * i + 2) * (width / 10), (height / 2)
					+ (width / 6) + (height / 8), width / 12, this);
		}

		// initialize row 5
		board[4] = new Cell[3];
		for (int i = 0; i < 3; i++) {
			board[4][i] = new Cell((2 * i + 3) * (width / 10), (height / 2)
					+ (width / 3) + (height / 8), width / 12, this);
		}

		// now group them for sliding.
		// right
		r0 = new Cell[] { board[0][2], board[0][1], board[0][0] };
		r1 = new Cell[] { board[1][3], board[1][2], board[1][1], board[1][0] };
		r2 = new Cell[] { board[2][4], board[2][3], board[2][2], board[2][1],
				board[2][0] };
		r3 = new Cell[] { board[3][3], board[3][2], board[3][1], board[3][0] };
		r4 = new Cell[] { board[4][2], board[4][1], board[4][0] };

		// up left
		ul0 = new Cell[] { board[2][0], board[3][0], board[4][0] };
		ul1 = new Cell[] { board[1][0], board[2][1], board[3][1], board[4][1] };
		ul2 = new Cell[] { board[0][0], board[1][1], board[2][2], board[3][2],
				board[4][2] };
		ul3 = new Cell[] { board[0][1], board[1][2], board[2][3], board[3][3] };
		ul4 = new Cell[] { board[0][2], board[1][3], board[2][4] };

		// down right
		dr0 = new Cell[] { board[4][0], board[3][0], board[2][0] };
		dr1 = new Cell[] { board[4][1], board[3][1], board[2][1], board[1][0] };
		dr2 = new Cell[] { board[4][2], board[3][2], board[2][2], board[1][1],
				board[0][0] };
		dr3 = new Cell[] { board[3][3], board[2][3], board[1][2], board[0][1] };
		dr4 = new Cell[] { board[2][4], board[1][3], board[0][2] };

		// up right
		ur0 = new Cell[] { board[0][0], board[1][0], board[2][0] };
		ur1 = new Cell[] { board[0][1], board[1][1], board[2][1], board[3][0] };
		ur2 = new Cell[] { board[0][2], board[1][2], board[2][2], board[3][1],
				board[4][0] };
		ur3 = new Cell[] { board[1][3], board[2][3], board[3][2], board[4][1] };
		ur4 = new Cell[] { board[2][4], board[3][3], board[4][2] };

		// down left
		dl0 = new Cell[] { board[2][0], board[1][0], board[0][0] };
		dl1 = new Cell[] { board[3][0], board[2][1], board[1][1], board[0][1] };
		dl2 = new Cell[] { board[4][0], board[3][1], board[2][2], board[1][2],
				board[0][2] };
		dl3 = new Cell[] { board[4][1], board[3][2], board[2][3], board[1][3] };
		dl4 = new Cell[] { board[4][2], board[3][3], board[2][4] };

		// neighborhoods (used to check endgame)
		n01 = new Cell[] { board[0][1], board[0][0], board[1][1], board[1][2], board[0][2] };
		n10 = new Cell[] { board[1][0], board[0][0], board[1][1], board[2][1], board[2][0] };
		n11 = new Cell[] { board[1][1], board[0][0], board[1][2], board[2][2], board[2][1] };
		n12 = new Cell[] { board[1][2], board[0][2], board[1][3], board[2][3], board[2][2] };
		n13 = new Cell[] { board[1][3], board[0][2], board[2][3], board[2][4] };
		n21 = new Cell[] { board[2][1], board[2][0], board[3][0], board[3][1], board[2][2] };
		n23 = new Cell[] { board[2][3], board[2][2], board[3][2], board[3][3], board[2][4] };
		n30 = new Cell[] { board[3][0], board[2][0], board[3][1], board[4][0] };
		n31 = new Cell[] { board[3][1], board[2][2], board[3][2], board[4][1], board[4][0] };
		n32 = new Cell[] { board[3][2], board[2][2], board[3][3], board[4][2], board[4][1] };
		n33 = new Cell[] { board[3][3], board[2][4], board[4][2] };
		n41 = new Cell[] { board[4][1], board[4][0], board[4][2] };
		
		neighborhoods = new Cell[][] {n01, n10, n11, n12, n13, n21, n23, n30, n31, n32, n33, n41};
	}

	 public void reset(){
		 for(int i = 0; i < 5; i++){
	         	for(int j = 0; j < board[i].length ; j++){
	        		board[i][j].contents.clear();
	        	}
		 }
	 }
	 
     public int numEmpty(){
    	 int ret = 0;
    	 for(int i = 0; i < 5; i++){
         	for(int j = 0; j < board[i].length ; j++){
        		if(board[i][j].contents.size() == 0){
        			ret++;
        		}
        	}
    	 }
    	 return ret;
     }
	 
     public void addRandomTile(Boolean isClassic){
    	 if(numEmpty() != 0){
	    	 Random rn = new Random();
	    	 int randindex = rn.nextInt(numEmpty());
	    	 int randval = rn.nextInt(10);
	    	 int newindex = 0;
	    	 if(randval == 9){
	    		 if(isClassic){
	    			 randval = 4;
	    			 newindex = 2;
	    		 } else {
	    			 randval = 2;
	    			 newindex = 2;
	    		 }
	    	 } else {
	    		 if(isClassic){
	    			 randval = 2;
	    			 newindex = 1;
	    		 } else {
	    			 randval = 1;
	    			 newindex = 1;
	    		 }
	    	 }
	    	 for(int i = 0; i < 5; i++){
	          	for(int j = 0; j < board[i].length ; j++){
	         		if(randindex == 0 && board[i][j].contents.size() == 0){
	         			board[i][j].contents.add(new Tile(randval, board[i][j].x, board[i][j].y, board[i][j].radius, newindex, isClassic));
	         			randindex--;
	         		} else if (board[i][j].contents.size() == 0){
	         			randindex--;
	         		}
	         	}
	     	 }
    	 }
     }
     
     // slides a row to the left filling in empty spaces but not combining.
     // return -1 when no combining could happen
     // otherwise returns the score to be added.
     public void slideRow(Cell[] row){
    	 int rowlen = row.length;

    	 // find the lowest empty index
    	 int emptyindex = -1;
    	 for(int i = 0; i < rowlen; i++){
    		 if(row[i].contents.size() == 0){
    			 emptyindex = i;
    			 break;
    		 }
    	 }
    	 
    	 if(emptyindex < 0){
        	 combineRow(row);
    		 return;
    	 }
    	 
    	 // slide the nonempty things into empty slots.
    	 for(int i = 0; i < rowlen; i++){
    		 if(row[i].contents.size() > 0 && i > emptyindex){
    			 row[emptyindex].contents.add(row[i].contents.get(0));  // seg fault at this line?
    			 row[emptyindex].contents.get(0).set_goal(row[emptyindex].x,row[emptyindex].y);
    			 row[i].contents.clear();
    			 emptyindex++;
    			 moved = true;
    		 }
    	 }
    	 combineRow(row);

     }
     
     public Boolean canCombine(Tile a, Tile b){
    	 if(isClassic){
    		 return canCombineClassic(a,b);
    	 } else {
    		 return canCombineFib(a,b);
    	 }
     }
     
     public Boolean canCombineClassic(Tile a, Tile b){
    	 return a.val == b.val;
     }
     
     public Boolean canCombineFib(Tile a, Tile b){
    	 	return Math.abs(a.index - b.index) == 1 || (a.val == 1 && b.val == 1);
     }
     
     public void combineRow(Cell[] row){
    	 // check that there is something to combine

    	 int rowlen = row.length;
    	 if(rowlen <= 1){
    		 return;
    	 }
    	 
    	 if(row[0].contents.size() + row[1].contents.size() >= 2){
    		 //if(row[0].contents.get(0).val == row[1].contents.get(0).val){
    		 if(canCombine(row[0].contents.get(0), row[1].contents.get(0))){
    			 moved = true;
    			 row[1].contents.get(0).set_goal(row[0].x, row[0].y);
    			 row[0].contents.add(row[1].contents.get(0));
    			 row[1].contents.clear();
    			 for(int i = 2; i < rowlen; i++){
    				 if(row[i].contents.size() > 0){
    					 row[i-1].contents.add(row[i].contents.get(0));
    					 row[i-1].contents.get(0).set_goal(row[i-1].x, row[i-1].y);
    					 row[i].contents.clear();
    				 }
    			 }
    		 }
    	 }
    	 combineRow(Arrays.copyOfRange(row, 1, row.length));
     }
     
     public void slideLeft(){
    	 for(int i = 0; i < 5; i++){
    		 slideRow(board[i]);
    	 }
     }
     
     public void slideRight(){
    	 slideRow(r0);
    	 slideRow(r1);
    	 slideRow(r2);  
    	 slideRow(r3);
    	 slideRow(r4);
     }
     
     public void slideUL(){
    	 slideRow(ul0);
    	 slideRow(ul1);
    	 slideRow(ul2);  
    	 slideRow(ul3);
    	 slideRow(ul4);
     }
     
     public void slideDR(){
    	 slideRow(dr0);
    	 slideRow(dr1);
    	 slideRow(dr2);  
    	 slideRow(dr3);
    	 slideRow(dr4);
     }
     
     public void slideUR(){
    	 slideRow(ur0);
    	 slideRow(ur1);
    	 slideRow(ur2);  
    	 slideRow(ur3);
    	 slideRow(ur4);
     }
     
     public void slideDL(){
    	 slideRow(dl0);
    	 slideRow(dl1);
    	 slideRow(dl2);  
    	 slideRow(dl3);
    	 slideRow(dl4);
     }
     
     public Boolean noMoves(){
    	 for(int i = 0; i < neighborhoods.length; i++){
    		 // if the center of the neighborhood is empty, return that there are legal moves
			 if(neighborhoods[i][0].contents.size() == 0){
				 return false;
			 } 
    		 for(int j = 1; j < neighborhoods[i].length; j++){
    			 // if any other cell is empty
    			 if(neighborhoods[i][j].contents.size() == 0){
    				 return false;
    			 // or it can combine with the center
    			 } else if (canCombine(neighborhoods[i][0].contents.get(0),neighborhoods[i][j].contents.get(0))) {
    				 return false;
    			 }
    		 }
    	 }
    	 return true;
     }
     
	 protected void update(){
    	 for(int i = 0; i < board.length; i++){
          	for(int j = 0; j < board[i].length ; j++){
          		board[i][j].update();
         	}
     	 }
	 }
	 
	 protected void Draw(Canvas canvas) {
    	 for(int i = 0; i < board.length; i++){
           	for(int j = 0; j < board[i].length ; j++){
           		board[i][j].Draw(canvas);
          	}
      	 }
    	 for(int i = 0; i < board.length; i++){
            	for(int j = 0; j < board[i].length ; j++){
            		board[i][j].TileDraw(canvas);
           	}
       	 }
	 }
}
