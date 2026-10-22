# Spring Boot za backend

## Kontekst in problem

Backend mora ponujati REST API, dostop do relacijske baze, preverjanje vlog in dokumentacijo API-ja. Ogrodje mora biti zrelo in dolgoročno vzdrževano, zanj pa mora biti na voljo tudi dovolj znanja.

## Obravnavane možnosti

* Spring Boot (Java)
* Quarkus
* Node.js (Express)

## Odločitev

Izbrali smo možnost: Spring Boot, torej Web, Data JPA, Security in springdoc, ker je to najbolj razširjeno ogrodje v Javi z dolgoročno podporo. Že vsebuje podporo za REST, bazo, varnost in OpenAPI, usklajeno pa je tudi z izbiro Jave kot jezika backenda.

## Posledice

* Dobro, ker je ekosistem zrel, dokumentacije in znanja pa je veliko. Varnost, dostop do podatkov in dokumentacija API-ja so rešeni z uveljavljenimi knjižnicami.
* Slabo, ker porabi več pomnilnika in se zaganja počasneje kot lažja ogrodja. Tudi docker slike so zaradi tega večje. Veliko pravil je skritih v anotacijah in samodejni konfiguraciji. 
