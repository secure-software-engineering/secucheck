package de.fraunhofer.iem.secucheck.todolist.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("storageService")
public interface DirectoryStorageService
{	
	void init() throws StorageException;
	
	String store(MultipartFile file, String fileName, String directory) throws StorageException;
	
	Stream<Path> loadAll(String directory) throws StorageException;
	Path load(String filename, String directory);
	Resource loadAsResource(String filename, String directory) throws FileNotFoundException;
	
	void deleteAll(String directory) throws StorageException;

	int getFileSizeOnSystem(String fileName, String directory);
}