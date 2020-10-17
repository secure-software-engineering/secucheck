package de.fraunhofer.iem.secucheck.todolist.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private String userId;
    private boolean complete;
    private String description;
    private String fileName;
    private int fileSizeOnSystem;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date deadline;
    
    public Task() {}

    public Task(String name, String userId, String description, String fileName) 
    {
        this.name = name;
        this.userId = userId;
        this.description = description;
        this.fileName = fileName;
        this.complete = false;
        this.fileSizeOnSystem = 0;
    }

    @Override
    public String toString() {
        return String.format(
                "Task[id=%d, fileName ='%s' ,description='%s', name='%s', complete='%b']",
                id, fileName,description, name, complete);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        return;
    }

     public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
        return;
    }

    public boolean isComplete() {
        return complete;
    }
    
    public void setComplete(boolean complete) {
        this.complete = complete;
        return;
    }

    /**
     * @return the userID
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param userID the userID to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDeadline(Date deadline) {
    	this.deadline=deadline;
    }
    
    public Date getDeadline() {
    	return deadline;
    }
    
    public void setFileSizeOnSystem(int fileSizeOnSystem){
    	this.fileSizeOnSystem = fileSizeOnSystem;
    }
    
    public int getFileSizeOnSystem(){
    	return this.fileSizeOnSystem;
    }
}