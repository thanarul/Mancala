Package mancala
package mancala
All Classes and InterfacesClassesException Classes
Class
Description
Board
Represents the board in the Mancala game.
GameNotOverException
 
InvalidMoveException
 
MancalaGame
Represents a Mancala game.
NoSuchPlayerException
 
Pit
Represents a pit in the Mancala game.
PitNotFoundException
 
Player
Represents a player in the Mancala game.
Store
Represents a store in the Mancala game.
code a mancala game in java using all these classes
make sure it is object oriented programming


package mancala;

import java.util.ArrayList;
import java.util.List;

public class MancalaGame {
    private Board board;
    private Player currentPlayer;

    public MancalaGame(Player player1, Player player2) {
        initializeGame(player1, player2);
    }

    private void initializeGame(Player player1, Player player2) {
        board = new Board(player1, player2);
        currentPlayer = player1;
    }

    public void playGame() {
        while (!isGameOver()) {
            try {
                displayBoard();
                int pitIndex = currentPlayer.chooseMove();
                makeMove(pitIndex);
                switchPlayer();
            } catch (InvalidMoveException | PitNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }

        displayResults();
    }

    private void displayBoard() {
        System.out.println("Current Board:");
        System.out.println(board);
        System.out.println("---------------------");
    }

    private void makeMove(int pitIndex) throws InvalidMoveException, PitNotFoundException {
        Pit selectedPit = board.getPit(currentPlayer, pitIndex);
        if (selectedPit.isEmpty()) {
            throw new InvalidMoveException("Selected pit is empty. Choose a non-empty pit.");
        }

        int stones = selectedPit.pickStones();
        distributeStones(stones, pitIndex);
    }

    private void distributeStones(int stones, int startingPitIndex) {
        int currentIndex = startingPitIndex;
        while (stones > 0) {
            currentIndex = board.getNextPitIndex(currentPlayer, currentIndex);
            board.getPit(currentPlayer, currentIndex).addStone();
            stones--;
        }
        checkCapture(currentIndex);
    }

    private void checkCapture(int lastPitIndex) {
        Pit lastPit = board.getPit(currentPlayer, lastPitIndex);
        if (lastPit.isStore()) {
            // If the last stone lands in the player's store, they get another turn
            System.out.println("You get another turn!");
        } else if (lastPit.isOwnSide() && lastPit.getStones() == 1) {
            // If the last stone lands in an empty pit on the player's side, capture stones
            int oppositePitIndex = board.getOppositePitIndex(lastPitIndex);
            Pit oppositePit = board.getPit(currentPlayer.getOpponent(), oppositePitIndex);
            int capturedStones = oppositePit.pickStones();
            lastPit.pickStones(); // Remove the capturing stone
            board.getStore(currentPlayer).addStones(capturedStones + 1); // Add captured stones + capturing stone to store
            System.out.println("Stones captured!");
        }
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer.getOpponent();
    }

    private boolean isGameOver() {
        return board.isGameOver();
    }

    private void displayResults() {
        System.out.println("Game Over!");
        System.out.println(board);
        System.out.println("Final Scores:");
        System.out.println(board.getStore(board.getPlayer1()));
        System.out.println(board.getStore(board.getPlayer2()));
        System.out.println("Winner: " + determineWinner());
    }

    private Player determineWinner() {
        int scorePlayer1 = board.getStore(board.getPlayer1()).getStones();
        int scorePlayer2 = board.getStore(board.getPlayer2()).getStones();

        if (scorePlayer1 > scorePlayer2) {
            return board.getPlayer1();
        } else if (scorePlayer1 < scorePlayer2) {
            return board.getPlayer2();
        } else {
            return null; // It's a tie
        }
    }

    public static void main(String[] args) {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        MancalaGame game = new MancalaGame(player1, player2);
        game.playGame();
    }
}
