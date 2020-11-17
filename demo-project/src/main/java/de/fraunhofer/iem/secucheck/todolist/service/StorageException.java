package de.fraunhofer.iem.secucheck.todolist.service;

public class StorageException extends Exception {
	
	public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
