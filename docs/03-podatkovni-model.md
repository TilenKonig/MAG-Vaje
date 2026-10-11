# 03 Podatkovni model

Baza: CockroachDB (relacijski model). Vsi ID-ji so **UUID**.

## Entitete

**Uporabnik**: id, email, geslo (BCrypt), ime, priimek, telefon, vloga (`MEHANIK` | `STRANKA` | `VODJA`)
Stranke so uporabniki z vlogo `STRANKA`, zato posebna tabela za stranke ni potrebna.

**Vozilo**: id, lastnik_id, znamka, model, letnik, registrska

**Del**: id, sifra, naziv, cena, zaloga, min_zaloga

**DelovniNalog**: id, vozilo_id, opis (težava), opis_dela, stanje (`ODPRT` | `V_DELU` | `ZAKLJUCEN` | `OBRACUNAN`), ure_dela, datum_odprtja, datum_zaprtja

**PostavkaNaloga**: id, nalog_id, del_id, kolicina, cena (cena dela ob dodajanju)

**Racun**: id, nalog_id, znesek, datum, placan

**Narocilo**: id, del_id, kolicina, stanje (`NAROCENO` | `PREJETO`), datum

## Povezave

- Uporabnik (stranka) 1 : N Vozilo
- Vozilo 1 : N DelovniNalog
- DelovniNalog 1 : N PostavkaNaloga
- Del 1 : N PostavkaNaloga
- DelovniNalog 1 : 0..1 Racun
- Del 1 : N Narocilo

## Diagram

```mermaid
erDiagram
    UPORABNIK ||--o{ VOZILO : "je lastnik"
    VOZILO ||--o{ DELOVNI_NALOG : "ima"
    DELOVNI_NALOG ||--o{ POSTAVKA_NALOGA : "vsebuje"
    DEL ||--o{ POSTAVKA_NALOGA : "se porabi v"
    DELOVNI_NALOG ||--o| RACUN : "se obračuna z"
    DEL ||--o{ NAROCILO : "se naroča"

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
    NAROCILO {
        uuid id
        uuid del_id
        int kolicina
        string stanje
        date datum
    }
```

## Opombe

- Račun = vsota (količina × cena) vseh postavk + ure dela × urna postavka. Urna postavka je konstanta v backendu. Ure dela vpiše vodja ob izdaji računa.
- Cena je shranjena tudi v postavki, da poznejša sprememba cene dela ne spremeni starih računov.
- Naročilo je simulirano: ob kliku nastane zapis `NAROCENO`, po nekaj sekundah preide v `PREJETO` in zaloga dela se poveča.
- Za SOA vajo (ena entiteta z REST CRUD) uporabimo **DelovniNalog**.
