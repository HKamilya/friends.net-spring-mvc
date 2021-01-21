package ru.itis.javalab.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.itis.javalab.model.Review;
import ru.itis.javalab.services.EventsService;
import ru.itis.javalab.services.UsersService;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class ReviewsRepositoryImpl implements ReviewsRepository {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private EventsService eventsService;
    private UsersService usersService;

    public ReviewsRepositoryImpl(DataSource dataSource, EventsService eventsService, UsersService usersService) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.eventsService = eventsService;
        this.usersService = usersService;
    }

    private RowMapper<Review> reviewRowMapper = (row, i) -> Review.builder()
            .user_id(usersService.findById(row.getInt("user_id")))
            .event_id(eventsService.findById(row.getInt("event_id")))
            .text(row.getString("text"))
            .date(row.getString("date"))
            .build();

    //language=SQL
    String SQL_SELECT_ALL_BY_EVENT_ID = "SELECT * FROM  review  where event_id=? ";
    //language=SQL
    String SQL_INSERT = "insert into review(user_id,event_id,text,date) values (?,?,?,?)";


    @Override
    public Optional<List<Review>> findAllByEventId(int id) {
        List<Review> reviews;
        try {
            reviews = jdbcTemplate.query(SQL_SELECT_ALL_BY_EVENT_ID, reviewRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            reviews = null;
        }
        return Optional.ofNullable(reviews);
    }

    @Override
    public void insert(Review review) {
        jdbcTemplate.update(SQL_INSERT, review.getUser_id().getId(), review.getEvent_id().getId(), review.getText(), review.getDate());
    }

    @Override
    public Review findById(int id) {
        return null;
    }

    @Override
    public void update(Review adr) {

    }

    @Override
    public List<Review> findAll() {
        return null;
    }
}
