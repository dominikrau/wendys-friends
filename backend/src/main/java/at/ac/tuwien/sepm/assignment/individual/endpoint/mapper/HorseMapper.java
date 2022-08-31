package at.ac.tuwien.sepm.assignment.individual.endpoint.mapper;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HorseMapper {

    private OwnerMapper ownerMapper;

    @Autowired
    HorseMapper(OwnerMapper ownerMapper) {
        this.ownerMapper = ownerMapper;
    }

    public HorseDto entityToDto(Horse horse) {
        return new HorseDto(horse.getId(), horse.getCreatedAt(), horse.getUpdatedAt(), horse.getName(),
            horse.getRating(), horse.getBirthDate(), horse.getDescription(), horse.getOwnerId(),
            ownerMapper.entityToDto(horse.getOwner()), horse.getBreed(), horse.getPicture());
    }

    public Horse dtoToEntity(HorseDto horseDto) {
        return new Horse(horseDto.getId(), horseDto.getCreatedAt(), horseDto.getUpdatedAt(), horseDto.getName(),
            horseDto.getRating(), horseDto.getBirthDate(), horseDto.getDescription(), horseDto.getOwnerId(),
            ownerMapper.dtoToEntity(horseDto.getOwnerDto()), horseDto.getBreed(), horseDto.getPicture());
    }

    public List<HorseDto> entityToDto(List<Horse> horses) {
        List<HorseDto> horseDtoList = new ArrayList();
        if(!horses.isEmpty()) {
            for (int i = 0; i < horses.size(); i++) {
                horseDtoList.add(new HorseDto(horses.get(i).getId(), horses.get(i).getCreatedAt(), horses.get(i).getUpdatedAt(), horses.get(i).getName(),
                    horses.get(i).getRating(), horses.get(i).getBirthDate(), horses.get(i).getDescription(), horses.get(i).getOwnerId(),
                    ownerMapper.entityToDto(horses.get(i).getOwner()), horses.get(i).getBreed(), horses.get(i).getPicture()));
            }
        }
        return horseDtoList;
    }
}
