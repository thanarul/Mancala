package mancala;

public class PitNotFoundException extends Exception {
    public PitNotFoundException() {
        super("pit not found");
    }
    public PitNotFoundException(String message) {
        super(message);
    }
}

