package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.util.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class SimpleHorseService implements HorseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final HorseDao horseDao;
    private final Validator validator;

    @Autowired
    public SimpleHorseService(HorseDao horseDao, Validator validator) {
        this.horseDao = horseDao;
        this.validator = validator;
    }

    @Override
    public List<Horse> findAll() {
        LOGGER.trace("findAll");
        try {
            return horseDao.findAll();
        } catch (DataAccessException e) {
            LOGGER.error("Couldn't read horse", e);
            throw new ServiceException("Couldn't read horse ");
        }
    }

    @Override
    public Horse findOneById(Long id) throws NotFoundException, ServiceException {
        validator.validateId(id);
        LOGGER.trace("findOneById({})", id);
        try {
            return horseDao.findOneById(id);
        } catch (DataAccessException e) {
            LOGGER.error("Couldn't read horse with id " + id, e);
            throw new ServiceException("Couldn't read horse with id " + id);
        }
    }

    @Override
    public Horse create(Horse horse) {
        validator.validateNewHorse(horse);
        LOGGER.trace("save horse ({})", horse);
        try {
            return horseDao.create(horse);
        } catch (DataAccessException e) {
            LOGGER.error("Couldn't create horse", e);
            throw new ServiceException("Couldn't create horse");
        }
    }

    @Override
    public Horse updateHorse(Horse horse) {
        validator.validateUpdateHorse(horse);
        LOGGER.trace("save horse " + horse.getName());
        try {
            horseDao.findOneById(horse.getId());
            return horseDao.updateHorse(horse);
        } catch (DataAccessException e) {
            LOGGER.error("Couldn't update horse", e);
            throw new ServiceException("Couldn't update horse");
        }
    }

    @Override
    public void deleteHorseById(Long id) {
        validator.validateId(id);
        LOGGER.trace("delete horse with id: " + id);
        try {
            horseDao.findOneById(id);
            horseDao.deleteHorseById(id);
        } catch (DataAccessException e) {
            LOGGER.error("Couldn't delete horse", e);
            throw new ServiceException("Couldn't delete horse");
        }
    }

    @Override
    public List<Horse> searchForHorse(Horse horse) throws NotFoundException {
        LOGGER.trace("Search for horses");
        try {
            return horseDao.searchForHorse(horse);
        } catch (DataAccessException e) {
            LOGGER.error("Couldn't read horse", e);
            throw new ServiceException("Couldn't read horse");
        }
    }

    @Override
    public List<Horse> searchForHorsesWithOwnerId(Long id) {
        validator.validateId(id);
        try {
            return horseDao.searchForHorsesWithOwnerId(id);
        } catch (DataAccessException e) {
            LOGGER.error("Couldn't read horse", e);
            throw new ServiceException("Couldn't read horse");
        }
    }
}
