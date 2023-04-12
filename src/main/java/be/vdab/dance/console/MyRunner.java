package be.vdab.dance.console;

import be.vdab.dance.domain.Boeking;
import be.vdab.dance.exceptions.FestivalNotFoundException;
import be.vdab.dance.exceptions.QtyTicketsMoreThanAvailable;
import be.vdab.dance.services.FestivalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MyRunner implements CommandLineRunner {
    private final FestivalService festivalService;
    public MyRunner(FestivalService festivalService) {
        this.festivalService = festivalService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Uw naam:");
        var naam = scanner.nextLine();

        System.out.println("Aantal Tickets");
        var aantal = scanner.nextInt();

        System.out.println("Festival id:");
        var idFestival = scanner.nextLong();

        try {
            var boeking = new Boeking(naam, aantal, idFestival);
            festivalService.boek(boeking);
            System.out.println("Boeking is gelukt, de boeking Nr is " + boeking.getId());
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
        } catch (FestivalNotFoundException ex) {
            System.err.println("Boeking mislukt, de festival met de ID " + ex.getId() + " is niet gevonden");
        } catch (QtyTicketsMoreThanAvailable ex) {
            System.out.println(ex.getMessage());
        }


//        System.out.println("Naam:");
//        var naam = scanner.nextLine();
//        System.out.println("Tickets Beschikbaar:");
//        var ticketsBeschikbaar = scanner.nextLong();
//        System.out.println("Reclame Budget:");
//        var reclameBudget = scanner.nextBigDecimal();
//
//        Festival newFestival = new Festival(0, naam, ticketsBeschikbaar,reclameBudget);
//        long nieuwId = festivalService.create(newFestival);
//        System.out.println("ID van de newFestival is " + nieuwId);
//
//        festivalService.findAll().forEach(festival -> System.out.println(festival.getNaam()));
//        System.out.println("\n\nUitverkochte festivals:");
//        festivalService.findUitverkocht().forEach(festival -> System.out.println(festival.getNaam() + " - tickets " + festival.getTicketsBeschikbaar()));
//
//        System.out.println("ID:");
//        var id = scanner.nextLong();
//        festivalService.delete(id);
    }
}
