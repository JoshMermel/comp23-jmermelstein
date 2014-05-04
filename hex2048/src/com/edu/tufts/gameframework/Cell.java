package com.edu.tufts.gameframework;

import com.edu.tufts.gameframework.Tile;

import java.util.List;
import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Cell {
	public float x;
	public float y;
	public int radius;
	public Color color;
	List <Tile> contents;
	List <Tile> stragglers; // tiles animating but to be deleted once they are done being animated.
	Gameboard gameboard;
	
	 public Cell(float _x, float _y, int _radius, Gameboard _gameboard){
		 this.x = _x;
		 this.y = _y;
		 this.radius = _radius;
		 this.gameboard = _gameboard;
		 contents = new ArrayList<Tile>();
		 stragglers = new ArrayList<Tile>();
	 }
	 
	 protected void update(){
		 for(int i = 0; i < contents.size(); i++){
			 contents.get(i).update();
		 }


		 if(contents.size() > 1){
			 int newval = 0;
			 int newindex = 0;
			 // combine tiles
			 for(int i = 0; i < contents.size(); i++){
				 newval += contents.get(i).val;
				 if(contents.get(i).index > newindex){
					 newindex = contents.get(i).index;
				 }
			 }
			 newindex++;
			 Boolean isClassic = contents.get(0).isClassic;
			 
			 for(int i = 1; i < contents.size(); i++){
				 stragglers.add(contents.get(i));
			 }
			 contents.clear();
			 contents.add(new Tile(newval, x, y, radius, newindex, isClassic));
			 gameboard.score += newval;
			 
			 if(newval >= 2048){
				 gameboard.victory = true;
			 }
			 
			 
		 }
		 
		 // update and clean up stragglers.
		 for(int i = 0; i < stragglers.size(); i++){
			 stragglers.get(i).update();
			 if (stragglers.get(i).x == stragglers.get(i).goalx && stragglers.get(i).y == stragglers.get(i).goaly){
				 stragglers.remove(i);
			 }
		 }
	 }
	 
	 protected Boolean isEmpty(){
		 return contents.size() > 0;
	 }
	 
	 protected void Draw(Canvas canvas) {
		 Paint paint = new Paint();
		 paint.setColor(Color.parseColor("#CCC0B3"));
		 canvas.drawCircle(x, y, radius, paint);
	 }
	 
	 protected void TileDraw(Canvas canvas){
		 if(contents.size() > 0){
			 contents.get(0).Draw(canvas);
		 }
		 for(int i = 0; i < stragglers.size(); i++){
			 stragglers.get(i).Draw(canvas);
		 }
	 }
}
