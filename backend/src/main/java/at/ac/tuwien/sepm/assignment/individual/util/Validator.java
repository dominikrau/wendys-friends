package at.ac.tuwien.sepm.assignment.individual.util;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void validateId(Long id) throws ValidationException {
        if (id < 1) {
            LOGGER.error("Id not valid: " + id);
            throw new ValidationException("Id not valid: " + id);
        }
    }

    public void validateName(String name) throws ValidationException {
        if (name == null || name.equals("")) {
            LOGGER.error("Name not valid");
            throw new ValidationException("Name not valid");
        }
    }

    public void validateNewOwner(Owner owner) throws ValidationException {
        if (owner.getName() == null || owner.getName().equals("")) {
            LOGGER.error("New owner not valid");
            throw new ValidationException("New owner not valid");
        }
    }

    public void validateUpdateOwner(Owner owner) throws ValidationException {
        if (owner.getName() == null || owner.getName().equals("") || owner.getId() == null) {
            LOGGER.error("New updated owner not valid");
            throw new ValidationException("Updating Owner not valid");
        }
    }

    public void validateNewHorse(Horse horse) throws ValidationException {
        if (horse.getName() == null         ||
            horse.getName().equals("")      ||
            horse.getBirthDate() == null    ||
            horse.getRating() > 5           ||
            horse.getRating() < 1)
        {
            LOGGER.error("New horse not valid");
            throw new ValidationException("New horse not valid");
        }
    }

    public void validateUpdateHorse(Horse horse) throws ValidationException {
        if (horse.getName() == null         ||
            horse.getBirthDate() == null    ||
            horse.getRating() > 5           ||
            horse.getRating() < 1)
        {
            LOGGER.error("New updated horse not valid");
            throw new ValidationException("New horse not valid");
        }
    }
}
