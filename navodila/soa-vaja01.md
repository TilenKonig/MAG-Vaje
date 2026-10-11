# SOA Vaja01: Docker in REST storitev

Primer "Igralec" iz originalnih navodil ignoriramo, uporabimo entiteto iz mehanične delavnice.

## Naloga

0. Lokalno namesti Docker.
1. Izberi eno entiteto ("tabelo"). Pripravi tabelo v bazi in strežnik z REST CRUD (Create, Read, Update, Delete).
2. (Samostojno) Pripravi 2 Dockerfile: 1 za bazo, 1 za storitev, ki se povezuje na bazo. Slike naj vsebujejo samo nujno (lightweight).
3. (Samostojno) Zgradi sliki in naloži na javni Docker Hub. Verzioniraj z oznakami (tags).
4. (Samostojno) Docker Compose datoteka: vsaka slika v ločen zabojnik. Zagotovi:
   - trajno hrambo podatkov,
   - skupno omrežje obeh storitev,
   - odprta vrata strežnika na gostitelju.
5. Poženi compose in pripravi demonstracijo vseh CRUD operacij na en način:
   - testni odjemalec, ali
   - kolekcija Postman klicev, ali
   - Swagger (OpenAPI) specifikacija in odjemalec v storitvi.

## Oddaja

- obe Dockerfile datoteki,
- TXT datoteka s povezavo do slik na Docker Hub,
- docker-compose.yml,
- izbrana demonstracija storitve.
