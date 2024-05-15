# DB to CSV to S3 Bucket Demo - Contargo

## Prerequisites
- checkout project
- Run ```docker compose up -d```. Startet Postgres DB und localstack Instanz.
- Run migration.sql
- Run ```mvn spring-boot:run```

## Anforderung
- Es sollte ein DB Dump von Kunden und Aufträgen alle 3h pro Land gruppiert in eine CSV geschrieben und in ein S3 Bucket hochgeladen werden
- Die CSV Dateien sollten keine Kopfzeilen beinhalten
- Der DB Bestand ist beim Hochladen nicht zu löschen
- Es sollten nur ausgewählte Felder in die jeweiligen CSVs geschrieben werden
- Die CSV Dateien sollten im Namen den Namen der Tabelle, das Land und das aktuelle Datum beinhalten

## Lösung
- Es wurde eine Spring Boot Anwendung mit einem @Scheduled Service gebaut
- Zum Schreiben der CSVs wurde OpenCSV verwendet
- Es wird localstack zum Simulieren eines S3 Buckets verwendet
