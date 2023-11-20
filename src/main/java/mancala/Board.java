package mancala;

import java.util.ArrayList;

public class Board {
    // instance variables 
    private ArrayList<Pit> boardPits;
    private ArrayList<Store> boardStores;
    private Player currentPlayer;

    public Board() {
        this.boardPits = new ArrayList<>(12);
        this.boardStores = new ArrayList<>(2);
        setUpPits();
        setUpStores();
    }

    public int captureStones(int stoppingPoint) throws PitNotFoundException {

        if (stoppingPoint < 1 || stoppingPoint >= boardPits.size()) {
            throw new PitNotFoundException("");
        }
        int capturedStones = 0;
        int index = 11 - stoppingPoint;

        if (boardPits.get(stoppingPoint -1).getStoneCount() == 1 && boardPits.get(index -1).getStoneCount() > 0) {
            capturedStones = boardPits.get(stoppingPoint - 1).removeStones();
            capturedStones += boardPits.get(index - 1).removeStones();
        }
        return capturedStones;
    }

    public int distributeStones(int startingPoint) throws PitNotFoundException {

        if (startingPoint < 0 || startingPoint >= boardPits.size()) {
            throw new PitNotFoundException("");
        }
        int totalStonesAdded = 0;
        int stonesToDistribute = boardPits.get(startingPoint-1).removeStones();
        int currentIndex = startingPoint;

        while (stonesToDistribute > 0) {
            currentIndex = (currentIndex % boardPits.size());
            if (currentIndex != startingPoint - 1) {
                boardPits.get(currentIndex).addStone();
                stonesToDistribute--;
                totalStonesAdded++;
            }
            currentIndex++;           
        }
        totalStonesAdded += captureStones(currentIndex);
        return totalStonesAdded;
    }

    private boolean pitValid(int pitNum) {
        return pitNum >= 0 && pitNum <= 11;
    }
    public int getNumStones(int pitNum) throws PitNotFoundException {
        if (!pitValid(pitNum)) {
            throw new PitNotFoundException("Error: Invalid Pit Selected");
        }
        return boardPits.get(pitNum-1).getStoneCount();
    }
    
    // Accessors 

    public ArrayList<Pit> getPits() {
        return boardPits;
    }

    public ArrayList<Store> getStores() {
        return boardStores;
    }

    public void initializeBoard() {
        for (Pit pit: boardPits) {
            for (int i = 0; i < 4; i++) {
                pit.addStone();
            }
        }
    }

    public boolean isSideEmpty(int pitNum) throws PitNotFoundException {
        if (pitNum < 0 || pitNum > 12) {
            throw new PitNotFoundException("Invalid Pit Number: " + pitNum);
        }

        int startIndex;
        int endIndex;
        if (pitNum < 7) {
            startIndex = 1;
            endIndex = 6; 
        } else {
            startIndex = 7; 
            endIndex = boardPits.size();
        }


        for (int i = startIndex; i <= endIndex; i++) {
            if (boardPits.get(i-1).getStoneCount() > 0) {
                return false;
            }
        }
        return true;
    }

    public int moveStones(int startPit, Player player) throws InvalidMoveException {

        if (!pitValid(startPit) || boardPits.get(startPit-1).getStoneCount() == 0) {
            throw new InvalidMoveException();
        }

        int stonesToMove = boardPits.get(startPit - 1).removeStones();
        int currentPit = (startPit-1) % boardPits.size();
        boolean enteredStore = false;

        while (stonesToMove > 0) {
            currentPit = (currentPit + 1) % boardPits.size();
            System.out.println(currentPit);
            if (!enteredStore && (currentPit == 6 || currentPit == 0)) {
                if (currentPit == 6){
                    boardStores.get(0).addStones(1);
                    enteredStore = false;
                } else if (currentPit == 0){
                    boardStores.get(1).addStones(1);
                    enteredStore = false;
                }
            }
            if (!enteredStore){
                System.out.println("HERE");
                boardPits.get(currentPit).addStone();
                enteredStore = false;
            }
            stonesToMove--;
        }   
        if (currentPit != (startPit - 1) && boardPits.get(currentPit).getStoneCount() == 1) {
            int pitStones = boardPits.get(currentPit).removeStones();
            player.getStore().addStones(pitStones + 1);
            return pitStones;
        }
        return 0;

    }

    public int getPitValue(int pitNum) throws PitNotFoundException {
        if (!pitValid(pitNum)) {
            throw new PitNotFoundException("");
        }
        return boardPits.get(pitNum - 1).getStoneCount();
    }

    public void registerPlayers(Player one, Player two) {
        one.setStore(boardStores.get(0));
        boardStores.get(0).setOwner(one);
        two.setStore(boardStores.get(1));
        boardStores.get(1).setOwner(two);
    }

    public void resetBoard() {
        for (Pit pit: boardPits) {
            pit.removeStones();
        }

        for (Store store: boardStores) {
            store.emptyStore();
        }

        initializeBoard();
    }

    public void setUpPits() {
        // Pit pits = new Pit();
        for (int i = 0; i < 12; i++) {
            boardPits.add(new Pit());
        }

    }

    public void setUpStores() {
        // Store stores = new Store();
        for (int i = 0; i < 2; i++) {
            boardStores .add(new Store());
        }
    }

    public void setPlayer(Player newPlayer) {
        this.currentPlayer = newPlayer;
    }

    public String toString() {
        StringBuilder board = new StringBuilder();
         // Display pits
        for (int i = 11; i >= 6; i--) {
            board.append(boardPits.get(i).getStoneCount()).append(" ");
        }
        board.append("\n");
        for (int i = 0; i < 6; i++) {
            board.append(boardPits.get(i).getStoneCount()).append(" ");
        }
        board.append("\n");
        // Display stores
        for (int i = 0; i < boardStores.size(); i++) {
            board.append("\nStore ").append(i + 1).append(": ").append(boardStores.get(i)).append("\n");
        }   
        return board.toString();
    }

    
}