package de.fatihkesikli.contargodemo.daos;

import de.fatihkesikli.contargodemo.entities.Kunde;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KundeRepository extends JpaRepository<Kunde, Long> {
	List<Kunde> findBySynced(boolean synced);
}
