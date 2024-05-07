package de.fatihkesikli.contargodemo.services;

import com.opencsv.CSVWriter;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import de.fatihkesikli.contargodemo.dtos.AuftragDto;
import de.fatihkesikli.contargodemo.dtos.KundeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CsvService {

	private final AuftragService auftragService;
	private final KundeService kundeService;

	@Autowired
	public CsvService(AuftragService auftragService, KundeService kundeService) {
		this.auftragService = auftragService;
		this.kundeService = kundeService;
	}

	public Map<String, List<KundeDto>> kundeToCsv() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

		Map<String, List<KundeDto>> fileNameToKundeDtoMap = new HashMap<String, List<KundeDto>>();
		List<KundeDto> allKunden = kundeService.findAllKunden();

		Map<String, List<KundeDto>> kundenByLandMap = allKunden.stream().collect(Collectors.groupingBy(KundeDto::getLand));

		var mappingStrategy = new CustomCSVWriterStrategy<KundeDto>();
		mappingStrategy.setType(KundeDto.class);


		kundenByLandMap.forEach((land, kundeDtos) -> {
			try {
				String fileName = "csvs/" + "kunde_" + land + "_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				File f = new File(fileName);
				Writer writer = new FileWriter(f);
				new StatefulBeanToCsvBuilder<KundeDto>(writer)
						.withSeparator(',')
						.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
						.withMappingStrategy(mappingStrategy)
						.build()
						.write(kundeDtos);
				writer.flush();
				fileNameToKundeDtoMap.put(fileName, kundeDtos);
			} catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
				System.out.println(e);
			}

		});

		return fileNameToKundeDtoMap;
	}

	public Map<String, List<AuftragDto>> auftragToCsv() throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

		Map<String, List<AuftragDto>> fileNameToAuftragDtosMap = new HashMap<String, List<AuftragDto>>();
		Map<String, List<AuftragDto>> collect = auftragService.findAllAuftraege().stream().collect(Collectors.groupingBy(AuftragDto::getLand));

		var mappingStrategy = new CustomCSVWriterStrategy<AuftragDto>();
		mappingStrategy.setType(AuftragDto.class);

		collect.forEach((land, auftragdtos) -> {
			try {
				Writer writer = null;
				String fileName = "csvs/" + "auftraege_" + land + "_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				writer = new FileWriter(fileName);
				new StatefulBeanToCsvBuilder<AuftragDto>(writer)
						.withSeparator(',')
						.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
						.withMappingStrategy(mappingStrategy)
						.build()
						.write(auftragdtos);
				writer.flush();
				fileNameToAuftragDtosMap.put(fileName, auftragdtos);
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (CsvRequiredFieldEmptyException e) {
				throw new RuntimeException(e);
			} catch (CsvDataTypeMismatchException e) {
				throw new RuntimeException(e);
			}

		});

		return fileNameToAuftragDtosMap;
	}

	public static class CustomCSVWriterStrategy<T> extends HeaderColumnNameMappingStrategy<T> {
		@Override
		public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {
			String[] header = super.generateHeader(bean);
			return new String[]{};
		}
	}
}
