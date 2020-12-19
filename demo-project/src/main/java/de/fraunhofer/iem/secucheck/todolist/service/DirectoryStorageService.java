package de.fraunhofer.iem.secucheck.todolist.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import de.fraunhofer.iem.secucheck.todolist.model.Task;

@Service("storageService")
public interface DirectoryStorageService
{	
	void init() throws StorageException;
	
	String store(MultipartFile file, Task task, String directory) throws StorageException;
	
	Stream<Path> loadAll(String directory) throws StorageException;
	Path load(Task task, String directory);
	Resource loadAsResource(Task task, String directory) throws FileNotFoundException;
	
	void deleteAll(String directory) throws StorageException;

	int getFileSizeOnSystem(Task task, String directory);
}