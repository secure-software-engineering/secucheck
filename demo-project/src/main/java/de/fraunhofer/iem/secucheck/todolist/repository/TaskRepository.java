package de.fraunhofer.iem.secucheck.todolist.repository;

import org.springframework.data.repository.CrudRepository;

import de.fraunhofer.iem.secucheck.todolist.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

}