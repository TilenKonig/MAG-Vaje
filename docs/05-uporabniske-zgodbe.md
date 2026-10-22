# 05 Uporabniške zgodbe

Zgodbe pokrivajo vse MVP funkcionalnosti (F1–F7) iz [02-funkcionalnosti-in-obseg.md](02-funkcionalnosti-in-obseg.md) ter F8 in F11.
Vloge: **Mehanik**, **Stranka**, **Vodja**.

## Pregled

| Oznaka | Funkcionalnost | Vloga | Naslov |
|---|---|---|---|
| UZ-01 | F1 | vsi | Prijava |
| UZ-02 | F1 | vsi | Omejitev dostopa glede na vlogo |
| UZ-03 | F2 | Vodja | Dodajanje stranke |
| UZ-04 | F2 | Vodja | Dodajanje vozila stranki |
| UZ-05 | F2 | Vodja | Iskanje vozila po registrski številki |
| UZ-06 | F3 | Vodja | Odpiranje delovnega naloga |
| UZ-07 | F4 | Mehanik | Sprememba stanja naloga |
| UZ-08 | F4 | Mehanik | Vpis opravljenega dela |
| UZ-09 | F5 | Mehanik | Dodajanje dela na nalog |
| UZ-10 | F5 | Mehanik | Zavrnitev dela brez zaloge |
| UZ-11 | F6 | Vodja | Izdaja računa |
| UZ-12 | F7 | Stranka | Pregled svojih vozil in nalogov |
| UZ-13 | F7 | Stranka | Pregled svojih računov |
| UZ-14 | F11 | Vodja | Označitev računa kot plačanega |
| UZ-15 | F8 | Vodja | Upravljanje kataloga delov |

---

## UZ-01 Prijava
**Funkcionalnost:** F1 · **Vloga:** Mehanik, Stranka, Vodja

Kot uporabnik želim, da se prijavim z e-pošto in geslom, da dostopam do svojih podatkov.

Acceptance criteria:
1. Ob pravilni e-pošti in geslu aplikacija prejme JWT žeton in prikaže začetno stran.
2. Ob napačni e-pošti ali geslu se prikaže sporočilo "Napačna e-pošta ali geslo", žeton se ne izda.
3. Gumb "Prijava" je onemogočen, dokler obe polji nista izpolnjeni.
4. Po odjavi se žeton izbriše in vsak naslednji klic API-ja brez prijave vrne 401.

## UZ-02 Omejitev dostopa glede na vlogo
**Funkcionalnost:** F1 · **Vloga:** Mehanik, Stranka, Vodja

Kot vodja delavnice želim, da vsaka vloga vidi in počne samo dovoljeno, da so podatki varni.

Acceptance criteria:
1. Meni stranke vsebuje samo vozila, naloge in račune; menija "Uporabniki" in "Deli" ni.
2. Mehanik ne vidi gumbov "Odpri nalog" in "Izdaj račun".
3. Klic API-ja, za katerega vloga nima pravice (npr. mehanik pošlje POST /api/nalogi), vrne 403.
4. Neprijavljen uporabnik, ki odpre zaščiteno stran, je preUZmerjen na prijavo.

## UZ-03 Dodajanje stranke
**Funkcionalnost:** F2 · **Vloga:** Vodja

Kot vodja želim dodati novo stranko, da ji lahko dodelim vozila in naloge.

Acceptance criteria:
1. Obrazec zahteva ime, priimek, e-pošto in geslo; telefon ni obvezen.
2. Po shranjevanju je stranka na seznamu uporabnikov z vlogo STRANKA.
3. E-pošta, ki že obstaja, je zavrnjena s sporočilom o napaki.
4. Geslo je v bazi shranjeno kot BCrypt zgoščena vrednost, ne kot čisto besedilo.

## UZ-04 Dodajanje vozila stranki
**Funkcionalnost:** F2 · **Vloga:** Vodja

Kot vodja želim stranki dodati vozilo, da lahko zanj odprem delovni nalog.

Acceptance criteria:
1. Obrazec zahteva lastnika (izbira iz strank), znamko, model, letnik in registrsko številko.
2. Po shranjevanju se vozilo pojavi na seznamu vozil in v pogledu lastnika.
3. Letnik mora biti celo število med 1900 in tekočim letom, drugače obrazec ne dovoli shranjevanja.
4. Vodja lahko vozilo uredi (PUT) in izbriše (DELETE).

## UZ-05 Iskanje vozila po registrski številki
**Funkcionalnost:** F2 · **Vloga:** Vodja

Kot vodja želim poiskati vozilo po registrski številki, da hitro najdem pravo vozilo in lastnika.

Acceptance criteria:
1. Vnos dela registrske številke (npr. "LJ 12") prikaže samo vozila, ki ta niz vsebujejo.
2. Iskanje ne razlikuje velikih in malih črk.
3. Vsaka vrstica rezultata prikazuje registrsko, znamko, model in ime lastnika.
4. Če ni zadetkov, se prikaže sporočilo "Ni vozil".

## UZ-06 Odpiranje delovnega naloga
**Funkcionalnost:** F3 · **Vloga:** Vodja

Kot vodja želim odpreti delovni nalog z opisom težave, da mehanik ve, kaj mora popraviti.

Acceptance criteria:
1. Obrazec zahteva izbiro vozila in opis težave (ne sme biti prazen).
2. Novi nalog dobi stanje ODPRT in današnji datum odprtja.
3. Nov nalog se takoj pojavi na seznamu nalogov pri vseh mehanikih in vodji.
4. Lastnik vozila nalog vidi na svojem seznamu.

