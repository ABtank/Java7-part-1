package ru.abtank.servises;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.abtank.dto.ModeDto;
import ru.abtank.persist.entities.Mode;
import ru.abtank.persist.repositories.ModeRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
public class ModeServiceImpl implements ModeService {
    private ModeRepository modeRepository;

    @Autowired
    public void setModeRepository(ModeRepository modeRepository) {
        this.modeRepository = modeRepository;
    }

    @Override
    public Set<ModeDto> findAllDto() {
        return modeRepository.findAll().stream().map(ModeDto::new).collect(Collectors.toSet());
    }

    @Override
    public Optional<ModeDto> findByIdDto(Integer id) {
        return modeRepository.findById(id).map(ModeDto::new);
    }

    @Override
    public List<Mode> findAll() {
        return modeRepository.findAll();
    }

    @Override
    public Optional<Mode> findById(Integer id) {
        return modeRepository.findById(id);
    }

    @Override
    public Optional<Mode> findByName(String name) {
        return modeRepository.findByName(name);
    }

    @Override
    public void deleteById(Integer id) {
        modeRepository.deleteById(id);
    }

    @Override
    public List<Mode> findAll(Specification<Mode> spec) {
        return modeRepository.findAll(spec);
    }

    @Override
    public void save(Mode mode) {
        modeRepository.save(mode);
    }

    @Override
    public Mode saveOrUpdate(Mode mode) {
        return modeRepository.save(mode);
    }
}
