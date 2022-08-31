package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface HorseDao {

    /**
     * @return the horse with the specified id.
     * @throws DataAccessException will be thrown if something goes wrong during the database access.
     */
    List<Horse> findAll();

    /**
     * @param id of the horse to find.
     * @return the horse with the specified id.
     * @throws DataAccessException will be thrown if something goes wrong during the database access.
     * @throws NotFoundException   will be thrown if the horse could not be found in the database.
     */
    Horse findOneById(Long id);

    /**
     * @param horse the horse to save.
     * @return the saved horse.
     * @throws DataAccessException will be thrown if something goes wrong during the database access.
     */
    Horse create(Horse horse);

    /**
     * @param horse of the horse to update.
     * @return the horse with the updated horse.
     * @throws DataAccessException will be thrown if something goes wrong during the database access.
     */
    Horse updateHorse(Horse horse);

    /**
     * @param id of the horse to delete.
     * @throws DataAccessException will be thrown if something goes wrong during the database access.
     */
    void deleteHorseById(Long id);

    /**
     * @param horse the horse object with the specific search-parameters for the horse to search for.
     * @return a list of all horses that match the search.
     * @throws DataAccessException will be thrown if something goes wrong during the database access.
     */
    List<Horse> searchForHorse(Horse horse);

    /**
     * @param id the id of the owner.
     * @return a list of horses corresponding to the owner-id.
     * @throws DataAccessException will be thrown if something goes wrong during the database access.
     */
    List<Horse> searchForHorsesWithOwnerId(Long id);

}
