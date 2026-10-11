# RIRS Vaja01: zasnova rešitve (dokumentacija)

Izberi idejo spletne rešitve in pripravi kratko, jasno, tehnično dovolj konkretno zasnovo.
Dokumentacija mora biti razumljiva razvijalcu, ki pri zasnovi ni sodeloval.

## 1. docs/01-zasnova-resitve.md

Osnovna ideja rešitve. Vključi:
- ime rešitve,
- namen in problem, ki ga rešitev naslavlja,
- ciljne uporabnike,
- kratek opis delovanja.

Opis konkreten: kdo uporablja, v kakšni situaciji, za kaj.

## 2. docs/02-funkcionalnosti-in-obseg.md

- **Uporabniške vloge:** vsaj 3. Za vsako: kaj lahko počne in katerih pravic nima.
- **Predvidene funkcionalnosti:** 10-15. Vsebinske (uporaba rešitve), ne samo prijava/registracija/pregled/dodajanje/urejanje/brisanje.
- **MVP:** 5-7 ključnih za prvo delujočo različico, označene z MVP. Vsaj 3 domensko specifične (ne samo CRUD).
- **Kasnejše nadgradnje:** vsaj 5.
- **Out of scope:** po alinejah, kaj ne bo implementirano / kaj projekt ni.

## 3. docs/03-podatkovni-model.md

- vsaj 3 povezane entitete,
- glavna polja vsake entitete,
- povezave med entitetami,
- osnutek modela (seznam, tabela, diagram ali Mermaid), odvisno od baze.

## 4. docs/04-arhitektura-in-tehnoloski-sklad.md

**Arhitektura:** glavni deli sistema, komunikacija med deli, podatkovna baza, avtentikacija/upravljanje uporabnikov, lokalni zagon, preprost diagram.

**Tehnološki sklad:** frontend, backend, podatkovna baza, avtentikacija, lokalni razvoj, morebitni drugi deli.

**Arhitekturne odločitve:** vse pomembne izbire na kratko utemeljene.

## Minimalne zahteve za projekt

- spletna rešitev, ne zgolj statična stran,
- vsaj 3 uporabniške vloge,
- vsaj 3 povezane entitete,
- shranjevanje podatkov v bazo,
- osnovna avtentikacija oz. upravljanje uporabnikov,
- vsaj 1 netrivialen delovni proces,
- 10-15 predvidenih funkcionalnosti,
- 5-7 funkcionalnosti za prvo različico,
- vsaj 5 kasnejših nadgradenj,
- vsaj 3 funkcionalnosti izven obsega,
- možnost nadaljnjega razvoja.
