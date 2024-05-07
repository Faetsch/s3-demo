package de.fatihkesikli.contargodemo.dtos;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuftragDto {

	@CsvBindByPosition(position = 0)
	private String auftragId;

	@CsvBindByPosition(position = 1)
	private String artikelnummer;

	@CsvBindByPosition(position = 2)
	private String kundeId;

	@CsvIgnore
	private String land;
}
