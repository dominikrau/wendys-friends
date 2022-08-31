package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.OwnerDao;
import at.ac.tuwien.sepm.assignment.individual.service.OwnerService;
import at.ac.tuwien.sepm.assignment.individual.util.Validator;
import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class SimpleOwnerService implements OwnerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final OwnerDao ownerDao;
    private final Validator validator;

    @Autowired
    public SimpleOwnerService(OwnerDao ownerDao, Validator validator) {
        this.ownerDao = ownerDao;
        this.validator = validator;
    }

    @Override
    public Owner findOneById(Long id) throws NotFoundException {
        validator.validateId(id);
        LOGGER.trace("findOneById({})", id);
        try {
            return ownerDao.findOneById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Couldn't read owner", e);
        }
    }

    @Override
    public List<Owner> findAll() {
        LOGGER.trace("find all");
        try {
            return ownerDao.findAll();
        } catch (DataAccessException e) {
            throw new ServiceException("Couldn't read owner", e);
        }
    }

    @Override
    public Owner create(Owner owner) {
        validator.validateNewOwner(owner);
        LOGGER.trace("save owner ({})", owner.getName());
        try {
            return ownerDao.create(owner);
        } catch (DataAccessException e) {
            throw new ServiceException("Couldn't create owner", e);
        }
    }

    @Override
    public Owner update(Owner owner) throws  NotFoundException {
        validator.validateUpdateOwner(owner);
        LOGGER.trace("update owner ({})", owner.getName());
        try {
            ownerDao.findOneById(owner.getId());
            return ownerDao.updateOwner(owner);
        } catch (DataAccessException e) {
            throw new ServiceException("Couldn't update owner", e);
        }
    }

    @Override
    public void deleteOwnerById(Long id) throws NotFoundException {
        validator.validateId(id);
        LOGGER.trace("delete owner ({})", id);
        try {
            ownerDao.findOneById(id);
            ownerDao.deleteOwnerById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Couldn't delete owner", e);
        }
    }

    @Override
    public List<Owner> searchForName(String name) throws NotFoundException {
        validator.validateName(name);
        LOGGER.trace("search owner" + name);
        try {
            return ownerDao.searchForName(name);
        } catch (DataAccessException e) {
            throw new ServiceException("Couldn't read owner", e);
        }
    }

    @Override
    public Owner getOwnerByName(String name) throws NotFoundException, ServiceException {
        validator.validateName(name);
        LOGGER.trace("search owner" + name);
        try {
            return ownerDao.getOwnerByName(name);
        }  catch (DataAccessException e) {
            throw new ServiceException("Couldn't read owner", e);
        } catch (PersistenceException e) {
            throw new ServiceException("More than one owner with same name");
        }
    }
}
