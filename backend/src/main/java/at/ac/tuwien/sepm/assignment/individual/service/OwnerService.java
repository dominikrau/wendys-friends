package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface OwnerService {

    /**
     * @param id of the owner to find.
     * @return the owner with the specified id.
     * @throws NotFoundException will be thrown if the owner could not be found in the system.
     * @throws ServiceException will be thrown if something went wrong in data access
     */
    Owner findOneById(Long id);

    /**
     * @return all owners in the database.
     * @throws ServiceException will be thrown if something went wrong in data access
     */
    List<Owner> findAll();

    /**
     * @param owner of the owner to save.
     * @return the saved owner.
     * @throws ServiceException will be thrown if something went wrong in data access
     */
    Owner create(Owner owner);

    /**
     * @param owner of the owner to update.
     * @return the updated owner.
     * @throws ServiceException will be thrown if something went wrong in data access
     */
    Owner update(Owner owner);

    /**
     * @param id of the owner to find.
     * @throws ServiceException will be thrown if something went wrong in data access
     */
    void deleteOwnerById(Long id);

    /**
     * @param name of the owner to search for.
     * @return a list of owners with the input parameter name being part of their name.
     * @throws NotFoundException  will be thrown if no owner could not be found in the database.
     * @throws ServiceException will be thrown if something went wrong in data access
     */
    List<Owner> searchForName(String name);

    /**
     * @param name of the owner to find.
     * @return the owner with the specified name.
     * @throws NotFoundException   will be thrown if the owner could not be found in the database.
     * @throws ServiceException will be thrown if something went wrong in data access
     */
    Owner getOwnerByName(String name);

}
