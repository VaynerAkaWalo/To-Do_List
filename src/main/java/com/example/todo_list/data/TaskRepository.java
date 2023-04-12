package com.example.todo_list.data;

import com.example.todo_list.models.Task;
import com.example.todo_list.models.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    public List<Task> getAllByUserEntity(UserEntity userEntity);
}
