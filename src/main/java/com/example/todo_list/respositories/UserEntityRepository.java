package com.example.todo_list.respositories;

import com.example.todo_list.models.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    boolean existsUserEntityByUsername(String username);
}
