package de.fatihkesikli.contargodemo;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import de.fatihkesikli.contargodemo.dtos.AuftragDto;
import de.fatihkesikli.contargodemo.dtos.KundeDto;
import de.fatihkesikli.contargodemo.services.CsvService;
import de.fatihkesikli.contargodemo.services.FileUploadService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
public class ContargodemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContargodemoApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(AmazonS3 amazonS3, CsvService csvService, FileUploadService fileUploadService) {
//		return runner -> {
////			File f = new File("C:\\SpringProjects\\contargodemo\\src\\main\\resources\\bingpot.txt");
////			System.out.println(f.getPath());
////			fileUploadService.uploadFile(f, "contargo-bucket");
////			if(!amazonS3.doesBucketExistV2("contargo-bucket")) {
////				amazonS3.createBucket("contargo-bucket");
////			} else {
////				amazonS3.deleteBucket("contargo-bucket");
////			}
////			amazonS3.putObject(new PutObjectRequest("contargo-bucket", f.getName(), f));
////			Map<String, List<AuftragDto>> auftragMap = csvService.auftragToCsv();
////			Map<String, List<KundeDto>> kundeMap = csvService.kundeToCsv();
////			Arrays.stream(new File("csvs").listFiles()).filter(file -> !file.isDirectory()).forEach(f -> fileUploadService.uploadFile(f, "contargo-bucket"));
////			fileUploadService.uploadFile(f, "contargo-bucket");
////			amazonS3.createBucket("contargo-bucket");
////			amazonS3.putObject(new PutObjectRequest("contargo-bucket", f.getName(), f));
//
//		};
//	}

}
