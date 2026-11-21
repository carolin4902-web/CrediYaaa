package co.com.bancolombia.jpa.helper;

import co.com.bancolombia.jpa.JPARepository;
import co.com.bancolombia.jpa.JPARepositoryAdapter;
import co.com.bancolombia.jpa.entity.UserEntity;
import co.com.bancolombia.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AdapterOperationsTest {

    @Mock
    private JPARepository repository;

    @Mock
    private ObjectMapper objectMapper;

    private JPARepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        adapter = new JPARepositoryAdapter(repository, objectMapper);
    }

    @Test
    void testSave() {
        User user = User.builder()
                .nombres("Fabian")
                .apellidos("Hernandez")
                .build();

        UserEntity entity = new UserEntity();

        when(objectMapper.map(user, UserEntity.class)).thenReturn(entity);
        when(objectMapper.map(entity, User.class)).thenReturn(user);
        when(repository.save(entity)).thenReturn(entity);

        User result = adapter.save(user);

        assertEquals(user, result);
    }

    @Test
    void testFindById() {
        UserEntity entity = new UserEntity();

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(objectMapper.map(entity, User.class)).thenReturn(null);

        User result = adapter.findById(1L);

        assertEquals(null, result);
    }

    @Test
    void testFindAll() {
        List<UserEntity> entities = List.of(new UserEntity(), new UserEntity());

        when(repository.findAll()).thenReturn(entities);
        when(objectMapper.map(any(UserEntity.class), any())).thenReturn(null);

        List<User> result = adapter.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void testFindByExample() {
        User user = User.builder()
                .nombres("Fabian")
                .build();

        UserEntity entity = new UserEntity();
        List<UserEntity> entities = List.of(entity, entity);

        when(objectMapper.map(user, UserEntity.class)).thenReturn(entity);
        when(repository.findAll(any(Example.class))).thenReturn(entities);
        when(objectMapper.map(any(UserEntity.class), any())).thenReturn(null);

        List<User> result = adapter.findByExample(user);

        assertEquals(2, result.size());
    }
}