package de.fatihkesikli.contargodemo.dtos;

import com.opencsv.bean.CsvIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuftragDto {

	private String auftragId;
	private String artikelnummer;
	private String kundeId;
	@CsvIgnore
	private String land;
}
