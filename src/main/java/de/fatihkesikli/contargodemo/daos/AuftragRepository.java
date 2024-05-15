package de.fatihkesikli.contargodemo.daos;

import de.fatihkesikli.contargodemo.entities.Auftrag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuftragRepository extends JpaRepository<Auftrag, String> {

	List<Auftrag> findBySynced(boolean synced);
}
