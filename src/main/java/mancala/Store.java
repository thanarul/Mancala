package mancala;

public class Store {
    private int totalStones;
    private Player owner; 

    public Store() {
        this.totalStones = 0;
        this.owner = null;
    } 

    public void addStones(int amount) {
        this.totalStones += amount;
    }

    public int emptyStore() {
        int stonesInStore= totalStones;
        totalStones = 0;
        return stonesInStore; 
    }

    public Player getOwner() {
        return this.owner;
    }

    public int getTotalStones() {
        return totalStones;
    }

    public void setOwner(Player player) {
        this.owner = player;
    }

    public String toString() {
        String ownerName; 
        if (owner != null) {
            ownerName = owner.getName();
        } else {
            ownerName = "no name";
        }
        return "Store " + "owner= " + ownerName + " totalStones, " + totalStones;
    }
}