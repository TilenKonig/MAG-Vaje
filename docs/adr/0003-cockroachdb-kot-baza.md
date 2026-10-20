# CockroachDB kot baza

* Status: sprejeto
* Datum: 20.10.2026

## Kontekst in problem

Podatki delavnice (nalogi, računi, zaloga) so relacijski in zahtevajo transakcije. Baza mora biti zanesljiva in omogočati rast (replikacija, kasnejši prenos v oblak).

## Obravnavane alternative

* PostgreSQL
* MySQL
* CockroachDB

## Odločitev

Izbrana alternativa: CockroachDB, ker je relacijska baza z ACID transakcijami, vgrajeno replikacijo in horizontalnim skaliranjem, hkrati pa uporablja protokol PostgreSQL, zato v kodi ni posebnosti in je zamenjava baze možna. Omogoča tudi kasnejši prenos v CockroachDB Cloud.

## Posledice

* Dobro: transakcije in dosledni podatki; možnost rasti in replikacije brez menjave baze; običajen JDBC gonilnik PostgreSQL.
* Slabo: večja poraba virov kot pri PostgreSQL za enak obseg podatkov; manjša skupnost; nekatere funkcije PostgreSQL niso podprte.
