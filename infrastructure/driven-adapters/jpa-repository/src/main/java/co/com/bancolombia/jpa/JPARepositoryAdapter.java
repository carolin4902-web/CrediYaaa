package co.com.bancolombia.jpa;

import co.com.bancolombia.jpa.entity.UserEntity;
import co.com.bancolombia.jpa.helper.AdapterOperations;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JPARepositoryAdapter
        extends AdapterOperations<User, UserEntity, Long, JPARepository>
        implements UserRepository {

    public JPARepositoryAdapter(JPARepository repository, ObjectMapper mapper) {
        super(repository, mapper, entity -> mapper.map(entity, User.class));
    }

    @Override
    public boolean existsByEmail(String correoElectronico) {
        return repository.existsByCorreoElectronico(correoElectronico);
    }

}