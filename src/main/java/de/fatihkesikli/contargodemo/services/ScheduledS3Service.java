package de.fatihkesikli.contargodemo.services;

import de.fatihkesikli.contargodemo.dtos.AuftragDto;
import de.fatihkesikli.contargodemo.dtos.KundeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class ScheduledS3Service {

	private final CsvService csvService;
	private final FileUploadService fileUploadService;
	private final AuftragService auftragService;
	private final KundeService kundeService;

	private final Logger logger = LoggerFactory.getLogger(ScheduledS3Service.class);

	@Value("${config.aws.s3.bucket-name}")
	private String bucketName;

	@Autowired
	public ScheduledS3Service(CsvService csvService, FileUploadService fileUploadService, AuftragService auftragService, KundeService kundeService) {
		this.csvService = csvService;
		this.fileUploadService = fileUploadService;
		this.auftragService = auftragService;
		this.kundeService = kundeService;
	}
	@Scheduled(fixedDelay = 1000 * 60 * 60 * 3) //3 Stunden
	public void createCsvsAndUpload() {

		logger.info("Starting Upload");
		fileUploadService.checkAndCreateBucket();

		final Map<String, List<AuftragDto>> auftragMap = csvService.auftragToCsv();
		final Map<String, List<KundeDto>> kundeMap = csvService.kundeToCsv();

		//TODO - evtl. gemeinsames Interface implementieren, um doppelte Implementierung zu abstrahieren

		auftragMap.forEach((fileName, auftragDtos) -> {
			fileUploadService.uploadAndDeleteFile(new File(fileName), bucketName);
			//wir haben uns in der Map zu jeder CSV die jeweiligen Business Objects gemerkt,
			//daher können wir alle Aufträge unter dem filename (key) als synced markieren
			auftragDtos.forEach(auftragService::markAuftragAsSynced);
		});

		kundeMap.forEach((fileName, kundeDtos) -> {
			fileUploadService.uploadAndDeleteFile(new File(fileName), bucketName);
			//gleiches wie oben bei Kunde
			kundeDtos.forEach(kundeService::markKundeAsSynced);
		});
		logger.info("Done uploading");
	}
}
