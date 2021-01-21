package ru.itis.javalab.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.itis.javalab.model.Request;
import ru.itis.javalab.services.EventsService;
import ru.itis.javalab.services.UsersService;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RequestsRepositoryImpl implements RequestsRepository {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private EventsService eventsService;
    private UsersService usersService;

    //language=SQL
    String SQL_SELECT_ALL_BY_EVENT_ID = "SELECT * FROM request where event_id=?";
    //language=SQL
    String SQL_SELECT_ALL_BY_SUBSCRIBER_ID = "SELECT * FROM request where subscriber_id=?";
    //language=SQL
    String SQL_SELECT_BY_USER_ID_AND_EVENT_ID = "SELECT * FROM request where subscriber_id=? and event_id=?";
    //language=SQL
    String SQL_INSERT = "insert into request(event_id, subscriber_id, comment) values (?,?,?)";
    //language=SQL
    String SQL_DELETE = "DELETE FROM request where event_id=? and subscriber_id=?";

    public RequestsRepositoryImpl(DataSource dataSource, EventsService eventsService, UsersService usersService) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.eventsService = eventsService;
        this.usersService = usersService;
    }

    private RowMapper<Request> requestRowMapper = (row, i) -> Request.builder()
            .event_id(eventsService.findById(row.getInt("event_id")))
            .subscriber_id(usersService.findById(row.getInt("subscriber_id")))
            .comment(row.getString("comment"))
            .build();

    @Override
    public Optional<List<Request>> findAllByEventId(int id) {
        List<Request> requests = new ArrayList<>();
        try {
            requests = jdbcTemplate.query(SQL_SELECT_ALL_BY_EVENT_ID, requestRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            requests = null;
        }
        return Optional.ofNullable(requests);
    }

    @Override
    public void delete(int event_id, int user_id) {
        jdbcTemplate.update(SQL_DELETE, event_id, user_id);
    }


    //"insert into request(event_id, subscriber_id, comment) values (?,?,?)"
    @Override
    public void insert(Request adr) {
        List<Request> requests = jdbcTemplate.query(SQL_SELECT_ALL_BY_EVENT_ID, requestRowMapper, adr.getEvent_id().getId());
        boolean isSubscriber = false;
        for (Request req : requests) {
            if (req.getSubscriber_id().getId() == adr.getSubscriber_id().getId()) {
                isSubscriber = true;
                break;
            }
        }
        if (!isSubscriber) {
            jdbcTemplate.update(SQL_INSERT, adr.getEvent_id().getId(), adr.getSubscriber_id().getId(), adr.getComment());
        }
    }

    @Override
    public Request findById(int id) {
        return null;
    }

    @Override
    public void update(Request adr) {
    }

    public List<Request> findAllByUserId(int id) {
        return jdbcTemplate.query(SQL_SELECT_ALL_BY_SUBSCRIBER_ID, requestRowMapper, id);
    }

    @Override
    public List<Request> findAll() {
        return null;
    }
}
