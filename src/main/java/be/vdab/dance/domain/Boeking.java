package be.vdab.dance.domain;

public class Boeking {
    private final long id;
    private final String naam;
    private final int aantal;
    private final long festivalId;
// deze constructor voor boekingen te raadplegen die al in DB zijn toegevoegd
    public Boeking(long id, String naam, int aantal, long festivalId) {
        this.id = id;
        this.naam = naam;
        this.aantal = aantal;
        this.festivalId = festivalId;
    }
// deze consturctor voor een boeking aan te maken
    public Boeking(String naam, int aantal, long festivalId) {
        if (naam.isBlank()) throw new IllegalArgumentException("De naam mag niet leeg zijn");
        if (aantal <= 0) throw new IllegalArgumentException("aantal moet positief zijn");
        if (festivalId <= 0) throw new IllegalArgumentException("id van festival moet positief zijn");
        this.id = 0;
        this.naam = naam;
        this.aantal = aantal;
        this.festivalId = festivalId;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public int getAantal() {
        return aantal;
    }

    public long getFestivalId() {
        return festivalId;
    }
}
