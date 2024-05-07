package de.fatihkesikli.contargodemo.entities;

import com.opencsv.bean.CsvBindAndJoinByName;
import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "kunde")
public class Kunde {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kundenid", nullable = false)
    private Long id;

    @Column(name = "vorname", nullable = false)
    private String vorname;

    @Column(name = "nachname", nullable = false)
    private String nachname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "strasse", nullable = false)
    private String strasse;

    @Column(name = "strassenzusatz", nullable = false)
    private String strassenzusatz;

    @Column(name = "ort", nullable = false)
    private String ort;

    @Column(name = "land", nullable = false)
    private String land;

    @Column(name = "plz", nullable = false)
    private String plz;

    @Column(name = "firmenname", nullable = false)
    private String firmenname;

    @OneToMany(mappedBy = "kunde")
    private Set<Auftrag> auftraege;

    @Column(name = "synced")
    private boolean synced;
}
