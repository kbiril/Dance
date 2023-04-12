package be.vdab.dance.repositories;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@JdbcTest
class DataSourceTest {
    private final DataSource dataSource;

    public DataSourceTest(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Test
    void getConnection() throws SQLException {
        try (var stream = dataSource.getConnection()) {
            assertThat(stream.getCatalog()).isEqualTo("dance");
        }
    }
}
