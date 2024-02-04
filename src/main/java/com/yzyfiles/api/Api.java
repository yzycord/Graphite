package com.yzyfiles.api;


import com.yzyfiles.api.files.UploadedFile;
import com.yzyfiles.api.repository.UploadedFileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class Api implements CommandLineRunner {

	private final UploadedFileRepository fileRepository;

	public Api(UploadedFileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(Api.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		fileRepository.deleteAll();

		// save a couple of customers
		fileRepository.save(new UploadedFile("abc123", "Grail.mp3"));
		fileRepository.save(new UploadedFile("def456", "Digital Nas.wav"));

		// fetch all customers
		System.out.println("Files found with findAll():");
		System.out.println("-------------------------------");

		for (UploadedFile files : fileRepository.findAll()) {
			System.out.println(files);
		}

		System.out.println();
	}
}
