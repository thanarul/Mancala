package mancala;

public class Player {

    private String playerName; 
    private Store playerStore;

    public Player() {
        this.playerName = "Player";
        this.playerStore = new Store();
    }
    
    public Player(String name) {
        this.playerName = name;
        this.playerStore = new Store();
    }

    public String getName() {
        return this.playerName;
    }

    public Store getStore() {
        return this.playerStore;
    }

    public int getStoreCount() {
        return playerStore.getTotalStones();
    }

    public void setName(String name) {
        this.playerName = name;
    }
    public void setStore(Store store) {
        this.playerStore = store; 
    }

    public String toString() {
        return "Player:{ name = " + playerName + "storeCount = " 
        + getStoreCount() + " + playerStore " + playerStore + '}';
    }
}