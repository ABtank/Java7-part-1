package ru.abtank.servises;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.abtank.controllers.NotFoundException;
import ru.abtank.dto.RoleDto;
import ru.abtank.persist.entities.Role;
import ru.abtank.persist.repositories.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<RoleDto> findAllDto() {
        return roleRepository.findAll().stream().map(RoleDto::new).collect(Collectors.toSet());
    }

    @Override
    public Optional<RoleDto> findByIdDto(Integer id) {
        return roleRepository.findById(id).map(RoleDto::new);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }

    @Override
    public List<Role> findAll(Specification<Role> spec) {
        return roleRepository.findAll(spec);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void saveOrUpdate(RoleDto roleDto) {
        Role role = (roleDto.getId() != null) ? roleRepository.findById(roleDto.getId())
                .orElseThrow(NotFoundException::new) : new Role();
        role.setName(roleDto.getName());
        roleRepository.save(role);
    }
}
