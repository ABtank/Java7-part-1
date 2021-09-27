package ru.abtank.servises;

import org.springframework.data.jpa.domain.Specification;
import ru.abtank.dto.RoleDto;
import ru.abtank.persist.entities.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleService {
    Set<RoleDto> findAllDto();
    Optional<RoleDto> findByIdDto(Integer id);
    Optional<Role> findByName(String name);
    List<Role> findAll();
    Optional<Role> findById(Integer id);
    void deleteById(Integer id);
    List<Role>findAll(Specification<Role> spec);
    void save(Role role);
    void saveOrUpdate(RoleDto roleDto);
}
