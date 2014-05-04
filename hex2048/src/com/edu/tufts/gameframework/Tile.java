package com.edu.tufts.gameframework;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

public class Tile {
	 public int val;
	 public float x;
	 public float goalx;
	 public float y;
	 public float goaly;
	 public int radius;
	 private int anim_counter;
	 private Paint paint;
	 private Paint textpaint;
	 public int index;
	 public Boolean isClassic;
	 
	 public Tile(int _val, float _x, float _y, int _radius, int _index, Boolean _isClassic){
		 this.val = _val;
		 this.x = _x;
		 this.y = _y;
		 this.goalx = (int)_x;
		 this.goaly = (int)_y;
		 this.radius = _radius;
		 this.anim_counter = 3;
		 this.index = _index;
		 this.isClassic = _isClassic;
		 paint = new Paint();
		 textpaint = new Paint();
       	 textpaint.setColor(Color.BLACK);
       	 
       	 if(isClassic){
			 switch (index) {
	         case 1:  paint.setColor(Color.parseColor("#EEE4DA"));
	                  break;
	         case 2:  paint.setColor(Color.parseColor("#EDE0C8"));
	                  break;
	         case 3:  paint.setColor(Color.parseColor("#F2B279"));
	                  break;
	         case 4: paint.setColor(Color.parseColor("#F59662"));
	                  break;
	         case 5: paint.setColor(Color.parseColor("#F67A5F"));
	                  break;
	// 		   bizarre bugs happen when this is uncommented and a 64 is created
	// 		   I blame ghosts.
	//         case 6: paint.setColor(Color.parseColor("00FF00"));
	//                  break;
	         case 7: paint.setColor(Color.parseColor("#EDCF72"));
	                  break;
	         case 8: paint.setColor(Color.parseColor("#EDCD61"));
	                  break;
	         case 9: paint.setColor(Color.parseColor("#EDC950"));
	                  break;
	         case 10: paint.setColor(Color.parseColor("#EDC53F"));
	                  break;
	         case 11: paint.setColor(Color.parseColor("#EDC12E"));
	                  break;
	         default: paint.setColor(Color.parseColor("#F65D3B"));
	         		  break;
			 }
       	 } else {
       		 switch(index){
		         case 1:  paint.setColor(Color.parseColor("#EEE4DA"));
		             break;
		         case 2:  paint.setColor(Color.parseColor("#EDE0C8"));
		             break;
		         case 3:  paint.setColor(Color.parseColor("#ECDAB3"));
		             break;
		         case 4: paint.setColor(Color.parseColor("#ECCFA8"));
		             break;
		         case 5: paint.setColor(Color.parseColor("#F2B279"));
		             break;
		         case 6: paint.setColor(Color.parseColor("#F4AC75"));
		         	break;
		         case 7: paint.setColor(Color.parseColor("#F5A370"));
		             break;
		         case 8: paint.setColor(Color.parseColor("#F59662"));
		             break;
		         case 9: paint.setColor(Color.parseColor("#F67A5F"));
		             break;
		         case 10: paint.setColor(Color.parseColor("#F65D3B"));
		             break;
		             
		         case 11: paint.setColor(Color.parseColor("#FA3B4A"));
		             break;
		         case 12: paint.setColor(Color.parseColor("#FD2F42"));
		             break;
		         //case 13: paint.setColor(Color.parseColor("#"));
		         //    break;
		         case 14: paint.setColor(Color.parseColor("#EDCF72"));
		             break;
		         case 15: paint.setColor(Color.parseColor("#EDCD61"));
		             break;
		         case 16: paint.setColor(Color.parseColor("#EDC950"));
		             break;
		         case 17: paint.setColor(Color.parseColor("#EDC53F"));
	             	break;
			     default: paint.setColor(Color.parseColor("#F67A5F"));
		     	 	break;

       		 }     		 
       	 }
	 }
	 
	 public void set_goal(float _x, float _y){
		 goalx = _x;
		 goaly = _y;	 
	 }
	 
	 public Boolean in_position(){
		 return x == goalx && y == goaly;
	 }
	 
	 protected void update(){
		 if(x == goalx && y == goaly){
			 anim_counter = 3; // reset the anim_counter
		 } else if (anim_counter == 0){  // something has gone wrong if this wasn't about to happen anyway
			 x = goalx;
			 y = goaly;
		 } else { // there is time left to animate
			 float delx = goalx - x;
			 float dely = goaly - y;
			 x += delx/anim_counter;
			 y += dely/anim_counter;
			 anim_counter--;
		 }

	 }
	 
	 protected void Draw(Canvas canvas) {

         canvas.drawCircle(x, y, radius, paint);

       	 textpaint.setTextSize(25);
         textpaint.setTextAlign(Align.CENTER);
       	 canvas.drawText(Integer.toString(val), x,y+10, textpaint);
	 }
}
