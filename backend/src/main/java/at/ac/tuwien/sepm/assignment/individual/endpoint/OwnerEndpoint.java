package at.ac.tuwien.sepm.assignment.individual.endpoint;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.mapper.OwnerMapper;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.service.OwnerService;
import java.lang.invoke.MethodHandles;
import java.util.List;

import at.ac.tuwien.sepm.assignment.individual.util.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(OwnerEndpoint.BASE_URL)
public class OwnerEndpoint {

    static final String BASE_URL = "/owners";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final OwnerService ownerService;
    private final OwnerMapper ownerMapper;
    private final HorseService horseService;

    @Autowired
    public OwnerEndpoint(OwnerService ownerService, HorseService horseService, OwnerMapper ownerMapper) {
        this.ownerService = ownerService;
        this.ownerMapper = ownerMapper;
        this.horseService = horseService;
    }

    @GetMapping(value = "/{id}")
    public OwnerDto getOneById(@PathVariable("id") Long id) {
        LOGGER.info("GET " + BASE_URL + "/{}", id);
        try {
            return ownerMapper.entityToDto(ownerService.findOneById(id));
        } catch (NotFoundException e) {
            LOGGER.error("Error during reading owner", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find owner", e);
        } catch (ValidationException e) {
            LOGGER.error("Error during validating owner.", e);
            throw new
                ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Owner name incorrect", e);
        } catch (ServiceException e) {
            LOGGER.error("Couldn't read owner", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't read owner");
        }
    }

    @GetMapping
    List<OwnerDto> getAll() {
        LOGGER.info("GET " + BASE_URL);
        try {
            return ownerMapper.entityToDto(ownerService.findAll());
        } catch (ServiceException e) {
            LOGGER.error("Couldn't read owner", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't read owner");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OwnerDto create(@RequestBody OwnerDto ownerDto) {
        LOGGER.info("POST " + BASE_URL + "/{}", ownerDto.getName());
        try {
            Owner ownerEntity = ownerMapper.dtoToEntity(ownerDto);
            return ownerMapper.entityToDto(ownerService.create(ownerEntity));
        } catch (ValidationException e) {
            LOGGER.error("Error during saving owner.", e);
            throw new
                ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error during saving owner", e);
        } catch (ServiceException e) {
            LOGGER.error("Couldn't read owner", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't create owner");
        }
    }

    @PutMapping(value = "/{id}")
    OwnerDto replaceOwner(@RequestBody OwnerDto newOwner, @PathVariable Long id) {
        LOGGER.info("PUT " + BASE_URL + "/{}", newOwner.getName());
        try {
            Owner newOwnerEntity = ownerMapper.dtoToEntity(newOwner);
            return ownerMapper.entityToDto(ownerService.update(newOwnerEntity));
        } catch (NotFoundException e) {
            LOGGER.error("Couldn't find owner to update", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find owner to update", e);
        } catch (ValidationException e) {
            LOGGER.error("Error during updating owner.", e);
            throw new
                ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error during updating owner", e);
        } catch (ServiceException e) {
            LOGGER.error("Couldn't read owner", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't read owner");
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteOwnerById(@PathVariable("id") Long id) {
        LOGGER.info("Delete " + BASE_URL + "/{}", id);
        try {
            if (horseService.searchForHorsesWithOwnerId(id).isEmpty()) {
                ownerService.deleteOwnerById(id);
            } else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner still has horses assigned");
        } catch (NotFoundException e) {
            LOGGER.error("Couldn't find owner to delete", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Couldn't find owner to delete", e);
        } catch (ValidationException e) {
            LOGGER.error("Error during deleting owner.", e);
            throw new
                ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error during deleting owner", e);
        } catch (ServiceException e) {
            LOGGER.error("Couldn't read owner", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't read owner");
        }
    }

    @GetMapping(value = "/namesearch/{name}")
    public List<OwnerDto> searchForName(@PathVariable("name") String name) {
        LOGGER.info("Search for " + BASE_URL + "/{}", name);
        try {
            return ownerMapper.entityToDto(ownerService.searchForName(name));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find any owner with name" + name);
        } catch (ValidationException e) {
            LOGGER.error("Error during searching owner.", e);
            throw new
                ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error during searching owner", e);
        } catch (ServiceException e) {
            LOGGER.error("Couldn't read owner", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't read owner");
        }
    }

    @GetMapping(value = "/name/{name}")
    public OwnerDto getOwnerByName(@PathVariable("name") String name) {
        LOGGER.info("Search for owner: " + name);
        try {
            return ownerMapper.entityToDto(ownerService.getOwnerByName(name));
        } catch (ValidationException e) {
            LOGGER.error("Error during searching owner.", e);
            throw new
                ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error during searching owner", e);
        } catch (NotFoundException e) {
            LOGGER.info("No owner with name \"" + name + "\" exists.");
            throw new
                ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No owner with name " + name + " exists.", e);
        } catch (ServiceException e) {
            LOGGER.info("Multiple owner with name \"" + name + "\" exist.");
            throw new
                ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
