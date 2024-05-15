package de.fatihkesikli.contargodemo.services;

import com.opencsv.CSVWriter;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import de.fatihkesikli.contargodemo.dtos.AuftragDto;
import de.fatihkesikli.contargodemo.dtos.KundeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CsvService {

	private static final String CSV_FOLDER = "csvs";
	private static final String KUNDE_TABLE_NAME = "kunde";
	private static final String AUFTRAEGE_TABLE_NAME = "auftraege";

	private final AuftragService auftragService;
	private final KundeService kundeService;

	private final Logger logger = LoggerFactory.getLogger(CsvService.class);

	@Autowired
	public CsvService(AuftragService auftragService, KundeService kundeService) {
		this.auftragService = auftragService;
		this.kundeService = kundeService;
	}

	//TODO - evtl Kunde und Auftrag gemeinsames interface "S3Syncable" teilen um doppelte Logik wie hier zu sparen
	public Map<String, List<KundeDto>> kundeToCsv() {
		final Map<String, List<KundeDto>> kundenByLandMap = kundeService.findAllKundenNotSynced()
				.stream()
				.collect(Collectors.groupingBy(KundeDto::getLand));

		return dtosToCSv(kundenByLandMap, KundeDto.class, KUNDE_TABLE_NAME);
	}

	public Map<String, List<AuftragDto>> auftragToCsv() {
		final Map<String, List<AuftragDto>> auftraegeByLandMap = auftragService.findAllAuftraegeNotSynced().
				stream()
				.collect(Collectors.groupingBy(AuftragDto::getLand));

		return dtosToCSv(auftraegeByLandMap, AuftragDto.class, AUFTRAEGE_TABLE_NAME);
	}

	private <T> Map<String, List<T>> dtosToCSv(final Map<String, List<T>> landToDtoMap, Class clazz, String tableName) {
		HashMap<String, List<T>> fileToDtoMap = new HashMap<>();
		HeaderColumnNameMappingStrategy<T> headerStrategy = new NoHeaderMappingStrategy<>();
		headerStrategy.setType(clazz);

		File csvFolder = new File(CSV_FOLDER);
		if(!csvFolder.exists()) {
			csvFolder.mkdir();
		}

		landToDtoMap.forEach((land, dtos) -> {
			//CSVs sollen laut Anforderung nach Land gruppiert sein
			String fileName = generateFileName(land, tableName);
			File f = new File(fileName);
			try (FileWriter writer = new FileWriter(f)){
				new StatefulBeanToCsvBuilder<T>(writer)
						.withSeparator(',')
						.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
						.withMappingStrategy(headerStrategy)
						.build()
						.write(dtos);
				writer.flush();
				//wir wollen uns später die Kunden merken, die in einer CSV file gelistet sind, damit
				//wir wissen, welche Kunden wir in der DB als synchronisiert markieren müssen
				fileToDtoMap.put(fileName, dtos);
			} catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
				logger.error(e.getMessage());
			}
		});
		return fileToDtoMap;
	}

	private String generateFileName(String land, String table) {
		return CSV_FOLDER + "/"
				+ table + "_"
				+ land + "_"
				+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"))
				+ ".csv";
	}

	//workaround um "keine Kopfzeile" Anforderung zu erfüllen. OpenCSV erstellt ansonsten immer eine Kopfzeile in der CSV
	private static class NoHeaderMappingStrategy<T> extends HeaderColumnNameMappingStrategy<T> {
		@Override
		public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {
			super.generateHeader(bean);
			return new String[]{};
		}
	}
}
