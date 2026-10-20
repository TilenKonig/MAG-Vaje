# Baza in backend v Dockerju, frontend z ng serve

* Status: sprejeto
* Datum: 20.10.2026

## Kontekst in problem

Okolje mora biti ponovljivo, da vsak razvijalec in vsak strežnik dobi enako različico baze in backenda. Hkrati frontend med razvojem potrebuje hitro osvežitev ob spremembah.

## Obravnavane alternative

* Vse tri dele v Docker Compose
* Baza in backend v Dockerju, frontend lokalno z ng serve

## Odločitev

Izbrana alternativa: baza in backend v docker-compose.yml (slike objavljene v registru), frontend z ng serve, ker zaledje dobi ponovljivo okolje brez lokalne namestitve Jave in baze, frontend pa ostane hiter za razvoj. Pred namestitvijo bo frontend zgrajen v statične datoteke in postrežen z običajnim spletnim strežnikom, kar bomo zapisali z novim ADR-jem.

## Posledice

* Dobro: en ukaz (docker compose up) zažene zaledje; verzije slik so določene, zato je okolje ponovljivo.
* Slabo: za polni zagon sta potrebna dva ukaza, ker frontend še ni pripravljen da bi ga deployal
