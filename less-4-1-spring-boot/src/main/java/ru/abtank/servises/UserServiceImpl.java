package ru.abtank.servises;


import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.abtank.dto.UserDto;
import ru.abtank.persist.entities.User;
import ru.abtank.persist.repositories.RoleRepository;
import ru.abtank.persist.repositories.UserRepository;
import ru.abtank.persist.repositories.UserSpecification;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserDto> findAll(Map<String, String> params, PageRequest pageRequest) {
        Specification<User> spec = UserSpecification.trueLiteral();
        if (params.containsKey("check_login_filter") && !params.get("login_filter").isBlank()) {
            spec = spec.and(UserSpecification.loginContains(params.get("login_filter")));
        }
        if (params.containsKey("check_email_filter") && !params.get("email_filter").isBlank()) {
            spec = spec.and(UserSpecification.emailContains(params.get("email_filter")));
        }
        return userRepository.findAll(spec, pageRequest).map(UserDto::new);
    }
    @Override
    public Optional<User> findById (Integer id){
        return userRepository.findById(id);
    }
    @Override
    public Optional<User> findByLogin (String login){
        return userRepository.findByLogin(login);
    }
    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserDto::new).collect(Collectors.toList());
    }
    @Override
    public List<User> findAll(Specification spec){
        return userRepository.findAll(spec);
    }
    @Override
    public User save(User user){
        return userRepository.save(user);
    }
    @Override
    public void deleteById(Integer id){
        userRepository.deleteById(id);
    }
}
