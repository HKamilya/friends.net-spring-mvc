package ru.itis.javalab.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.itis.javalab.model.Image;

import javax.sql.DataSource;
import java.util.List;

public class ImagesRepositoryImpl implements ImagesRepository {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    //language=SQL
    String SQL_INSERT_RETURN_ID = "insert into image(type, address) values (?,?)";
    //language=SQL
    String SQL_UPDATE = "update image set address=?, type=? where id=?";
    //language=SQL
    String SQL_SELECT_BY_ID = "SELECT * FROM image where id=?";
    //language=SQL
    String SQL_SELECT_BY_ADDRESS = "SELECT * FROM image where address=?";


    public ImagesRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private RowMapper<Image> imageRowMapper = (row, i) -> Image.builder()
            .id(row.getInt("id"))
            .type(row.getString("type"))
            .address(row.getString("address"))
            .build();


    public void insert(Image adr) {
        jdbcTemplate.update(SQL_INSERT_RETURN_ID, adr.getType(), adr.getAddress());

    }

    @Override
    public Image findById(int id) {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, imageRowMapper, id);
    }

    @Override
    public void update(Image adr) {
        jdbcTemplate.update(SQL_UPDATE, adr.getAddress(), adr.getType(), adr.getId());
    }

    @Override
    public List<Image> findAll() {
        return null;
    }

    @Override
    public Image findByAddress(String address) {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_ADDRESS, imageRowMapper, address);
    }
}
