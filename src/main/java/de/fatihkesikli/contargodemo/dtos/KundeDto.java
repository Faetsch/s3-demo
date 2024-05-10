package de.fatihkesikli.contargodemo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KundeDto {

	private String firma;
	private String strasse;
	private String strassenzusatz;
	private String ort;
	private String land;
	private String plz;
	private String vorname;
	private String nachname;
	private String kundenId;
}
