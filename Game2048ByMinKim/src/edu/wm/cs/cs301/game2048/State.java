package edu.wm.cs.cs301.game2048;

import java.awt.image.TileObserver;
import java.util.Random;

import com.sun.source.tree.WhileLoopTree;

public class State implements GameState {
	
	int [][] tile = new int [4][4];

	public State(GameState original) {
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++){
				tile[i][j] = original.getValue(i, j);
			}
		}
	}

	public State() {
		setEmptyBoard();
	}

	@Override
	public int getValue(int xCoordinate, int yCoordinate) {
		// TODO Auto-generated method stub
		return tile[xCoordinate][yCoordinate];
	}

	@Override
	public void setValue(int xCoordinate, int yCoordinate, int value) {
		// TODO Auto-generated method stub
		tile[xCoordinate][yCoordinate]=value;
	}

	@Override
	public void setEmptyBoard() {
		// TODO Auto-generated method stub
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++){
				tile[i][j] = 0;
			}
		}

	}

	@Override
	public boolean addTile() {
		// TODO Auto-generated method stub
		if(!isFull()) {
			Random rand = new Random();
			// Generate random integers in range 0 to 3
			int rand_int1=0;
			int rand_int2=0;
			do{
				rand_int1 = rand.nextInt(4);
				rand_int2 = rand.nextInt(4);
			} while (tile[rand_int1][rand_int2]!=0);
	        
	        // Generate random integers in range 0 to 1
	        int rand_int3 = rand.nextInt(2);
	        if(rand_int3==0) {
	        	tile[rand_int1][rand_int2] = 2;
	        	return true;
	        }else{
	        	tile[rand_int1][rand_int2] = 2;
	        	return true;
	        }
		}else{return false;}
	}

	@Override
	public boolean isFull() {
		// TODO Auto-generated method stub
		boolean isEmpty = true;
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++){
				if(tile[i][j] == 0) {
					return !isEmpty;
				}
			}
		}
		return isEmpty;
	}

	@Override
	public boolean canMerge() {
		// TODO Auto-generated method stub
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++){
				if(i!=0 && tile[i-1][j] == tile[i][j]){
					return true;
				}
				if(i!=3 && tile[i+1][j] == tile[i][j]){
					return true;
				}
				if(j!=0 && tile[i][j-1] == tile[i][j]){
					return true;
				}
				if(j!=3 && tile[i][j+1] == tile[i][j]){
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean reachedThreshold() {
		// TODO Auto-generated method stub
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++){
				if(tile[i][j] >= 2048) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int left() {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i=0; i<4; i++) {
			for(int j=0; j<3; j++){
				if(getValue(i, j)!=0) {
					if(getValue(i, j) == getValue(i, j+1)){
						sum += getValue(i, j)+getValue(i, j+1);
						tile[i][j] += tile[i][j+1];
						tile[i][j+1]=0;
					}
				}else {
					int x = getValue(i, j+1);
					tile[i][j+1]= getValue(i, j);
					tile[i][j]=x;
				}
			}
		}
		return sum;
	}

	@Override
	public int right() {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i=0; i<4; i++) {
			for(int j=4; j>1; j--){
				if(tile[i][j] == tile[i][j-1]){
					sum += tile[i][j]+tile[i][j-1];
					tile[i][j] = tile[i][j]+tile[i][j-1];
					tile[i][0] = 0;
				}
			}
		}
		return sum;
	}

	@Override
	public int down() {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i=4; i>1; i--) {
			for(int j=0; j<4; j++){
				if(tile[i][j] == tile[i-1][j]){
					sum += tile[i][j]+tile[i-1][j];
					tile[i][j] = tile[i][j]+tile[i-1][j];
					tile[0][j] = 0;
				}
			}
		}
		return sum;
	}

	@Override
	public int up() {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i=0; i<3; i++) {
			for(int j=0; j<4; j++){
				if(tile[i][j] == tile[i+1][j]){
					sum += tile[i][j]+tile[i+1][j];
					tile[i][j] = tile[i][j]+tile[i+1][j];
					tile[3][j] = 0;
				}
			}
		}
		return sum;
	}

}
