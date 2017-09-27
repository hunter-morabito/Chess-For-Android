package chess;

import java.io.Serializable;

public class Coordinate implements Serializable{
	public int row = -1;
	public int col = -1;
	
	//constructor for reading in command
	public Coordinate(String coordinates){
		if(coordinates.length() == 2){
			this.col = getRealCol(coordinates.charAt(0));
			this.row = getRealRow(coordinates.charAt(1));
		}
	}
	
	//constructor for making new Coordinate
	public Coordinate(int row, int col){
			this.row = row;
			this.col = col;
	}
	
	public Coordinate() {}
	
	private int getRealCol(char col){
		return col - 97;
	}
	
	private int getRealRow(char row){
		return 8 - Character.getNumericValue(row); 
	}
	
	@Override
    public boolean equals(Object object)
    {
        if (object != null && object instanceof Coordinate)
        {
            if((this.row == ((Coordinate) object).row) && (this.col == ((Coordinate) object).col)){
            	return true;
            }
        }

        return false;
    }

	public String toString(){
		return row + ", " + col;
	}

}
