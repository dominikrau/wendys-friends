package at.ac.tuwien.sepm.assignment.individual.unit.persistence;

import static org.junit.jupiter.api.Assertions.*;

import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.OwnerDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class OwnerDaoTestBase {

    @Autowired
    OwnerDao ownerDao;

    @Test
    @DisplayName("Finding owner by non-existing ID should throw NotFoundException")
    public void findingOwnerById_nonExisting_shouldThrowNotFoundException() {
        assertThrows(NotFoundException.class,
            () -> ownerDao.findOneById(-1L));
    }

    @Test
    @DisplayName("Finding owner by non-existing name should throw NotFoundException")
    public void getOwnerByName_nonExisting_shouldThrowNotFoundException() {
        assertThrows(NotFoundException.class,
            () -> ownerDao.getOwnerByName("aaabbbccc"));
    }

    @Test
    @DisplayName("Finding owner by name should return the correct owner")
    public void findingOwnerByName_shouldReturnCorrectOwnerName() {
        ownerDao.create(new Owner("Money Boy The Pineapple Fruit Dude"));
        String expected = "Money Boy The Pineapple Fruit Dude";
        String got = ownerDao.getOwnerByName("Money Boy The Pineapple Fruit Dude").getName();
        assertEquals(expected, got);
    }

    @Test
    @DisplayName("Finding owner by name should return the correct owner")
    public void CreatingNewOwner_shouldReturnCorrectOwnerName() {
        Owner owner = new Owner("Owner");
        String expected = "Owner";
        String got = ownerDao.create(owner).getName();
        assertEquals(expected, got);
    }

    @Test
    @DisplayName("Finding owner by non-existing name should throw NotFoundException")
    public void getOwnerByName_moreThanOneOwnerWithSameName_shouldThrowPersistenceException() {
        Owner owner1 = new Owner("Owner");
        Owner owner2 = new Owner("Owner");
        ownerDao.create(owner1);
        ownerDao.create(owner2);
        assertThrows(PersistenceException.class,
            () -> ownerDao.getOwnerByName("Owner"));
    }
}
