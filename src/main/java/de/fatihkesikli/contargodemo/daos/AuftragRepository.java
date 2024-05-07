package de.fatihkesikli.contargodemo.daos;

import de.fatihkesikli.contargodemo.entities.Auftrag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuftragRepository extends JpaRepository<Auftrag, String> {
}
