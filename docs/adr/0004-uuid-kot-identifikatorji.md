# UUID kot identifikatorji

## Kontekst in problem

Vsaka entiteta potrebuje enoličen ID. Ker ID potuje med bazo, backendom in Angularjem, mora biti varen v vseh treh. Zaporedni ID-ji v URL-jih, na primer /api/racuni/17, pa olajšajo ugibanje tujih zapisov. Za ustvarjanje enoličnih ID-jev že obstajajo preverjene rešitve, zato lastne logike ne potrebujemo.

## Obravnavane možnosti

* Auto incrementanje števila (Long)
* UUID, ki ga ustvari obstoječa knjižnica (Hibernate GenerationType.UUID, java.util.UUID)
* Lasten generator ID-jev

## Odločitev

Izbrana možnost za ta projekt je UUID, ki ga ustvari obstoječa knjižnica, ker ga Hibernate ustvari z @GeneratedValue(strategy = GenerationType.UUID) v skupnem razredu BaseEntity. V Javi uporabljamo standardni java.util.UUID, baza ga hrani kot tip UUID, v Angularju pa je to navaden niz. Lastnega generatorja ne pišemo, ker obstoječe rešitve to že pokrijejo.

## Posledice

* Dobro, ker so ID-ji v JavaScriptu nizi in jih ni treba pretvarjati, tujih zapisov pa ni mogoče uganiti. ID lahko nastane na backendu, ne da bi ga določilo zaporedje v bazi, ena definicija v BaseEntity pa velja za vse entitete (TODO: odločit se, kaj dodat v base entity).
* Slabo, ker so ID-ji daljši in manj berljivi pri ročnem pregledovanju baze in dnevnikov. Indeksi so tudi večji kot pri številskih ID-jih.
