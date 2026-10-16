# 02 Funkcionalnosti in obseg

## Uporabniške vloge

| Vloga | Opis
|---|---|
| **Mehanik** | Pregleda samo naloge, dodeljene njemu, spremeni stanje naloga, vpiše opis opravljenega dela, doda dele iz kataloga na nalog. Ne vidi ali spreminja naloge drugih mehanikov, odpira naloge, ureja stranke in vozila, naroča dele, ureja katalog in zalogo, izdaja račune |
| **Stranka** | Vidi samo svoja vozila, naloge in račune, odda zahtevek za servis 
| **Vodja delavnice** | Vse, kar mehanik, ter vodi evidenco strank in vozil, odpira naloge, naroča dele, ureja katalog in zalogo, vpiše ure dela, izdaja račune (stranka jih vidi v aplikaciji) in jih označuje kot plačane, ustvarja uporabnike |

## Predvidene funkcionalnosti

| # | Funkcionalnost | MVP |
|---|---|---|
| F1 | Prijava z JWT in omejevanje dostopa glede na vlogo | MVP |
| F2 | Evidenca strank in njihovih vozil: dodajanje, iskanje, urejanje (vodja) | MVP |
| F3 | Odpiranje delovnega naloga za vozilo z opisom težave in dodelitev mehaniku (vodja) | MVP |
| F4 | Mehanik spreminja stanje naloga (odprt → v delu → zaključen) in vpiše opis opravljenega dela | MVP |
| F5 | Mehanik doda dele iz kataloga na nalog, zaloga se samodejno zmanjša, aplikacija zavrne dodajanje, če zaloge ni dovolj | MVP |
| F6 | Vodja izda račun iz zaključenega naloga (vsota delov in ur dela, možnost dodajanja popusta za redne stranke...), stranka ga vidi v aplikaciji | MVP |
| F7 | Stranka pregleduje svoja vozila, naloge in račune, prejema posodobitve statusa | MVP |
| F8 | Upravljanje kataloga delov in zaloge (vodja) | |
| F9 | Naročanje delov z enim klikom (vodja): dobava je similirana, traja nekaj sekund, nato se zaloga poveča | |
| F10 | Zgodovina servisov vozila in opomnik za servis: seznam vozil, ki že več kot 12 mesecev niso imela naloga | |
| F11 | Označitev računa kot plačanega (vodja) | |
| F12 | Predlog naročila: deli z zalogo pod minimalno se izračunajo skupaj z manjkajočo količino, vodja jih naroči z enim klikom (uporabi F9) | |
| F13 | Iskanje in filtriranje nalogov po stanju, registrski številki ali stranki | |
| F14 | Stranka odda zahtevek za servis, ki ga vodja spremeni v nalog | |
| F15 | Poročilo delavnice (vodja): prihodek po mesecih in 5 najpogosteje porabljenih delov | |

**Domensko specifične MVP funkcionalnosti:** F3, F4, F5, F6 (stanja naloga, odpis zaloge, obračun s popustom).

## Kasnejše nadgradnje

- izvoz računa v PDF,
- e-poštna obvestila stranki ob spremembi stanja naloga ali izdaji računa,
- samodejno naročanje delov pri dobaviteljih,
- samodejna dodelitev naloga najmanj zasedenemu mehaniku,
- pošiljanje "vabil" na servis in dodani pospusti
- prenos baze v oblak (CockroachDB Cloud).

## Out of scope

- plačilni sistem in spletno plačevanje,
- več poslovalnic ali več delavnic,
- upravljanje plač in delovnega časa mehanikov,
- diagnostika vozil in povezava z vozili (OBD),
- prikaz servisnih priročnikov
- mobilna aplikacija.
