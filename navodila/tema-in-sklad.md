# Tema in tehnološki sklad

## Tema

Mehanična delavnica. Delavci odpirajo delovne naloge, naročajo dele, izdajajo račune in vodijo evidenco strank.

## Uporabniške vloge

- **Mehanik**
- **Stranka**
- **Vodja delavnice** (administrator)

## Tehnološki sklad (predlog uporabnika)

- Backend: Java
- Frontend: Angular
- Baza: CockroachDB, lokalno (Docker)
- Orodja: Docker, Git

## Dogovorjene odločitve

- Vaje se združijo v en projekt. SOA vaja uporabi entiteto iz delavnice namesto "Igralca".
- CockroachDB zadostuje PTS (NewSQL, +1 točka) in SOA (lokalno v Dockerju).
- Backend: Spring Boot. Avtentikacija: čim preprostejši JWT. Entiteta za SOA CRUD: DelovniNalog.
- ID-ji so UUID (veliki številski ID-ji se v JavaScriptu pokvarijo). Tabele ustvari Hibernate (`ddl-auto=update`), baza `delavnica` (ustvari jo `COCKROACH_DATABASE` v docker-compose.yml).
- Dokumentacija RIRS je napisana v `docs/`.
