package at.ac.tuwien.sepm.assignment.individual.service;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;

import java.util.List;

public interface HorseService {

    /**
     * @param id of the horse to find.
     * @return the horse with the specified id.
     * @throws NotFoundException   will be thrown if the horse could not be found in the database.
     * @throws ServiceException will be thrown if something went wrong in data access
     */
    Horse findOneById(Long id);

    /**
     * @return the horse with the specified id.
     * @throws NotFoundException   will be thrown if no horse could not be found in the database.
     * @throws ServiceException will be thrown if something went wrong in data access
     */
    List<Horse> findAll();

    /**
     * @param horse the horse to save.
     * @return the saved horse.
     * @throws ServiceException will be thrown if something went wrong in data access
     */
    Horse create(Horse horse);

    /**
     * @param horse of the horse to update.
     * @return the horse with the updated horse.
     * @throws ServiceException will be thrown if something went wrong in data access
     */
    Horse updateHorse(Horse horse);

    /**
     * @param id of the horse to delete.
     * @throws ServiceException will be thrown if something went wrong in data access
     */
    void deleteHorseById(Long id);

    /**
     * @param horse the horse object with the specific search-parameters for the horse to search for.
     * @return a list of all horses that match the search.
     * @throws NotFoundException   will be thrown if no horse could not be found in the database that matches the search criteria.
     * @throws ServiceException will be thrown if something went wrong in data access
     */
    List<Horse> searchForHorse(Horse horse);

    /**
     * @param id the id of the owner.
     * @return a list of horses corresponding to the owner-id.
     * @throws NotFoundException   will be thrown if the horse could not be found in the database.
     * @throws ServiceException will be thrown if something went wrong in data access
     */
    List<Horse> searchForHorsesWithOwnerId(Long id);

}
