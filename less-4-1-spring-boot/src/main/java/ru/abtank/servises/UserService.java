package ru.abtank.servises;


import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.abtank.persist.entities.User;
import ru.abtank.persist.repositories.UserRepository;
import ru.abtank.persist.repositories.UserSpecification;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@NoArgsConstructor
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> findAll(Map<String, String> params, PageRequest pageRequest){
        Specification<User> spec = UserSpecification.trueLiteral();
        if (params.containsKey("check_login_filter") && !params.get("login_filter").isBlank()) {
            spec = spec.and(UserSpecification.loginContains(params.get("login_filter")));
        }
        if (params.containsKey("check_email_filter") && !params.get("email_filter").isBlank()) {
            spec = spec.and(UserSpecification.emailContains(params.get("email_filter")));
        }
        return userRepository.findAll(spec,pageRequest);
    }

    public Optional<User> findById (Integer id){
        return userRepository.findById(id);
    }
    public Optional<User> findByLogin (String login){
        return userRepository.findByLogin(login);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }
    public List<User> findAll(Specification spec){
        return userRepository.findAll(spec);
    }

    public void save(User user){
        userRepository.save(user);
    }
    public void deleteById(Integer id){
        userRepository.deleteById(id);
    }
}
