package ru.itis.javalab.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.itis.javalab.model.User;
import ru.itis.javalab.services.ImagesService;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryImpl implements UsersRepository {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private ImagesService imagesService;

    //language=SQL
    String SQL_INSERT = "insert into \"user\"(fullname,email,username,password,image) values (?,?,?,?,?)";
    //language=SQL
    String SQL_SELECT_USERNAME_PASSWORD = "SELECT * FROM \"user\" where username=? and password=?";
    //language=SQL
    String SQL_SELECT_BY_USERNAME = "SELECT * FROM \"user\" where username=?";
    //language=SQL
    String SQL_UPDATE = "update \"user\" set fullname=?, description=?, image=? where username=?";
    //language=SQL
    String SQL_SELECT_BY_ID = "SELECT * FROM \"user\" where id=?";
    //language=SQL
    String SQL_SELECT_BY_UUID = "SELECT * FROM \"user\" where uuid=?";
    //language=SQL
    String SQL_UPDATE_UUID = "update \"user\" set uuid=? where username=?";


    public UsersRepositoryImpl(DataSource dataSource, ImagesService imagesService) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.imagesService = imagesService;
    }

    private RowMapper<User> userRowMapper = (row, i) -> User.builder()
            .id(row.getInt("id"))
            .fullname(row.getString("fullname"))
            .email(row.getString("email"))
            .username(row.getString("username"))
            .password(row.getString("password"))
            .description(row.getString("description"))
            .image(imagesService.findById(row.getInt("image")))
            .build();

    @Override
    public User authenticateUser(User user) {
        return jdbcTemplate.queryForObject(SQL_SELECT_USERNAME_PASSWORD, userRowMapper, user.getUsername(), user.getPassword());
    }

    @Override
    public Optional<User> findByUUID(String uuid) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_UUID, userRowMapper, uuid));
    }

    @Override
    public void updateUUID(User user) {
        jdbcTemplate.update(SQL_UPDATE_UUID, user.getUUID(), user.getUsername());
    }


    @Override
    public User findByName(String name) {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_USERNAME, userRowMapper, name);
    }

    @Override
    public void insert(User user) {
        jdbcTemplate.update(SQL_INSERT, user.getFullname(), user.getEmail(), user.getUsername(), user.getPassword(), user.getImage().getId());
    }

    @Override
    public User findById(int id) {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, userRowMapper, id);
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(SQL_UPDATE, user.getFullname(), user.getDescription(), user.getImage().getId(), user.getUsername());
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
