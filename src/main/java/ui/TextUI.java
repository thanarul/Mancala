package ui;

import mancala.MancalaGame;
import mancala.Player;
import mancala.InvalidMoveException;
import mancala.GameNotOverException;
import java.util.Scanner;

public class TextUI {

    private MancalaGame mancalaGame;
    private Scanner mancalaScanner;
    
    public TextUI() {
        this.mancalaGame = new MancalaGame();
        this.mancalaScanner = new Scanner(System.in); 
    }

    public static void main(String[] args) {
        TextUI textUI = new TextUI();
        textUI.startGame();
    }

    public void startGame() {
        MancalaGame game = new MancalaGame();
        Player playerOne = new Player();
        Player playerTwo = new Player();

        Scanner scanner = new Scanner(System.in);
        String userInput;

        System.out.println("Welcome to Mancala!\n");
        System.out.println("Enter Player 1 Name: ");
        userInput = scanner.nextLine();
        playerOne.setName(userInput);

        System.out.println("Enter Player 2 Name: ");
        userInput = scanner.nextLine();
        playerTwo.setName(userInput);

        game.setPlayers(playerOne, playerTwo);
        game.startNewGame();

        // System.out.println(game.toString());
        while (!game.isGameOver()) {
           System.out.println(game.getBoard());
           System.out.println("Current Player: " + game.getCurrentPlayer().getName());
          
           int startPit = promptMove(game.getCurrentPlayer(), playerOne, playerTwo);


           try {
               int stonesMoved = game.move(startPit);
               System.out.println("Stones moved to store: " + stonesMoved);
           } catch (InvalidMoveException e) {
               System.out.println("invalid");
           }

           if (game.getCurrentPlayer() == playerOne) {
            game.setCurrentPlayer(playerTwo);
           } else {
            game.setCurrentPlayer(playerOne);
           }
       }


       System.out.println("Game Over!");
       try {
           System.out.println("Winner: " + game.getWinner().getName());
       } catch (GameNotOverException e) {
           System.out.println("Game is not over yet");
       }
   }


   private Player createPlayer(int playerNumber) {
       System.out.println("Enter name for Player: " + playerNumber);
       String playerName = mancalaScanner.nextLine();
       return new Player(playerName);
   }


   private int promptMove(Player currentPlayer, Player playerOne, Player playerTwo) {
       int startPit = -1;

       if (playerOne == currentPlayer) {
        System.out.println("Enter the pit number to move stones from 1-6: ");
       } else{
        System.out.println("Enter the pit number to move stones from 7-12: ");
       }
       do {
           startPit = Integer.parseInt(mancalaScanner.nextLine());
       } while (startPit < 1 || startPit > 11);
       return startPit;
   }
}
