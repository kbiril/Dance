package be.vdab.dance.repositories;
import be.vdab.dance.domain.Festival;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@JdbcTest
@Import(FestivalRepository.class)
@Sql("/festivals.sql")
class FestivalRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String FESTIVALS = "festivals";
    private final FestivalRepository festivalRepository;

    public FestivalRepositoryTest(FestivalRepository festivalRepository) {
        this.festivalRepository = festivalRepository;
    }
    @Test void findAllGeeftAlleFestivalsGesorteerdOpNaam () {
        assertThat(festivalRepository.findAll()).hasSize(countRowsInTable(FESTIVALS))
                                                .extracting(Festival::getNaam)
                                                .isSortedAccordingTo(String::compareToIgnoreCase);
    }

    @Test void findAllFesitvalsGesorteerdOpNaamDieGeenTicketsMeerHebben() {
        assertThat(festivalRepository.findUitverkocht())
                .hasSize(countRowsInTableWhere(FESTIVALS,"ticketsBeschikbaar = 0"))
                .extracting(Festival::getNaam)
                .isSortedAccordingTo(String::compareToIgnoreCase);
    }

    @Test void create() {
        var id = festivalRepository
                .create(new Festival(0,"Tomorrowland", 1000000L, BigDecimal.valueOf(5000)));
        assertThat(id).isPositive();
        assertThat(countRowsInTableWhere(FESTIVALS, "id = " + id)).isOne();
    }

    private long idVoorGladiolen() {
        return jdbcTemplate.queryForObject("select id from festivals where naam = 'Gladiolen2'", Long.class);
    }
    @Test void delete() {
        var id = idVoorGladiolen();
        festivalRepository.delete(id);
        assertThat(countRowsInTableWhere(FESTIVALS,"id = " + id)).isZero();
    }

    @Test void findAndLockById() {
        var id = idVoorGladiolen();
        assertThat(festivalRepository.findAndLockById(id))
                .hasValueSatisfying(festival -> assertThat(festival.getNaam()).isEqualTo("Gladiolen2"));
    }

    @Test void findAndLockByOnbestaandeId() {
        assertThat(festivalRepository.findAndLockById(Long.MAX_VALUE)).isEmpty();
    }
    @Test void findAantal() {
        assertThat(festivalRepository.findAantal()).isEqualTo(countRowsInTable(FESTIVALS));
    }

    @Test void updateReclamaBudget() {
        var id = idVoorGladiolen();
        festivalRepository.updateReclamaBudget(id,BigDecimal.TEN);
        assertThat(countRowsInTableWhere(FESTIVALS,"reclameBudget = 510 and id = " + id)).isOne();
    }
}