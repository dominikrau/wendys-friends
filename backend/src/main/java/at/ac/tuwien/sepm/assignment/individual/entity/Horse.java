package at.ac.tuwien.sepm.assignment.individual.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Horse extends BaseEntity {

    private Owner owner;
    private Long ownerId;
    private String name;
    private int rating;
    private LocalDate birthDate;
    private String description;     // optional
    private String breed;
    private byte[] picture;

    public Horse() {
    }

    public Horse(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, String name, int rating, LocalDate birthDate, String description, Long ownerId,
                 Owner owner, String breed, byte[] picture) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.rating = rating;
        this.birthDate = birthDate;
        this.description = description;
        this.ownerId = ownerId;
        this.owner = owner;
        this.breed = breed;
        this.picture = picture;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Horse{" +
            "owner=" + owner +
            ", ownerId=" + ownerId +
            ", name='" + name + '\'' +
            ", rating=" + rating +
            ", birthDate=" + birthDate +
            ", description='" + description + '\'' +
            ", breed='" + breed + '\'' +
            '}';
    }
}
