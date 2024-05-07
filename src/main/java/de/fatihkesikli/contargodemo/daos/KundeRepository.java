package de.fatihkesikli.contargodemo.daos;

import de.fatihkesikli.contargodemo.entities.Kunde;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KundeRepository extends JpaRepository<Kunde, Long> {
}
