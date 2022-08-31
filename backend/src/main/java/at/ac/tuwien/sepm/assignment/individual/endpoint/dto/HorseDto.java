package at.ac.tuwien.sepm.assignment.individual.endpoint.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class HorseDto extends BaseDto {

    private OwnerDto ownerDto;
    private Long ownerId;
    private String name;
    private int rating;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;
    private String description;
    private String breed;
    private byte[] picture;

    public HorseDto() {
    }

    public HorseDto(Long id, LocalDateTime createdAt, LocalDateTime updatedAt,
                    String name, int rating, LocalDate birthDate, String description, Long ownerId, OwnerDto ownerDto, String breed, byte[] picture) {
        super(id, createdAt, updatedAt);
        this.name = name;
        this.rating = rating;
        this.birthDate = birthDate;
        this.description = description;
        this.ownerId = ownerId;
        this.ownerDto = ownerDto;
        this.breed = breed;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public OwnerDto getOwnerDto() {
        return ownerDto;
    }

    public void setOwnerDto(OwnerDto ownerDto) {
        this.ownerDto = ownerDto;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HorseDto horseDto = (HorseDto) o;
        return rating == horseDto.rating &&
            Objects.equals(ownerId, horseDto.ownerId) &&
            name.equals(horseDto.name) &&
            Objects.equals(description, horseDto.description) &&
            birthDate.equals(horseDto.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ownerId, name, rating, description, birthDate);
    }

    @Override
    public String toString() {
        return "HorseDto{" +
            "ownerDto=" + ownerDto +
            ", ownerId=" + ownerId +
            ", name='" + name + '\'' +
            ", rating=" + rating +
            ", birthDate=" + birthDate +
            ", description='" + description + '\'' +
            ", breed='" + breed + '\'' +
            '}';
    }
}
