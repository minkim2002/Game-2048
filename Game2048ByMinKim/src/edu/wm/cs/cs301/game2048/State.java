package edu.wm.cs.cs301.game2048;

import java.awt.image.TileObserver;
import java.util.Arrays;
import java.util.Random;

import com.sun.source.tree.WhileLoopTree;

public class State implements GameState {
	
	//field
	int [][] tile = new int [4][4]; 
	
	//constructor with a parem
	public State(GameState original) {
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++){
				tile[i][j] = original.getValue(i, j);
			}
		}
	}
	
	//constructor with no parem
	public State() {
		setEmptyBoard();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(tile);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		return Arrays.deepEquals(tile, other.tile);
	}

	@Override
	public int getValue(int xCoordinate, int yCoordinate) {
		// TODO Auto-generated method stub
		return tile[yCoordinate][xCoordinate];
	}

	@Override
	public void setValue(int xCoordinate, int yCoordinate, int value) {
		// TODO Auto-generated method stub
		tile[yCoordinate][xCoordinate]=value;
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
		if(!isFull() && canMerge()) {
			Random rand = new Random();
			// Generate random integers in range 0 to 3 for finding a grid
			int rand_int1=0;
			int rand_int2=0;
			do{
				//repeat until it finds an empty grid
				rand_int1 = rand.nextInt(4);
				rand_int2 = rand.nextInt(4);
			} while (tile[rand_int1][rand_int2]!=0);
	        
	        // Generate random integers in range 0 to 1 for picking either 2 or 4
	        int rand_int3 = rand.nextInt(2);
	        if(rand_int3==0) {
	        	//if 0, new tile will be 2;
	        	tile[rand_int1][rand_int2] = 2;
	        	return true;
	        }else{
	        	//if 1, new tile will be 4;
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
				if(this.getValue(i, j) == 0) {
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
				//checking left
				if(i!=0 && (this.getValue(i-1, j)==this.getValue(i, j))){
					return true;
				}
				//checking right
				if(i!=3 && (this.getValue(i+1, j)==this.getValue(i, j))){
					return true;
				}
				//checking up
				if(j!=0 && (this.getValue(i, j-1)==this.getValue(i, j))){
					return true;
				}
				//checking down
				if(j!=3 && (this.getValue(i, j+1)==this.getValue(i, j))){
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
				if(this.getValue(i, j)>= 2048) {
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
			for(int j=1; j<4; j++){
				if(this.getValue(j, i)!=0) {
					int move = j;
					for (int x = j-1; x>=0; x--) {
						if(tile[i][x] == 0) {
							move = x;
						}
					}
					if(move!=j) {
						int temp = tile[i][j];
						tile[i][move] = temp;
						tile[i][j]= 0; 
					}
				}
			}
			for (int m =1; m<4; m++) {
				if ((tile[i][m-1] == tile[i][m] && tile[i][m]!=0 )) {
					tile[i][m-1] *= 2;
					tile[i][m]=0;
					sum += tile[i][m-1];
				}
			}
			for (int e=1; e<4; e++) {
				if(tile[i][e]>0) {
					int move = e;
					for(int t = e-1; t>=0; t--) {
						if(tile[i][t]==0) {
							move=t;
						}
					}
					if(move !=e) {
						int temp = tile[i][e];
						tile[i][move] = temp;
						tile[i][e]=0;
					}
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
			for(int j=2; j>=0; j--){
				if(this.getValue(j, i)!=0) {
					int move = j;
					for (int x = j+1; x<=3; x++) {
						if(tile[i][x] == 0) {
							move = x;
						}
					}
					if(move!=j) {
						int temp = tile[i][j];
						tile[i][move] = temp;
						tile[i][j]= 0; 
					}
				}
			}
			for (int m =2; m>=0; m--) {
				if ((tile[i][m+1] == tile[i][m] && tile[i][m]!=0 )) {
					tile[i][m+1] *= 2;
					tile[i][m]=0;
					sum += tile[i][m+1];
				}
			}
			for (int e=2; e>=0; e--) {
				if(tile[i][e]>0) {
					int move = e;
					for(int t = e+1; t<=3; t++) {
						if(tile[i][t]==0) {
							move=t;
						}
					}
					if(move !=e) {
						int temp = tile[i][e];
						tile[i][move] = temp;
						tile[i][e]=0;
					}
				}
			}
		}
					
		return sum;
		
	}

	@Override
	public int down() {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int j=0; j<4; j++) {
			for(int i=2; i>=0; i--){
				if(this.getValue(j, i)!=0) {
					int move = i;
					for (int x = i+1; x<=3; x++) {
						if(tile[x][j] == 0) {
							move = x;
						}
					}
					if(move!=i) {
						int temp = tile[i][j];
						tile[move][j] = temp;
						tile[i][j]= 0; 
					}
				}
			}
			for (int m =2; m>=0; m--) {
				if ((tile[m+1][j] == tile[m][j] && tile[m][j]!=0 )) {
					tile[m+1][j] *= 2;
					tile[m][j]=0;
					sum += tile[m+1][j];
				}
			}
			for (int e=2; e>=0; e--) {
				if(tile[e][j]>0) {
					int move = e;
					for(int t = e+1; t<=3; t++) {
						if(tile[t][j]==0) {
							move=t;
						}
					}
					if(move !=e) {
						int temp = tile[e][j];
						tile[move][j] = temp;
						tile[e][j]=0;
					}
				}
			}
		}
					
		return sum;
		
	}

	@Override
	public int up() {
		// TODO Auto-generated method stub
		
		int sum = 0;
		for(int j=0; j<4; j++) {
			for(int i=1; i<4; i++){
				if(this.getValue(j, i)!=0) {
					int move = i;
					for (int x = i-1; x>=0; x--) {
						if(tile[x][j] == 0) {
							move = x;
						}
					}
					if(move!=i) {
						int temp = tile[i][j];
						tile[move][j] = temp;
						tile[i][j]= 0; 
					}
				}
			}
			for (int m =1; m<4; m++) {
				if ((tile[m-1][j] == tile[m][j] && tile[m][j]!=0 )) {
					tile[m-1][j] *= 2;
					tile[m][j]=0;
					sum += tile[m-1][j];
				}
			}
			for (int e=1; e<4; e++) {
				if(tile[e][j]>0) {
					int move = e;
					for(int t = e-1; t>=0; t--) {
						if(tile[t][j]==0) {
							move=t;
						}
					}
					if(move !=e) {
						int temp = tile[e][j];
						tile[move][j] = temp;
						tile[e][j]=0;
					}
				}
			}
		}
					
		return sum;
		
	}

}
