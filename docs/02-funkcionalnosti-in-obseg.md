# 02 Funkcionalnosti in obseg

## Uporabniške vloge

| Vloga | Lahko | Ne more |
|---|---|---|
| **Mehanik** | Pregleda delovne naloge, spremeni stanje naloga, vpiše opis opravljenega dela, doda dele iz kataloga na nalog | Odpira naloge, ureja stranke in vozila, naroča dele, ureja katalog in zalogo, izdaja račune |
| **Stranka** | Vidi samo svoja vozila, naloge in račune, odda zahtevek za servis | Vidi podatke drugih strank, spreminja naloge, dele ali račune |
| **Vodja delavnice** | Vse, kar mehanik, ter vodi evidenco strank in vozil, odpira naloge, naroča dele, ureja katalog in zalogo, vpiše ure dela, izdaja račune (stranka jih vidi v aplikaciji) in jih označuje kot plačane, ustvarja uporabnike | / |

## Predvidene funkcionalnosti

| # | Funkcionalnost | MVP |
|---|---|---|
| F1 | Prijava z JWT in omejevanje dostopa glede na vlogo | MVP |
| F2 | Evidenca strank in njihovih vozil: dodajanje, iskanje, urejanje (vodja) | MVP |
| F3 | Odpiranje delovnega naloga za vozilo z opisom težave (vodja) | MVP |
| F4 | Mehanik spreminja stanje naloga (odprt → v delu → zaključen) in vpiše opis opravljenega dela | MVP |
| F5 | Mehanik doda dele iz kataloga na nalog, zaloga se samodejno zmanjša, aplikacija zavrne dodajanje, če zaloge ni dovolj | MVP |
| F6 | Vodja izda račun iz zaključenega naloga (vsota delov in ur dela), stranka ga vidi v aplikaciji | MVP |
| F7 | Stranka pregleduje svoja vozila, naloge in račune | MVP |
| F8 | Upravljanje kataloga delov in zaloge (vodja) | |
| F9 | Naročanje delov z enim klikom (vodja): simulirana dobava traja nekaj sekund, nato se zaloga poveča | |
| F10 | Zgodovina servisov posameznega vozila | |
| F11 | Označitev računa kot plačanega (vodja) | |
| F12 | Seznam delov z nizko zalogo | |
| F13 | Iskanje in filtriranje nalogov po stanju, registrski številki ali stranki | |
| F14 | Stranka odda zahtevek za servis, ki ga vodja spremeni v nalog | |

**Domensko specifične MVP funkcionalnosti:** F3, F4, F5, F6 (netrivialni delovni proces: stanja naloga, odpis zaloge, obračun).

## Kasnejše nadgradnje

- izvoz računa v PDF,
- e-poštna obvestila stranki ob spremembi stanja naloga ali izdaji računa,
- rezervacija terminov za servis,
- samodejno naročanje delov pri dobaviteljih,
- spletno plačevanje računov,
- dodeljevanje nalogov posameznim mehanikom,
- prenos baze v oblak (CockroachDB Cloud).

## Out of scope

- plačilni sistem in spletno plačevanje,
- davčno potrjevanje računov in knjigovodstvo,
- več poslovalnic ali več delavnic,
- upravljanje plač in delovnega časa mehanikov,
- diagnostika vozil in povezava z vozili (OBD),
- mobilna aplikacija.
