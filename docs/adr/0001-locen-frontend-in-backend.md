# Ločen frontend in backend

* Status: sprejeto
* Datum: 20.10.2026

## Kontekst in problem

Aplikacijo uporabljajo tri vloge (mehanik, stranka, vodja) prek brskalnika. Vodja bi lahko kasneje želel tudi mobilni odjemalec ali povezavo z drugimi sistemi (npr. dobavitelji delov). Odločiti se je treba, ali bo aplikacija ena enota ali bosta odjemalec in strežnik ločena.

## Obravnavane alternative

* Enotna aplikacija s strežniško izrisanimi stranmi (npr. Thymeleaf)
* Ločen Angular frontend in Spring Boot REST API

## Odločitev

Izbrana alternativa: ločen frontend in REST API, ker poslovna pravila (stanja nalogov, odpis zaloge, obračun) ostanejo na enem mestu, API pa lahko brez sprememb uporabi tudi drug odjemalec. Frontend in backend se lahko tudi nameščata in skalirata neodvisno.

## Posledice

* Dobro: ena pot do podatkov za vse odjemalce; varnost in poslovna pravila so samo na strežniku; neodvisni izdaji frontenda in backenda.
* Slabo: dva projekta za vzdrževanje; potrebna sta nastavitev CORS in prenos žetona pri vsaki zahtevi.
