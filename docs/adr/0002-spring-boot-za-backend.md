# Spring Boot za backend

* Status: sprejeto
* Datum: 20.10.2026

## Kontekst in problem

Backend mora ponuditi REST API, dostop do relacijske baze, preverjanje vlog in dokumentacijo API-ja. Ogrodje mora biti zrelo, dolgoročno vzdrževano in zanj mora biti na voljo dovolj ljudi.

## Obravnavane alternative

* Spring Boot (Java)
* Quarkus
* Node.js (Express)

## Odločitev

Izbrana alternativa: Spring Boot (Web, Data JPA, Security, springdoc), ker je to najbolj razširjeno ogrodje v Javi z dolgoročno podporo, ima vgrajeno podporo za REST, bazo, varnost in OpenAPI ter je že usklajeno z izbiro Jave kot jezika backenda.

## Posledice

* Dobro: zrel ekosistem, veliko dokumentacije in znanja na trgu; varnost, dostop do podatkov in dokumentacija API-ja so rešeni z uveljavljenimi knjižnicami.
* Slabo: večja poraba pomnilnika in počasnejši zagon kot pri lažjih ogrodjih; veliko pravil se skriva v anotacijah in samodejni konfiguraciji.
