package com.yzyfiles.api;


import com.yzyfiles.api.files.UploadedFile;
import com.yzyfiles.api.repository.UploadedFileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class Graphite implements CommandLineRunner {

	private final UploadedFileRepository fileRepository;

	public Graphite(UploadedFileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(Graphite.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// fetch all customers
		System.out.println("Files found with findAll():");
		System.out.println("-------------------------------");

		for (UploadedFile files : fileRepository.findAll()) {
			System.out.println(files);
		}

		System.out.println();
	}
}
