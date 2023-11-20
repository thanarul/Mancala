package mancala;

public class InvalidMoveException extends Exception {
    public InvalidMoveException() {
        super("This is an invalid move");
    }
}