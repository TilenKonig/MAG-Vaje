# SharedVaja

Šolski projekt: ena aplikacija (mehanična delavnica), ki združuje zahteve iz treh vaj (RIRS, PTS, SOA).
Namen je demonstracija znanja pri predmetu, ne produkcijska koda.

## Pravila

- **Preprostost na prvem mestu.** Kar se da minimalna koda, brez "fancy" rešitev.
- **Berljivost in razumljivost** pred zmogljivostjo ali zvitostjo. Študent mora kodo znati razložiti.
- **Preprostost ne pomeni slabih praks.** Ločene plasti (controller → service → repository, v frontendu komponenta → API servis), brez poslovne logike v kontrolerjih in komponentah, ponavljajoča se koda gre v skupno osnovo (`CrudService`, `BaseEntity`), brez magičnih števil, standardna imena (`getAll`, `getById`).
- Brez nepotrebnih knjižnic, abstrakcij, konfiguracij in funkcionalnosti, ki jih vaje ne zahtevajo.
- Vsaka zahtevana funkcionalnost iz vaj mora biti jasno vidna in prikazana.
- Komentarji kratki, samo kjer razlaga pomaga razumevanju.
- Ponytail je aktiven.
- Najprej se dogovorimo za organizacijo in funkcionalnosti, šele nato pišemo kodo.

## Navodila (preberi samo, kar je potrebno za trenutno nalogo)

Vse je v mapi [navodila/](navodila/):

- [tema-in-sklad.md](navodila/tema-in-sklad.md): tema, vloge, tehnološki sklad, dogovorjene odločitve
- [rirs-vaja01.md](navodila/rirs-vaja01.md): dokumentacija zasnove (4 md dokumenti v `docs/`)
- [pts-vaja01.md](navodila/pts-vaja01.md): frontend, baza (CockroachDB), backend, 10.000 zapisov
- [soa-vaja01.md](navodila/soa-vaja01.md): REST CRUD, Dockerfile, Docker Hub, docker-compose, demonstracija

Nova vaja = nova datoteka v `navodila/` + ena vrstica zgoraj.
