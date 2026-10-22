# Ločen frontend in backend

## Kontekst in problem

Aplikacijo uporabljajo tri vloge, to so mehanik, stranka in vodja, in sicer prek brskalnika. Vodja bi lahko kasneje želel še mobilnega odjemalca ali povezavo z drugimi sistemi, na primer z dobavitelji delov. Odločiti se moramo, ali bo aplikacija ena celota ali pa bosta odjemalec in strežnik ločena.

## Obravnavane možnosti

* Enotna aplikacija s strežniško izrisanimi stranmi, na primer s Thymeleafom
* Ločen Angular frontend in Spring Boot REST API

## Odločitev

Odločili smo se za možnost: Ločen Angular frontend in Spring Boot REST API, ker tako poslovna pravila, kot so stanja nalogov, odpis zaloge in obračun, ostanejo na enem mestu. API lahko brez sprememb uporabi tudi drug odjemalec, frontend in backend pa lahko nameščamo in razvijamo neodvisno.

## Posledice

* Dobro, ker imajo vsi odjemalci eno pot do podatkov, varnost in poslovna pravila pa so samo na strežniku. Frontend in backend lahko izdajamo neodvisno.
* Slabo, ker moramo vzdrževati dva projekta, poleg tega je treba nastaviti CORS in pri vsaki zahtevi poslati žeton.
