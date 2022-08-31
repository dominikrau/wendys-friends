package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface OwnerDao {

    /**
     * @param id of the owner to find.
     * @return the owner with the specified id.
     * @throws DataAccessException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException   will be thrown if the owner could not be found in the database.
     */
    Owner findOneById(Long id);

    /**
     * @return all owners in the database.
     * @throws DataAccessException will be thrown if something goes wrong during the database access.
     */
    List<Owner> findAll();

    /**
     * @param owner of the owner to save.
     * @return the saved owner.
     * @throws DataAccessException will be thrown if something goes wrong during the database access.
     */
    Owner create(Owner owner);

    /**
     * @param owner of the owner to update.
     * @return the updated owner.
     * @throws DataAccessException will be thrown if something goes wrong during the database access.
     */
    Owner updateOwner(Owner owner);

    /**
     * @param id of the owner to find.
     * @throws DataAccessException will be thrown if something goes wrong during the database access.
     */
    void deleteOwnerById(Long id);

    /**
     * @param name of the owner to search for.
     * @return a list of owners with the input parameter name being part of their name.
     * @throws DataAccessException will be thrown if something goes wrong during the database access.
     */
    List<Owner> searchForName(String name);

    /**
     * @param name of the owner to find.
     * @return the owner with the specified name.
     * @throws DataAccessException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException   will be thrown if the owner could not be found in the database.
     */
    Owner getOwnerByName(String name);

}
