package mancala;

import java.util.ArrayList;

public class MancalaGame {
    
    private Board board;
    private Player currentPlayer;
    private ArrayList<Player> players;

    public MancalaGame() {
        this.board = new Board();
        this.players = new ArrayList<>();
        
        this.currentPlayer = null;

    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getNumStones(int pitNum) throws PitNotFoundException {
        try {
            return board.getNumStones(pitNum); 
        } catch (PitNotFoundException e) {
            throw new PitNotFoundException("error");
        }
    
    }
    
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getStoreCount(Player player) throws NoSuchPlayerException {
        if (player == null) {
            throw new NoSuchPlayerException();
        }
        int storeCount = 0; 
        for (Store store : board.getStores()) {
            if (store.getOwner() == player) {
                storeCount += store.getTotalStones();
            }
        }
        return storeCount;
    }

    public Player getWinner() throws GameNotOverException {
        
        if (!isGameOver()) {
            throw new GameNotOverException();
        }
        int playerOneStoreCount = 0;
        int playerTwoStoreCount = 0;
        for (Store store : this.board.getStores()) {
            if (store.getOwner() == players.get(0)) {
                playerOneStoreCount += store.getTotalStones();
            } else if (store.getOwner() == players.get(1)) {
                playerTwoStoreCount += store.getTotalStones();
            }
        }
        if (playerOneStoreCount > playerTwoStoreCount) {
            return players.get(0);
        } else if (playerTwoStoreCount > playerOneStoreCount) {
            return players.get(1);
        } else {
            return null; // tie outcome
        }
    }

    public boolean isGameOver() {
        boolean player1SideEmpty = false;
        boolean player2SideEmpty = false;
        try {
            player1SideEmpty = this.board.isSideEmpty(5);
            player2SideEmpty = this.board.isSideEmpty(11);
        } catch (PitNotFoundException e) {
            System.out.println("");
        }
        return player1SideEmpty || player2SideEmpty;
    }

    public int move(int startPit) throws InvalidMoveException {

        int totalStones = board.moveStones(startPit, currentPlayer);
        int totalStonesLeft = 0;

        for (Pit pit: board.getPits()) {
            totalStonesLeft += pit.getStoneCount();
        }
        return totalStonesLeft;
    }

    public void setBoard(Board theBoard) {
        this.board = theBoard;
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player; 
    }

    public void setPlayers(Player onePlayer, Player twoPlayer) {

        players.add(onePlayer);
        players.add(twoPlayer);
        currentPlayer = onePlayer;
        board.registerPlayers(onePlayer, twoPlayer);
    }

    public void startNewGame() {
        board.initializeBoard();

    }

    public String toString() {
        return "Mancala Game{" + "board =" + board + ", players =" + players 
        + ", currentPlayer =" + currentPlayer + '}';
    }
}