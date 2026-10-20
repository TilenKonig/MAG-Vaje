# UUID kot identifikatorji

* Status: sprejeto
* Datum: 20.10.2026

## Kontekst in problem

Vsaka entiteta potrebuje enolični ID. Ker ID potuje med bazo, backendom in Angularjem, mora biti varen v vseh treh. Zaporedni ID-ji v URL-jih (npr. /api/racuni/17) pa olajšajo ugibanje tujih zapisov. Za generiranje enoličnih ID-jev že obstajajo preverjene rešitve, zato lastne logike ne potrebujemo.

## Obravnavane alternative

* Samodejno povečevana števila (Long)
* UUID, ki ga ustvari obstoječa knjižnica (Hibernate GenerationType.UUID, java.util.UUID)
* Lasten generator ID-jev

## Odločitev

Izbrana alternativa: UUID, ki ga ustvari Hibernate z @GeneratedValue(strategy = GenerationType.UUID) v skupnem razredu BaseEntity. V Javi uporabljamo standardni java.util.UUID, baza ga hrani kot tip UUID, v Angularju pa je to navaden niz. Lastnega generatorja ne pišemo, ker obstoječe rešitve to že pokrijejo.

## Posledice

* Dobro: ID-ji so v JavaScriptu nizi, zato jih ni treba pretvarjati; tujih zapisov ni mogoče uganiti; ID lahko nastane na backendu brez zaporedja v bazi; ena definicija v BaseEntity velja za vse entitete.
* Slabo: daljši ID-ji, manj berljivi pri ročnem pregledovanju baze in v dnevnikih; večji indeksi kot pri številskih ID-jih.
