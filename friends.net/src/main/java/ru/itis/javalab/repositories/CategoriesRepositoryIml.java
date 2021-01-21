package ru.itis.javalab.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.itis.javalab.model.Categories;

import javax.sql.DataSource;
import java.util.List;

public class CategoriesRepositoryIml implements CategoriesRepository {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //language=SQL
    String SQL_SELECT_ALL = "SELECT * FROM categories";
    //language=SQL
    String SQL_SELECT_BY_ID = "SELECT * FROM categories where id=?";

    public CategoriesRepositoryIml(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private RowMapper<Categories> categoryRowMapper = (row, i) -> Categories.builder()
            .id(row.getInt("id"))
            .name(row.getString("name"))
            .description(row.getString("description"))
            .build();


    @Override
    public void insert(Categories adr) {

    }

    @Override
    public Categories findById(int id) {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, categoryRowMapper, id);
    }

    @Override
    public void update(Categories adr) {

    }

    @Override
    public List<Categories> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, categoryRowMapper);

    }
}
