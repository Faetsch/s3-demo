package de.fatihkesikli.contargodemo.services;

import de.fatihkesikli.contargodemo.daos.KundeRepository;
import de.fatihkesikli.contargodemo.dtos.KundeDto;
import de.fatihkesikli.contargodemo.entities.Kunde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KundeService {

	private KundeRepository kundeRepository;

	@Autowired
	private KundeService(KundeRepository kundeRepository) {
		this.kundeRepository = kundeRepository;
	}

	private KundeDto kundeToDto(Kunde kunde) {
		return new KundeDto(kunde.getFirmenname(), kunde.getStrasse(), kunde.getStrassenzusatz(), kunde.getOrt(), kunde.getLand(), kunde.getPlz(),
				kunde.getVorname(), kunde.getNachname(), String.valueOf(kunde.getId()));
	}

	public List<KundeDto> findAllKunden() {
		return kundeRepository.findAll().stream().map(this::kundeToDto).collect(Collectors.toList());
	}
}
