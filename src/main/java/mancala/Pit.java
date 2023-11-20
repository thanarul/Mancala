package mancala;

public class Pit {
    private int stoneCount; 

    public Pit() {
        this.stoneCount = 0; 
    }

    public void addStone() {
        stoneCount++;
    }

    public int getStoneCount() {
        return stoneCount;
    }

    public int removeStones() {

        int stoneRemoved = stoneCount;
        stoneCount = 0;
        return stoneRemoved;

    }

    public String toString() {
        return String.valueOf(stoneCount);   
    }


}