package de.fatihkesikli.contargodemo.services;

import de.fatihkesikli.contargodemo.daos.AuftragRepository;
import de.fatihkesikli.contargodemo.dtos.AuftragDto;
import de.fatihkesikli.contargodemo.entities.Auftrag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuftragService {

	private AuftragRepository auftragRepository;

	@Autowired
	public AuftragService(AuftragRepository auftragRepository) {
		this.auftragRepository = auftragRepository;
	}

	public AuftragDto auftragToDto(Auftrag auftrag) {
		return new AuftragDto(auftrag.getId(), auftrag.getArtikelnummer(), String.valueOf(auftrag.getKunde().getId()), auftrag.getKunde().getLand());
	}

	public List<AuftragDto> findAllAuftraege() {
		return auftragRepository.findAll()
				.stream()
				.map(this::auftragToDto)
				.collect(Collectors.toList());
	}
}