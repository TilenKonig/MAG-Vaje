# CockroachDB kot baza

## Kontekst in problem

Podatki delavnice, torej nalogi, računi in zaloga, so relacijski in zahtevajo transakcije. Baza mora biti zanesljiva in omogočati rast in kasnejši prenos v oblak.

## Obravnavane možnosti

* PostgreSQL
* MySQL
* CockroachDB

## Odločitev

Izbrali smo možnost: CockroachDB, ker je relacijska baza z ACID transakcijami, vgrajeno replikacijo in horizontalnim skaliranjem. Uporablja protokol PostgreSQL, zato v kodi ni posebnosti in bi bila zamenjava baze še vedno možna. Omogoča tudi kasnejši prenos v CockroachDB Cloud. P.S: Rabim jo za eno drugo vajo :) 

## Posledice

* Dobro, ker imamo transakcije in dosledne podatke. Baze ne bo treba menjati, če bomo želeli rast ali replikacijo, uporabimo pa lahko navaden JDBC gonilnik za PostgreSQL.
* Slabo, ker porabi več virov kot PostgreSQL pri enakem obsegu podatkov. Skupnost je manjša in nekatere funkcije PostgreSQL niso podprte. Porabi pa tudi kar dosti prostora na disku. 
