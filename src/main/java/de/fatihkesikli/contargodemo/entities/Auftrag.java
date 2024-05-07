package de.fatihkesikli.contargodemo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "auftraege")
public class Auftrag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "auftragid", nullable = false)
    private String id;

    @Column(name = "artikelnummer", nullable = false)
    private String artikelnummer;

    @Column(name = "created", nullable = false)
    private String created;

    @Column(name = "lastchange", nullable = false)
	private String lastchange;

    @ManyToOne
    @JoinColumn(name = "kundeid", nullable = false)
    private Kunde kunde;

    @Column(name = "synced")
    private boolean synced;
}
