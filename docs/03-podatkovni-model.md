# 03 Podatkovni model

Baza: CockroachDB

## Entitete

**Uporabnik**: id, email, geslo (BCrypt), ime, priimek, telefon, vloga (`MEHANIK` | `STRANKA` | `VODJA`).

**Vozilo**: id, lastnik_id, znamka, model, letnik, registrska

**Del**: id, sifra, naziv, cena, zaloga, min_zaloga

**DelovniNalog**: id, vozilo_id, mehanik_id (uporabnik z vlogo `MEHANIK`), opis (težava), opis_dela, stanje (`ODPRT` | `V_DELU` | `ZAKLJUCEN` | `OBRACUNAN`), ure_dela, datum_odprtja, datum_zaprtja

**PostavkaNaloga**: id, nalog_id, del_id, kolicina, cena (cena dela ob dodajanju)

**Racun**: id, nalog_id, znesek, datum, placan


## Povezave

- Uporabnik (stranka) 1 : N Vozilo
- Vozilo 1 : N DelovniNalog
- Uporabnik (mehanik) 1 : N DelovniNalog
- DelovniNalog 1 : N PostavkaNaloga
- Del 1 : N PostavkaNaloga
- DelovniNalog 1 : 0..1 Racun
- Del 1 : N Narocilo

## Diagram

```mermaid
erDiagram
    UPORABNIK ||--o{ VOZILO : "je lastnik"
    VOZILO ||--o{ DELOVNI_NALOG : "ima"
    UPORABNIK ||--o{ DELOVNI_NALOG : "je dodeljen mehaniku"
    DELOVNI_NALOG ||--o{ POSTAVKA_NALOGA : "vsebuje"
    DEL ||--o{ POSTAVKA_NALOGA : "se porabi v"
    DELOVNI_NALOG ||--o| RACUN : "se obračuna z"

    UPORABNIK {
        uuid id
        string email
        string geslo
        string ime
        string priimek
        string telefon
        string vloga
    }
    VOZILO {
        uuid id
        uuid lastnik_id
        string znamka
        string model
        int letnik
        string registrska
    }
    DEL {
        uuid id
        string sifra
        string naziv
        decimal cena
        int zaloga
        int min_zaloga
    }
    DELOVNI_NALOG {
        uuid id
        uuid vozilo_id
        uuid mehanik_id
        string opis
        string opis_dela
        string stanje
        decimal ure_dela
        date datum_odprtja
        date datum_zaprtja
    }
    POSTAVKA_NALOGA {
        uuid id
        uuid nalog_id
        uuid del_id
        int kolicina
        decimal cena
    }
    RACUN {
        uuid id
        uuid nalog_id
        decimal znesek
        date datum
        boolean placan
    }

```



- Račun = vsota (količina × cena) vseh postavk + ure dela × urna postavka. Stranka z vsaj 4 prejšnjimi računi dobi 10 % popusta
- Cena je shranjena tudi v postavki, da poznejša sprememba cene dela ne spremeni starih računov.
- Naročilo je simulirano.