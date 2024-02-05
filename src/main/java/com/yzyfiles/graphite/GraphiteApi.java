package com.yzyfiles.graphite;


import com.yzyfiles.graphite.files.UploadedFile;
import com.yzyfiles.graphite.repository.UploadedFileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class GraphiteApi implements CommandLineRunner {

	private final UploadedFileRepository fileRepository;

	public GraphiteApi(UploadedFileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(GraphiteApi.class, args);
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
