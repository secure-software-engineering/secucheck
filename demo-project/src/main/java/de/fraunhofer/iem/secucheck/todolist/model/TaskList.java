package de.fraunhofer.iem.secucheck.todolist.model;

import java.util.ArrayList;

import javax.validation.Valid;

public class TaskList {

	@Valid
	private ArrayList<Task> taskList = new ArrayList<Task>();
	
	public TaskList() {}
	
	public TaskList(ArrayList<Task> taskList) {
		this.taskList = taskList;
	}

	public ArrayList<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(ArrayList<Task> taskList) {
		this.taskList = taskList;
	}
}