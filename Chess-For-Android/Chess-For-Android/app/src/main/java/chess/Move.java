package chess;

/**
 * Object used in the Recorded game class
 * that holds two coordinates, making a move
 * Created by huntermorabito on 12/9/16.
 */

public class Move implements java.io.Serializable{

    private Coordinate start;
    private Coordinate end;

    public Move(Coordinate startIn, Coordinate endIn){
        this.start = startIn;
        this.end = endIn;
    }


    public Coordinate getStart() {
        return start;
    }

    public void setStart(Coordinate start) {
        this.start = start;
    }

    public Coordinate getEnd() {
        return end;
    }

    public void setEnd(Coordinate end) {
        this.end = end;
    }

    public String toString(){
        return start.toString() +", "+end.toString();
    }
}
