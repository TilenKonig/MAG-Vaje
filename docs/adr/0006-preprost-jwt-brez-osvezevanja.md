# Preprost JWT brez osveževanja

## Kontekst in problem

Aplikacija ima tri vloge, ki vidijo različne podatke, stranka na primer samo svoje. Potrebujemo prijavo in preverjanje vloge pri vsaki zahtevi. API naj bo brez stanja, da ga lahko poganjamo v več instancah.

## Obravnavane možnosti

* Seja na strežniku (piškotek)
* JWT z osveževalnimi žetoni
* En JWT z ID-jem in vlogo

## Odločitev

Izbrana možnost: "En JWT z ID-jem in vlogo", pri čemer so gesla shranjena kot BCrypt. Strežnik tako ne hrani sej, API ostane brez stanja, vloga pa je takoj na voljo za preverjanje pravic. Osveževalni žetoni v tej fazi ne prinesejo toliko, da bi upravičili dodatno zapletenost. Aplikacija ne bo tako kompleksna, število uporabnikov pa tudi ne bo veliko. Tudi nabor funkcionalnosti ni tako velik, da bi zahteval specifične pravice za vsakega uporabnika.

## Posledice

* Dobro, ker je API brez stanja in ga je mogoče skalirati, skupne shrambe sej pa ne potrebujemo.
* Slabo, ker žetona ni mogoče preklicati pred iztekom, na primer ob odvzemu vloge ali kraji, po izteku pa se mora uporabnik znova prijaviti. Če bo to postalo težava, uvedemo kratkotrajne žetone z osveževanjem in napišemo nov ADR.
