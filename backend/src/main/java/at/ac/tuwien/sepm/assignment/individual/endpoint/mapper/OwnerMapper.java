package at.ac.tuwien.sepm.assignment.individual.endpoint.mapper;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class OwnerMapper {


    public OwnerDto entityToDto(Owner owner) {
        return new OwnerDto(owner.getId(), owner.getName(), owner.getCreatedAt(), owner.getUpdatedAt());
    }

    public List<OwnerDto> entityToDto(List<Owner> owners) {
        List<OwnerDto> ownerDtoList = new ArrayList();
        if(!owners.isEmpty()) {
            for (int i = 0; i < owners.size(); i++) {
                ownerDtoList.add(new OwnerDto(owners.get(i).getId(), owners.get(i).getName(), owners.get(i).getCreatedAt(), owners.get(i).getUpdatedAt()));
            }
        }
        return ownerDtoList;
    }

    public Owner dtoToEntity(OwnerDto ownerDto) {
        return new Owner(ownerDto.getId(), ownerDto.getName(), ownerDto.getCreatedAt(), ownerDto.getUpdatedAt());
    }

}
