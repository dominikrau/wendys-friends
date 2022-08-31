package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.OwnerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class HorseJdbcDao implements HorseDao {

    private static final String TABLE_NAME ="Horse";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private OwnerDao ownerDao;

    @Autowired
    public HorseJdbcDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, OwnerDao ownerDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.ownerDao = ownerDao;
    }

    @Override
    public List<Horse> findAll() {
        LOGGER.trace("Get all horses");
        final String sql = "SELECT * FROM " + TABLE_NAME;
        List<Horse> horses = jdbcTemplate.query(sql, new Object[]{}, this::mapRow);

        return horses;
    }

    @Override
    public Horse findOneById(Long id) throws NotFoundException {
        LOGGER.trace("Get horse with id {}", id);
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        List<Horse> horses = jdbcTemplate.query(sql, new Object[] { id }, this::mapRow);

        if (horses.isEmpty()) {
            LOGGER.error("Could not find horse with id " + id);
            throw new NotFoundException("Could not find horse");
        }
        return horses.get(0);
    }


    @Override
    public Horse create(Horse horse) {
        LOGGER.trace("Save horse with name {}", horse.getName());
        LocalDateTime currentTime = LocalDateTime.now();
        horse.setCreatedAt(currentTime);
        horse.setUpdatedAt(currentTime);

        final String sql = "INSERT INTO " + TABLE_NAME + " (name, rating, birthDate, description, created_at, updated_at, ownerId, breed, picture)" +
            " VALUES (?,?,?,?,?,?,?,?,?);";
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, horse.getName());
            stmt.setInt(2, horse.getRating());
            stmt.setObject(3, horse.getBirthDate());
            stmt.setString(4, horse.getDescription());
            stmt.setObject(5, horse.getCreatedAt());
            stmt.setObject(6, horse.getUpdatedAt());
            stmt.setObject(7, horse.getOwnerId());
            stmt.setString(8, horse.getBreed());
            stmt.setBytes(9, horse.getPicture());
            return stmt;
        }, keyholder);
        horse.setId(((Number) keyholder.getKeys().get("id")).longValue());
        return horse;
    }

    @Override
    public Horse updateHorse(Horse horse) {
        LOGGER.trace("Update horse with name" + horse.getName());
        LocalDateTime currentTime = LocalDateTime.now();
        horse.setUpdatedAt(currentTime);

        final String sql = "UPDATE " + TABLE_NAME + " set name=?, ownerId=?, rating=?, birthDate=?, description=?, updated_at=?, breed=? where id=?;";
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, horse.getName());
            stmt.setObject(2, horse.getOwnerId());
            stmt.setInt(3, horse.getRating());
            stmt.setObject(4, horse.getBirthDate());
            stmt.setString(5, horse.getDescription());
            stmt.setObject(6, horse.getUpdatedAt());
            stmt.setString(7, horse.getBreed());
            stmt.setObject(8, horse.getId());
            return stmt;
        });
        return horse;
    }

    @Override
    public void deleteHorseById(Long id) {
        LOGGER.trace("Delete horse with id: " + id);

        final String sql = "DELETE FROM " + TABLE_NAME + " where id=?;";
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, id);
            return stmt;
        });
    }

    @Override
    public List<Horse> searchForHorsesWithOwnerId(Long id) {
        LOGGER.trace("Search for horses with OwnerId " + id);
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ownerId=?";
        List<Horse> horses = jdbcTemplate.query(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, id);
            return stmt;
        }, this::mapRow);

        if (horses.isEmpty()) {
            LOGGER.info("Could not find any horses that matches owner-Id " + id);
        }
        return horses;
    }

    public List<Horse> searchForHorse(Horse horse) {
        String temp = "";
        final int min = Integer.MIN_VALUE;
        if         (horse.getRating() == min && horse.getDescription() != null && !horse.getDescription().equals("") && (horse.getBreed() == null || horse.getBreed().equals(""))) {
            temp = "SELECT * FROM " + TABLE_NAME + " where UPPER(name) like UPPER(?) and birthDate <= ? and UPPER(description) like UPPER(?);";
        } else if (horse.getRating() == min && horse.getDescription() != null && !horse.getDescription().equals("") && horse.getBreed() != null) {
            temp = "SELECT * FROM " + TABLE_NAME + " where UPPER(name) like UPPER(?) and birthDate <= ? and UPPER(description) like UPPER(?) and UPPER(breed)=UPPER(?);";
        } else if (horse.getRating() == min && (horse.getDescription() == null || horse.getDescription().equals("")) && (horse.getBreed() == null || horse.getBreed().equals(""))) {
            temp = "SELECT * FROM " + TABLE_NAME + " where UPPER(name) like UPPER(?) and birthDate <= ?;";
        } else if (horse.getRating() == min && (horse.getDescription() == null || horse.getDescription().equals("")) && horse.getBreed() != null) {
            temp = "SELECT * FROM " + TABLE_NAME + " where UPPER(name) like UPPER(?) and birthDate <= ? and UPPER(breed)=UPPER(?);";
        } else if (horse.getRating() != min && horse.getDescription() != null && !horse.getDescription().equals("") && (horse.getBreed() == null || horse.getBreed().equals(""))) {
            temp = "SELECT * FROM " + TABLE_NAME + " where UPPER(name) like UPPER(?) and birthDate <= ? and UPPER(description) like UPPER(?) and rating=?;";
        } else if (horse.getRating() != min && horse.getDescription() != null && !horse.getDescription().equals("") && horse.getBreed() != null) {
            temp = "SELECT * FROM " + TABLE_NAME + " where UPPER(name) like UPPER(?) and birthDate <= ? and UPPER(description) like UPPER(?) and rating=? and UPPER(breed)=UPPER(?);";
        } else if (horse.getRating() != min && (horse.getDescription() == null || horse.getDescription().equals("")) && (horse.getBreed() == null || horse.getBreed().equals(""))){
             temp = "SELECT * FROM " + TABLE_NAME + " where UPPER(name) like UPPER(?) and birthDate <= ? and rating=?;";
        } else if (horse.getRating() != min && (horse.getDescription() == null || horse.getDescription().equals("")) && horse.getBreed() != null){
            temp = "SELECT * FROM " + TABLE_NAME + " where UPPER(name) like UPPER(?) and birthDate <= ? and rating=? and UPPER(breed)=UPPER(?);";
        }
        final String sql = temp;
        List<Horse> horses = jdbcTemplate.query(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            if (horse.getName() == null) {
                stmt.setString(1, "%%");
            } else {
                stmt.setString(1, "%" + horse.getName() + "%");
            }
            if (horse.getBirthDate() == null) {
                stmt.setObject(2, "9999-12-12");
            } else {
                stmt.setObject(2, horse.getBirthDate());
            }
            if (horse.getRating() == min && horse.getDescription() != null && !horse.getDescription().equals("") && (horse.getBreed() == null || horse.getBreed().equals(""))) {
                stmt.setString(3, "%" + horse.getDescription() + "%");
            } else if (horse.getRating() == min && horse.getDescription() != null && !horse.getDescription().equals("") && horse.getBreed() != null && !horse.getBreed().equals("")) {
                stmt.setString(3, "%" + horse.getDescription() + "%");
                stmt.setString(4, horse.getBreed());
            } else if (horse.getRating() != min && horse.getDescription() != null && !horse.getDescription().equals("") && (horse.getBreed() == null || horse.getBreed().equals(""))) {
                stmt.setString(3, "%" + horse.getDescription() + "%");
                stmt.setInt(4, horse.getRating());
            } else if (horse.getRating() != min && horse.getDescription() != null && !horse.getDescription().equals("") && horse.getBreed() != null && !horse.getBreed().equals("")) {
                stmt.setString(3, "%" + horse.getDescription() + "%");
                stmt.setInt(4, horse.getRating());
                stmt.setString(5, horse.getBreed());
            } else if (horse.getRating() != min && (horse.getDescription() == null || horse.getDescription().equals("")) && (horse.getBreed() == null || horse.getBreed().equals(""))){
                stmt.setInt(3, horse.getRating());
            } else if (horse.getRating() != min && (horse.getDescription() == null || horse.getDescription().equals("")) && horse.getBreed() != null && !horse.getBreed().equals("")){
                stmt.setInt(3, horse.getRating());
                stmt.setString(4, horse.getBreed());
            } else if (horse.getRating() == min && (horse.getDescription() == null || horse.getDescription().equals("")) && horse.getBreed() != null && !horse.getBreed().equals("")) {
                stmt.setString(3, horse.getBreed());
            }
            return stmt;
        }, this::mapRow);
        if (horses.isEmpty()) {
            LOGGER.info("Could not find any horses that matches search");
        }
        return horses;
    }
    private Horse mapRow(ResultSet resultSet, int i) throws SQLException {
        final Horse horse = new Horse();
        horse.setOwnerId(resultSet.getLong("ownerId"));
        horse.setId(resultSet.getLong("id"));
        horse.setName(resultSet.getString("name"));
        horse.setRating(resultSet.getInt("rating"));
        horse.setBirthDate(resultSet.getDate("birthdate").toLocalDate());
        horse.setDescription(resultSet.getString("description"));
        horse.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        horse.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
        horse.setOwner(ownerDao.findOneById(resultSet.getLong("ownerId")));
        horse.setBreed(resultSet.getString("breed"));
        return horse;
    }
}
