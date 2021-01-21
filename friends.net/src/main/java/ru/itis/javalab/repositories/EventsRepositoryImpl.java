package ru.itis.javalab.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.itis.javalab.model.Event;
import ru.itis.javalab.services.CategoriesService;
import ru.itis.javalab.services.ImagesService;
import ru.itis.javalab.services.UsersService;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsRepositoryImpl implements EventsRepository {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private UsersService usersService;
    private CategoriesService categoriesService;
    private ImagesService imagesService;

    //language=SQL
    private static final String SQL_SELECT_ALL_WITH_PAGES = "select * from event where status='актуально' order by id limit :limit offset :offset ;";
    //language=SQL
    String SQL_SELECT_RANDOM = "SELECT * FROM event ORDER BY RANDOM() LIMIT 1";
    //language=SQL
    String SQL_INSERT = "insert into event(user_id,name,city,street,house,image,description,category_id,status,date, time ) values (?,?,?,?,?,?,?,?,?,?,?)";
    //language=SQL
    String SQL_SELECT_ALL_ACTUAL = "SELECT * FROM event where status='актуально' order by date asc";
    //language=SQL
    String SQL_SELECT_BY_USER_ID = "SELECT * FROM event where user_id=? order by date desc ";
    //language=SQL
    String SQL_SELECT_BY_ID = "SELECT * FROM event where id=?";
    //language=SQL
    String SQL_SELECT_BY_NAME = "SELECT * FROM event where name ILIKE ? and status='актуально'";
    //language=SQL
    String SQL_SELECT_BY_NAME_AND_DATE = "SELECT * FROM event where name ILIKE ? and date ILIKE ? and status='актуально'";
    //language=SQL
    String SQL_SELECT_BY_DATE = "SELECT * FROM event where date ILIKE ? and status='актуально'";
    //language=SQl
    String SQl_UPDATE = "update event set status=? where id=?";

    public EventsRepositoryImpl(DataSource dataSource, UsersService usersService, CategoriesService categoriesService, ImagesService imagesService) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.usersService = usersService;
        this.categoriesService = categoriesService;
        this.imagesService = imagesService;
    }

    private RowMapper<Event> eventRowMapper = (row, i) -> Event.builder()
            .id(row.getInt("id"))
            .user_id(usersService.findById(row.getInt("user_id")))
            .name(row.getString("name"))
            .city(row.getString("city"))
            .street(row.getString("street"))
            .house(row.getString("house"))
            .description(row.getString("description"))
            .category_id(categoriesService.findById(row.getInt("category_id")))
            .status(row.getString("status"))
            .date(row.getString("date"))
            .time(row.getString("time"))
            .image(imagesService.findById(row.getInt("image")))
            .build();

    @Override
    public Event findRandomEvent() {
        return jdbcTemplate.queryForObject(SQL_SELECT_RANDOM, eventRowMapper);
    }

    @Override
    public List<Event> findByPage(int page, int size) {
        Map<String, Object> params = new HashMap<>();
        params.put("limit", size);
        params.put("offset", page * size);
        return namedParameterJdbcTemplate.query(SQL_SELECT_ALL_WITH_PAGES, params, eventRowMapper);

    }

    @Override
    public List<Event> findByUserId(int id) {
        return jdbcTemplate.query(SQL_SELECT_BY_USER_ID, eventRowMapper, id);
    }

    @Override
    public List<Event> findByNameAndCategory(String eventName, List<Integer> categories) {
        String sql = "";
        if (categories.size() > 0 & eventName.length() > 0) {
            sql = "SELECT * FROM event where status='актуально' and name ILIKE " + eventName + "%" + " and (";
            for (int i = 0; i < categories.size() - 1; i++) {
                sql += "category_id=" + categories.get(i) + " or ";
            }
            sql += "category_id=" + categories.get(categories.size() - 1) + " );";
            return jdbcTemplate.query(sql, eventRowMapper);
        } else if (categories.size() > 0) {
            sql = "SELECT * FROM event where status='актуально' and (";
            for (int i = 0; i < categories.size() - 1; i++) {
                sql += "category_id=" + categories.get(i) + " or ";
            }
            sql += "category_id=" + categories.get(categories.size() - 1) + " );";
            return jdbcTemplate.query(sql, eventRowMapper);
        }
        return null;
    }

    @Override
    public List<Event> findByNameAndCategoryAndDate(String eventName, List<Integer> categories, String date) {
        String sql = null;
        if (categories.size() > 0 & eventName.length() > 0) {
            sql = "SELECT * FROM event where status='актуально' and name ILIKE ? and date=? and (";
            for (int i = 0; i < categories.size() - 1; i++) {
                sql += "category_id=" + categories.get(i) + " or ";
            }
            sql += "category_id=" + categories.get(categories.size() - 1) + ");";
            String event = eventName + "%";
            return jdbcTemplate.query(sql, eventRowMapper, event, date);
        } else if (categories.size() > 0) {
            sql = "SELECT * FROM event where status='актуально' and date=? and (";
            for (int i = 0; i < categories.size() - 1; i++) {
                sql += " category_id=" + categories.get(i) + " or ";
            }
            sql += "category_id=" + categories.get(categories.size() - 1) + ");";
            return jdbcTemplate.query(sql, eventRowMapper, date);
        }
        return null;
    }

    @Override
    public List<Event> findByName(String eventName) {
        return jdbcTemplate.query(SQL_SELECT_BY_NAME, eventRowMapper, eventName);
    }

    @Override
    public List<Event> findByNameAndDate(String eventName, String eventDate) {
        String event = eventName + "%";
        return jdbcTemplate.query(SQL_SELECT_BY_NAME_AND_DATE, eventRowMapper, event, eventDate);
    }

    @Override
    public List<Event> findByDate(String eventDate) {
        return jdbcTemplate.query(SQL_SELECT_BY_DATE, eventRowMapper, eventDate);
    }

    public void insert(Event event) {
        jdbcTemplate.update(SQL_INSERT, event.getUser_id().getId(), event.getName(), event.getCity(), event.getStreet(),
                event.getHouse(), event.getImage().getId(), event.getDescription(), event.getCategory_id().getId(), event.getStatus(), event.getDate(), event.getTime());
    }

    @Override
    public Event findById(int id) {
        return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, eventRowMapper, id);
    }

    @Override
    public void update(Event adr) {
        jdbcTemplate.update(SQl_UPDATE, adr.getStatus(), adr.getId());
    }

    @Override
    public List<Event> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL_ACTUAL, eventRowMapper);
    }
}