## UZ-07 Sprememba stanja naloga
**Funkcionalnost:** F4 · **Vloga:** Mehanik

Kot mehanik želim spremeniti stanje naloga (ODPRT → V_DELU → ZAKLJUCEN), da vsi vedo, kako daleč je popravilo.

Acceptance criteria:
1. Mehanik lahko nastavi stanje ODPRT, V_DELU ali ZAKLJUCEN in ga shrani.
2. Novo stanje je takoj vidno na seznamu nalogov.
3. Mehanik ne more nastaviti stanja OBRACUNAN; to stanje nastane le z izdajo računa.
4. Nalog v stanju OBRACUNAN mehanik ne more več spreminjati (polja so samo za branje).

## UZ-08 Vpis opravljenega dela
**Funkcionalnost:** F4 · **Vloga:** Mehanik

Kot mehanik želim vpisati opis opravljenega dela in ure dela, da se lahko pravilno obračuna.

Acceptance criteria:
1. Na nalogu lahko vpišem besedilni opis opravljenega dela in število ur dela.
2. Ure dela ne smejo biti negativne; negativna vrednost je zavrnjena.
3. Po shranjevanju se opis in ure prikažejo na podrobnostih naloga.
4. Stranka opis in ure vidi, vendar jih ne more spreminjati.

## UZ-09 Dodajanje dela na nalog
**Funkcionalnost:** F5 · **Vloga:** Mehanik

Kot mehanik želim na nalog dodati rezervni del iz kataloga, da je poraba dokumentirana.

Acceptance criteria:
1. Izbiram lahko samo dele, ki obstajajo v katalogu, in vpišem količino večjo od 0.
2. Po dodajanju se v tabeli "Uporabljeni deli" prikažejo naziv dela, količina in cena.
3. Zaloga dela v katalogu se zmanjša za dodano količino.
4. V postavki je shranjena cena dela v času dodajanja; poznejša sprememba cene je ne spremeni.

## UZ-10 Zavrnitev dela brez zaloge
**Funkcionalnost:** F5 · **Vloga:** Mehanik

Kot mehanik želim, da me aplikacija opozori, če dela ni dovolj na zalogi, da ne vpišem dela, ki ga ni.

Acceptance criteria:
1. Če je zahtevana količina večja od zaloge, API vrne napako 400 s sporočilom o premajhni zalogi.
2. Pri zavrnjenem dodajanju se zaloga dela ne spremeni.
3. Pri zavrnjenem dodajanju se na nalogu ne UZtvari postavka.
4. Uporabniku se sporočilo o napaki prikaže v oknu, ne kot prazna stran.

## UZ-11 Izdaja računa
**Funkcionalnost:** F6 · **Vloga:** Vodja

Kot vodja želim iz zaključenega naloga izdati račun, da stranka dobi obračun dela.

Acceptance criteria:
1. Gumb "Izdaj račun" je viden samo pri nalogu v stanju ZAKLJUCEN.
2. Znesek je vsota (količina × cena) vseh postavk in ur dela, pomnoženih s ceno ure.
3. Po izdaji se nalog preklopi v stanje OBRACUNAN, račun ima današnji datum in je neplačan.
4. Za isti nalog ni mogoče izdati drugega računa.

## UZ-12 Pregled svojih vozil in nalogov
**Funkcionalnost:** F7 · **Vloga:** Stranka

Kot stranka želim videti svoja vozila in naloge, da vem, v kakšnem stanju je popravilo.

Acceptance criteria:
1. Seznam vozil vsebuje samo vozila, katerih lastnik sem jaz.
2. Seznam nalogov vsebuje samo naloge mojih vozil, s stanjem, opisom težave in datumom.
3. Klic podatkov druge stranke (npr. GET /api/vozila/{id} tujega vozila) ne vrne podatkov.
4. Seznama sta samo za branje; gumbov za dodajanje, urejanje in brisanje ni.

## UZ-13 Pregled svojih računov
**Funkcionalnost:** F7 · **Vloga:** Stranka

Kot stranka želim videti svoje račune, da vem, koliko moram plačati.

Acceptance criteria:
1. Seznam vsebuje samo račune mojih nalogov, z datumom, zneskom in oznako plačan/neplačan.
2. Račun drugega uporabnika mi API ne vrne.
3. Račun novega naloga se pojavi na seznamu takoj po izdaji, brez ponovne prijave.
4. Stranka računa ne more spremeniti ali izbrisati.

## UZ-14 Označitev računa kot plačanega
**Funkcionalnost:** F11 · **Vloga:** Vodja

Kot vodja želim račun označiti kot plačan, da imam pregled nad neporavnanimi računi.

Acceptance criteria:
1. Pri neplačanem računu je gumb "Označi kot plačan".
2. Po potrditvi je račun označen kot plačan, gumb izgine.
3. Stranka vidi spremenjeno oznako ob naslednjem odprtju seznama računov.
4. Mehanik gumba ne vidi, njegov klic PUT /api/racuni/{id} vrne 403.

## UZ-15 Upravljanje kataloga delov
**Funkcionalnost:** F8 · **Vloga:** Vodja

Kot vodja želim dodajati in urejati dele ter zalogo, da katalog UZtreza stanju v skladišču.

Acceptance criteria:
1. Del ima šifro, naziv, ceno, zalogo in minimalno zalogo; vsa polja so obvezna.
2. Cena in zaloga ne smeta biti negativni.
3. Šifra, ki že obstaja, je zavrnjena.
4. Spremenjena zaloga se pri naslednjem dodajanju dela na nalog že upošteva.
5. Mehanik katalog vidi, ga pa ne more spreminjati (PUT/POST/DELETE vrne 403).
