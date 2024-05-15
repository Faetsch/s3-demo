package de.fatihkesikli.contargodemo.services;

import de.fatihkesikli.contargodemo.daos.AuftragRepository;
import de.fatihkesikli.contargodemo.dtos.AuftragDto;
import de.fatihkesikli.contargodemo.entities.Auftrag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuftragService {

	private final AuftragRepository auftragRepository;

	@Autowired
	public AuftragService(AuftragRepository auftragRepository) {
		this.auftragRepository = auftragRepository;
	}

	public AuftragDto auftragToDto(Auftrag auftrag) {
		return new AuftragDto(String.valueOf(auftrag.getId()), auftrag.getArtikelnummer(), String.valueOf(auftrag.getKunde().getId()), auftrag.getKunde().getLand());
	}

	public List<AuftragDto> findAllAuftraegeNotSynced() {
		return auftragRepository.findBySynced(false).stream()
				.map(this::auftragToDto)
				.collect(Collectors.toList());
	}

	public void markAuftragAsSynced(AuftragDto auftragDto) {
		String auftragId = auftragDto.getAuftragId();
		Optional<Auftrag> optAuftrag = auftragRepository.findById(auftragId);
		optAuftrag.ifPresent(auftrag -> {
			auftrag.setSynced(true);
			auftragRepository.save(auftrag);
		});
	}
}
