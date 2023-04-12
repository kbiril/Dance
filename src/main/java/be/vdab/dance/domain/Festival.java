package be.vdab.dance.domain;

import be.vdab.dance.exceptions.QtyTicketsMoreThanAvailable;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Festival {
    private final long id;
    private final String naam;
    private long ticketsBeschikbaar;
    private BigDecimal reclameBudget;

    public Festival(long id, String naam, long ticketsBeschikbaar, BigDecimal reclameBudget) {
        this.id = id;
        this.naam = naam;
        this.ticketsBeschikbaar = ticketsBeschikbaar;
        this.reclameBudget = reclameBudget;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public long getTicketsBeschikbaar() {
        return ticketsBeschikbaar;
    }

    public BigDecimal getReclameBudget() {
        return reclameBudget;
    }

    public void boek(int aantal) {

        if (ticketsBeschikbaar < aantal) {
            throw new QtyTicketsMoreThanAvailable("Aantal van beschikbare tickets is " +
                                                  "minder dan gewenste boeking aantal");
        }
        ticketsBeschikbaar -= aantal;
    }
}
