package de.fatihkesikli.contargodemo.services;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import de.fatihkesikli.contargodemo.dtos.AuftragDto;
import de.fatihkesikli.contargodemo.dtos.KundeDto;
import de.fatihkesikli.contargodemo.entities.Kunde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ScheduledS3Uploader {

	private CsvService csvService;
	private FileUploadService fileUploadService;
	private AuftragService auftragService;
	private KundeService kundeService;
	@Autowired
	public ScheduledS3Uploader(CsvService csvService, FileUploadService fileUploadService, AuftragService auftragService, KundeService kundeService) {
		this.csvService = csvService;
		this.fileUploadService = fileUploadService;
		this.auftragService = auftragService;
		this.kundeService = kundeService;
	}
	@Scheduled(fixedDelay = 1000 * 60 * 60 * 3)
	public void createCsvsAndUpload() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {

		System.out.println("UPLOADINGGGG");
		Map<String, List<AuftragDto>> auftragMap = csvService.auftragToCsv();
		auftragMap.forEach((fileName, auftragDtos) -> {
			File f = new File(fileName);
			fileUploadService.uploadFile(f, "contargo-bucket");
			auftragDtos.forEach(auftragDto -> auftragService.markAuftragAsSynced(auftragDto));
		});


		Map<String, List<KundeDto>> kundeMap = csvService.kundeToCsv();
		kundeMap.forEach((fileName, kundeDtos) -> {
			File f = new File(fileName);
			fileUploadService.uploadFile(f, "contargo-bucket");
			kundeDtos.forEach(kundeDto -> kundeService.markKundeAsSynced(kundeDto));
		});

	}
}
