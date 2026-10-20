# Hibernate ustvari tabele (v MVP fazi)

* Status: sprejeto
* Datum: 20.10.2026

## Kontekst in problem

Shemo baze je treba ustvariti in vzdrževati. Podatkovni model je v začetni fazi še v spreminjanju, produkcijskih podatkov, ki bi jih migracija morala ohraniti, pa še ni.

## Obravnavane alternative

* Ločene migracije (Flyway ali Liquibase)
* Ročni SQL skripti
* Hibernate ddl-auto=update

## Odločitev

Izbrana alternativa: Hibernate ddl-auto=update, ker shema vedno sledi entitetam v kodi, dokler se model še spreminja, in ne potrebuje dodatnih orodij. To je začasno, dokler ne dopolnim MVP-ja do konca.

## Posledice

* Dobro: najmanj dela in konfiguracije; ni neusklajenosti med entitetami in shemo.
* Slabo: ni zgodovine sprememb sheme, brisanja stolpcev in preimenovanj ne izvede, spremembe so neobvladljive v večjem okolju. Ko model ni več v spreminjanju ali ko pridejo prvi pravi podatki, uvedemo Flyway in odločitev zamenjamo
