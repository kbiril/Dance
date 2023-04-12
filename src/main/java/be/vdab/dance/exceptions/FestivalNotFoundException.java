package be.vdab.dance.exceptions;

public class FestivalNotFoundException extends RuntimeException{
    private final long id;
    public FestivalNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
