package be.vdab.dance.repositories;

import be.vdab.dance.domain.Boeking;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class BoekingRepository {
    private final JdbcTemplate template;

    public BoekingRepository(JdbcTemplate template) {
        this.template = template;
    }

    public void create(Boeking boeking) {
        var sql = """
                    insert into boekingen (naam, aantal, festivalId)
                    values (?, ?, ?)
                  """;

        template.update(sql, boeking.getNaam(), boeking.getAantal(), boeking.getFestivalId());
//        var keyHolder = new GeneratedKeyHolder();

//        template.update(
//                connection -> {
//                    var statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
//                    statement.setString(1, boeking.getNaam());
//                    statement.setInt(2, boeking.getAantal());
//                    statement.setLong(3, boeking.getFestivalId());
//                    return statement;
//                },
//                keyHolder
//        );
//        return keyHolder.getKey().longValue();
    }
}
