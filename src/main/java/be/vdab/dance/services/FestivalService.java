package be.vdab.dance.services;

import be.vdab.dance.domain.Boeking;
import be.vdab.dance.domain.Festival;
import be.vdab.dance.exceptions.FestivalNotFoundException;
import be.vdab.dance.repositories.BoekingRepository;
import be.vdab.dance.repositories.FestivalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class FestivalService {
    private final FestivalRepository festivalRepository;
    private final BoekingRepository boekingRepository;

    public FestivalService(FestivalRepository festivalRepository, BoekingRepository boekingRepository) {
        this.festivalRepository = festivalRepository;
        this.boekingRepository = boekingRepository;
    }

    public List<Festival> findAll() {
        return festivalRepository.findAll();
    }

    public List<Festival> findUitverkocht() {
        return festivalRepository.findUitverkocht();
    }

    @Transactional
    public long create(Festival festival) {
        return festivalRepository.create(festival);
    }
    @Transactional
    public void delete(long id) {
        festivalRepository.delete(id);
    }

    @Transactional
    public void cancelFestival(long id) {


        var festivalToCancel = festivalRepository.findAndLockById(id)
                                                 .orElseThrow(() -> new FestivalNotFoundException(id));
        var budget = festivalToCancel.getReclameBudget();
        int qty = festivalRepository.findAantal() - 1;
        if (qty > 0) {
            BigDecimal sumToAddToBudget = budget.divide(BigDecimal.valueOf(qty),2, RoundingMode.HALF_UP);
            festivalRepository.updateReclamaBudget(id,sumToAddToBudget);
            festivalRepository.delete(id);
        } else throw new IllegalArgumentException("mag niet door 0 delen");
    }

    @Transactional
    public void boek(Boeking boeking) {
        var festival = festivalRepository.findAndLockById(boeking.getFestivalId())
                                           .orElseThrow(() -> new FestivalNotFoundException(boeking.getFestivalId()));
        festival.boek(boeking.getAantal());

        festivalRepository.update(festival);
        boekingRepository.create(boeking);
    }

}
