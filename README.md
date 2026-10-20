# Mehanična delavnica

Šolska aplikacija za mehanično delavnico: delovni nalogi, rezervni deli, računi in evidenca strank. Združuje vaje RIRS, PTS in SOA. Vloge: mehanik, stranka, vodja delavnice.

## Sestava

| Mapa | Vsebina |
|---|---|
| `frontend/delavnica-web` | Angular odjemalec |
| `backend/delavnica-api` | Spring Boot REST API (JWT, Swagger) |
| `db` | Dockerfile za CockroachDB |
| `docker-compose.yml` | zažene bazo in backend |
| `docs` | zasnova (01–04), uporabniške zgodbe in wireframes (05), arhitekturne odločitve (`adr/`) |

## Zagon

1. Baza in backend:
   ```bash
   docker compose up --build
   ```
   - API in Swagger: http://localhost:8080 (Swagger UI na `/swagger-ui/index.html`)
   - Konzola baze: http://localhost:8081
   - SQL: vrata 26257
2. Frontend:
   ```bash
   cd frontend/delavnica-web
   npm install
   ng serve
   ```
   Aplikacija je na http://localhost:4200.

## Dokumentacija

- [01 Zasnova rešitve](docs/01-zasnova-resitve.md)
- [02 Funkcionalnosti in obseg](docs/02-funkcionalnosti-in-obseg.md)
- [03 Podatkovni model](docs/03-podatkovni-model.md)
- [04 Arhitektura in tehnološki sklad](docs/04-arhitektura-in-tehnoloski-sklad.md)
- [05 Uporabniške zgodbe](docs/05-uporabniske-zgodbe.md)
- [05 Wireframes](docs/05-wireframes.md)
- [Arhitekturne odločitve (ADR)](docs/adr/)
