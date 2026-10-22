# Baza in backend v Dockerju, frontend z ng serve

## Kontekst in problem

Okolje mora biti ponovljivo, da vsak razvijalec in vsak strežnik dobi enako različico baze in backenda. Hkrati frontend med razvojem potrebuje hitro osvežitev ob spremembah.

## Obravnavane možnosti

* Vsi trije deli v Docker Compose
* Baza in backend v Dockerju, frontend lokalno z ng serve

## Odločitev

Izbrana možnost (začasno): "Baza in backend v Dockerju, frontend lokalno z ng serve". Baza in backend tečeta prek docker-compose.yml s slikami iz registra, zato dobimo ponovljivo okolje brez lokalne namestitve Jave in baze. Frontend ostane hiter za razvoj. Pred namestitvijo bomo frontend zgradili v statične datoteke in ga servali z običajnim spletnim strežnikom (nginx ali podobno), kar bomo zapisali v novem ADR-ju.

## Posledice

* Dobro, ker en ukaz (docker compose up) zažene zaledje, verzije slik pa so določene, zato je okolje ponovljivo.
* Slabo, ker za poln zagon potrebujemo dva ukaza. Frontend še ni pripravljen na namestitev.
