# Preprost JWT brez osveževanja

* Status: sprejeto
* Datum: 20.10.2026

## Kontekst in problem

Aplikacija ima tri vloge, ki vidijo različne podatke (stranka samo svoje). Potrebujemo prijavo in preverjanje vloge pri vsaki zahtevi. API naj bo brez stanja, da se lahko izvaja v več instancah.

## Obravnavane alternative

* Seja na strežniku (piškotek)
* JWT z osveževalnimi žetoni
* En JWT z ID-jem in vlogo

## Odločitev

Izbrana alternativa: en JWT z ID-jem uporabnika in vlogo (gesla so shranjena kot BCrypt), ker strežnik ne hrani sej in je API brez stanja, vloga pa je takoj na voljo za preverjanje pravic. Osveževalni žetoni v tej fazi ne prinašajo toliko, da bi upravičili dodatno zapletenost.

## Posledice

* Dobro: API je brez stanja in ga je mogoče skalirati; ni skupne shrambe sej.
* Slabo: žetona ni mogoče preklicati pred iztekom (npr. ob odvzemu vloge ali kraji); po izteku se mora uporabnik znova prijaviti. Če to postane težava, uvedemo kratkotrajne žetone z osveževanjem in nov ADR.
