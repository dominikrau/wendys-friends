package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.OwnerDao;
import java.lang.invoke.MethodHandles;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class OwnerJdbcDao implements OwnerDao {

    private static final String TABLE_NAME = "Owner";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public OwnerJdbcDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Owner findOneById(Long id) throws NotFoundException {
        LOGGER.trace("Get owner with id {}", id);
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";

        List<Owner> owners = jdbcTemplate.query(sql, new Object[]{id}, this::mapRow);
        if (owners.isEmpty()) {
            LOGGER.error("Could not find owner with id " + id);
            throw new NotFoundException("Could not find owner");
        }

        return owners.get(0);
    }

    @Override
    public List<Owner> findAll() {
        LOGGER.trace("Get all owners");
        final String sql = "SELECT * FROM " + TABLE_NAME;
        List<Owner> owners = jdbcTemplate.query(sql, new Object[]{}, this::mapRow);

        return owners;
    }

    @Override
    public Owner create(Owner owner) {
        LOGGER.trace("Save owner with name {}", owner.getName());
        LocalDateTime currentTime = LocalDateTime.now();
        owner.setCreatedAt(currentTime);
        owner.setUpdatedAt(currentTime);

        final String sql = "INSERT INTO " + TABLE_NAME + " (name, created_at, updated_at)" +
            " VALUES (?,?,?);";
        KeyHolder keyholder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, owner.getName());
            stmt.setObject(2, owner.getCreatedAt());
            stmt.setObject(3, owner.getUpdatedAt());
            return stmt;
        }, keyholder);
        owner.setId(((Number) keyholder.getKeys().get("id")).longValue());
        return owner;
    }


    @Override
    public Owner updateOwner(Owner owner) {
        LOGGER.trace("Update owner with name {}", owner.getName());
        LocalDateTime currentTime = LocalDateTime.now();
        owner.setUpdatedAt(currentTime);

        final String sql = "UPDATE " + TABLE_NAME + " set name=?, updated_at=? where id=?;";
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, owner.getName());
            stmt.setObject(2, owner.getUpdatedAt());
            stmt.setObject(3, owner.getId());
            return stmt;
        });
        LOGGER.info(owner.getName());
        return owner;
    }

    @Override
    public void deleteOwnerById(Long id) {
        LOGGER.trace("Delete owner with id " + id);

        final String sql = "DELETE FROM " + TABLE_NAME + " where id=?;";
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, id);
            return stmt;
        });
    }

    @Override
    public List<Owner> searchForName(String name) {
        LOGGER.trace("Get all owners with {} in name", name);
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE UPPER(name) like UPPER(?)";
        List<Owner> owners = jdbcTemplate.query(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, "%" + name + "%");
            return stmt;
        }, this::mapRow);
        if (owners.isEmpty()) {
            LOGGER.error("Could not find any owner with name " + name);
        }

        return owners;
    }

    @Override
    public Owner getOwnerByName(String name) throws NotFoundException, PersistenceException {
        LOGGER.trace("Get owner with name " + name);
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE name=?";
        List<Owner> owners = jdbcTemplate.query(sql, new Object[]{ name }, this::mapRow);

        if (owners.isEmpty()) {
            LOGGER.error("Could not find owner with name " + name);
            throw new NotFoundException("Could not find any owner with name " + name);
        }
        if (owners.size() > 1) {
            LOGGER.error("More than 1 owner with same name: " + name);
            throw new PersistenceException("More than one owner with same name");
        }
        return owners.get(0);
    }

    private Owner mapRow(ResultSet resultSet, int i) throws SQLException {
        final Owner owner = new Owner();
        owner.setId(resultSet.getLong("id"));
        owner.setName(resultSet.getString("name"));
        owner.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        owner.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
        return owner;
    }
}
