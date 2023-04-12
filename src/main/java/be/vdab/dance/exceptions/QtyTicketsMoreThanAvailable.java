package be.vdab.dance.exceptions;

public class QtyTicketsMoreThanAvailable extends RuntimeException{

    public QtyTicketsMoreThanAvailable(String message) {
        super(message);
    }
}
