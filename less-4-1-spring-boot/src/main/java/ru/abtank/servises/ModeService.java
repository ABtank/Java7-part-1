package ru.abtank.servises;

import org.springframework.data.jpa.domain.Specification;
import ru.abtank.dto.ModeDto;
import ru.abtank.persist.entities.Mode;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ModeService {
    Set<ModeDto> findAllDto();
    Optional<ModeDto> findByIdDto(Integer id);
    List<Mode> findAll();
    Optional<Mode> findById(Integer id);
    Optional<Mode> findByName(String name);
    void deleteById(Integer id);
    List<Mode>findAll(Specification<Mode> spec);
    void save(Mode Mode);
    Mode saveOrUpdate(Mode Mode);
}
