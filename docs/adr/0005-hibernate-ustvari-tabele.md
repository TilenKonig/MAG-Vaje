# Hibernate ustvari tabele v fazi MVP

## Kontekst in problem

Shemo baze je treba ustvariti in vzdrževati. Podatkovni model se v začetni fazi še spreminja, produkcijskih podatkov, ki bi jih morala migracija ohraniti, pa še ni.

## Obravnavane možnosti

* Ločene migracije (Flyway ali Liquibase)
* Ročni SQL skripti
* Hibernate ddl-auto=update

## Odločitev

Izbrali smo možnost "Hibernate ddl-auto=update", ker shema vedno sledi entitetam v kodi, dokler se model še spreminja, in ne potrebujemo dodatnih orodij. Rešitev je začasna, dokler ne dokončamo MVP-ja.

## Posledice

* Dobro, ker je dela in konfiguracije najmanj, entitete in shema pa se ne razhajajo.
* Slabo, ker ni zgodovine sprememb sheme in ker Hibernate ne izvede brisanja stolpcev ter preimenovanj. V večjem okolju so spremembe neobvladljive. Ko se model preneha spreminjati ali ko pridejo prvi pravi podatki, uvedemo Flyway in odločitev zamenjamo.
