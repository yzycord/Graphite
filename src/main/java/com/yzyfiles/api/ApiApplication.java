package com.yzyfiles.api;


import com.yzyfiles.api.files.UploadedFile;
import com.yzyfiles.api.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;

@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

	@Autowired
	private FileRepository fileRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		fileRepository.deleteAll();

		// save a couple of customers
		fileRepository.save(new UploadedFile("Alice", "Smith"));
		fileRepository.save(new UploadedFile("Bob", "Smith"));

		// fetch all customers
		System.out.println("Files found with findAll():");
		System.out.println("-------------------------------");
		for (UploadedFile files : fileRepository.findAll()) {
			System.out.println(files);
		}

		System.out.println();
	}
}
