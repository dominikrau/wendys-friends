package at.ac.tuwien.sepm.assignment.individual.endpoint;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.util.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(HorseEndpoint.BASE_URL)
public class HorseEndpoint {
    static final String BASE_URL = "/horses";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final HorseService horseService;
    private final HorseMapper horseMapper;

    @Autowired
    public HorseEndpoint(HorseService horseService, HorseMapper horseMapper) {
        this.horseService = horseService;
        this.horseMapper = horseMapper;
    }

    @GetMapping
        List<HorseDto> getAll() {
        LOGGER.info("GET " + BASE_URL);
        try {
            return horseMapper.entityToDto(horseService.findAll());
        } catch (ServiceException e) {
            LOGGER.error("Couldn't read horse", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't read horse");
        }
    }

    @GetMapping(value = "/{id}")
    HorseDto getOneById(@PathVariable("id") Long id) {
        LOGGER.info("GET " + BASE_URL + "/{}", id);
        try {
            return horseMapper.entityToDto(horseService.findOneById(id));
        } catch (NotFoundException e) {
            LOGGER.error("Error during reading horse", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find horse", e);
        } catch (ValidationException e) {
            LOGGER.error("Error during searching horse.", e);
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Horse information not valid", e);
        } catch (ServiceException e) {
            LOGGER.error("Couldn't read horse with id ", e);
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Couldn't read horse");
        }
    }

    @GetMapping(value = "/ownerid/{id}")
    List<HorseDto> getHorsesWithOwnerId(@PathVariable("id") Long id) {
        LOGGER.info("GET " + BASE_URL + "/ownerid/{}", id);
        try {
            return horseMapper.entityToDto(horseService.searchForHorsesWithOwnerId(id));
        } catch (NotFoundException e) {
            LOGGER.error("No horses found with Owner id: " + id, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No horses found" , e);
        } catch (ValidationException e) {
            LOGGER.error("Error during searching horse.", e);
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error during searching horse", e);
        } catch (ServiceException e) {
            LOGGER.error("Couldn't read horse", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't read horse");
        }
    }

    @GetMapping(value = "/search")
    List<HorseDto> getHorseSearch(@RequestParam(required = false) String name, @RequestParam(required = false) String description, @RequestParam(required = false) String rating,
                               @RequestParam(required = false) String date, @RequestParam(required = false) String breed) {
        LOGGER.info("Search for horses: " + "name: \"" + name + "\" description: \"" + description + "\" rating: \"" + rating + "\" date: \"" + date + "\" breed: \"" + breed + "\"");
        try {
            LocalDate localDate = null;
            if (date != null && !date.equals("")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                localDate = LocalDate.parse(date, formatter);
            }
            int ratingInt = Integer.MIN_VALUE;
            if (rating != null && !rating.equals("")) {
                ratingInt = Integer.parseInt(rating);
            }
            Horse horse = new Horse(null, null, null, name, ratingInt, localDate, description, null, null, breed, null);
            return horseMapper.entityToDto(horseService.searchForHorse(horse));
        } catch (NotFoundException e) {
            LOGGER.error("Error during searching horse", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find horse", e);
        } catch (ValidationException e) {
            LOGGER.error("Error during searching horse.", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horse search-parameters not valid", e);
        }  catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HorseDto create(@RequestBody HorseDto horseDto) {
        LOGGER.info("POST " + BASE_URL + "/{}", horseDto.getName());
        try {
            Horse horseEntity = horseMapper.dtoToEntity(horseDto);
            return horseMapper.entityToDto(horseService.create(horseEntity));
        } catch (ValidationException e) {
            LOGGER.error("Error during saving horse", e);
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Horse information not valid", e);
        } catch (ServiceException e) {
            LOGGER.error("Couldn't create horse", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't save horse");
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteHorseById(@PathVariable("id") Long id) {
        LOGGER.info("Delete " + BASE_URL + "/" + id);
        try {
            horseService.deleteHorseById(id);
        } catch (NotFoundException e) {
            LOGGER.error("Horse to delete with not found, id: " + id, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Horse to delete not found" , e);
        } catch (ValidationException e) {
            LOGGER.error("Error during deleting horse.", e);
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error during deleting horse", e);
        } catch (ServiceException e) {
            LOGGER.error("Couldn't delete horse", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't delete horse");
        }
    }

    @PutMapping(value = "/{id}")
    HorseDto updateHorse(@RequestBody HorseDto horseDto, @PathVariable Long id) {
        LOGGER.info("PUT " + BASE_URL + "/{}", horseDto.getName());
        try {
            Horse newHorseEntity = horseMapper.dtoToEntity(horseDto);
            return horseMapper.entityToDto(horseService.updateHorse(newHorseEntity));
        } catch (NotFoundException e) {
            LOGGER.error("Horse to update with not found, id: " + id, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Horse to update not found" , e);
        } catch (ValidationException e) {
            LOGGER.error("Error during updating horse.", e);
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Horse information not valid", e);
        } catch (ServiceException e) {
            LOGGER.error("Couldn't update horse", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't update horse");
        }
    }
}
