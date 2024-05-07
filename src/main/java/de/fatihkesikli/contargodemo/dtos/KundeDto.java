package de.fatihkesikli.contargodemo.dtos;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KundeDto {

	@CsvBindByPosition(position = 0)
	private String firma;

	@CsvBindByPosition(position = 1)
	private String strasse;

	@CsvBindByPosition(position = 2)
	private String strassenzusatz;

	@CsvBindByPosition(position = 3)
	private String ort;

	@CsvBindByPosition(position = 4)
	private String land;

	@CsvBindByPosition(position = 5)
	private String plz;

	@CsvBindByPosition(position = 6)
	private String vorname;

	@CsvBindByPosition(position = 7)
	private String nachname;

	@CsvBindByPosition(position = 8)
	private String kundenId;
}
