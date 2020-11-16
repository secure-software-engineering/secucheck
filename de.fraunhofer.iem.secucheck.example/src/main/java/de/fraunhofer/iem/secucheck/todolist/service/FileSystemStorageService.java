package de.fraunhofer.iem.secucheck.todolist.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import de.fraunhofer.iem.secucheck.todolist.model.Task;

@Service
public class FileSystemStorageService implements DirectoryStorageService {
	
	private final String location;
	
    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
    	this.location = properties.getLocation();
    }

    @Override
    @PostConstruct
    public void init() throws StorageException {
        try {
        	Path rootLocation = Paths.get(location);
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }
    }

    @Override
    public String store(MultipartFile file, Task task,
    		String userDirectory) throws StorageException {
    	String fileName = task.getFileName();
        try {
        	Path rootLocation = Paths.get(location + File.separator + userDirectory);
        	Files.createDirectories(rootLocation);
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + fileName);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, rootLocation.resolve(fileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + fileName, e);
        }

        return fileName;
    }

    @Override
    public Stream<Path> loadAll(String userDirectory) throws StorageException {
        try {
        	Path rootLocation = Paths.get(location + File.separator + userDirectory);
        	Files.createDirectories(rootLocation);
            return Files.walk(rootLocation, 1)
                    .filter(path -> !path.equals(rootLocation))
                    .map(rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(Task task, String userDirectory) {
    	String fileName = task.getFileName();
        return Paths.get(location + File.separator + userDirectory).resolve(fileName);
    }

    @Override
    public Resource loadAsResource(Task task, 
    		String userDirectory) throws FileNotFoundException {
    	String fileName = task.getFileName();
        try {
            Path file = load(task, userDirectory);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new FileNotFoundException(
                        "Could not read file: " + fileName);
            }
        }
        catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not read file: " + fileName, e);
        }
    }
       
    @Override
    public void deleteAll(String userDirectory) {
    	Path rootLocation = Paths.get(location + File.separator + userDirectory);
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

	@Override
	public int getFileSizeOnSystem(Task task, String directory) {
		String fileName = task.getFileName();
		try {
				
			String command = "wc -c < " + System.getProperty("user.dir") 
				+ (location.startsWith(".") ? location.substring(1) : location)  
						+ File.separator + directory +
						File.separator + "'" +fileName + "'";
			
			ProcessBuilder processBuilder = new ProcessBuilder();
			processBuilder.command("bash", "-c", command);
			Process process = processBuilder.start();
			StringBuilder output = new StringBuilder();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line);
			}
			
			process.waitFor();
            process.getErrorStream().close();
            process.getInputStream().close();
            process.getOutputStream().close();
				
            return Integer.parseInt(output.toString());
            
		} catch (Exception e) {
        	return 0; 	
        }
	}
}
