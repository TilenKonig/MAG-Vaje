# 01 Zasnova rešitve

## Ime

*Delavnica neki...

## Namen in problem

Zasebniki/ s.pji vodijo naloge na papirju ali v razpršenih preglednicah. Zato se izgubijo podatki o tem, katero vozilo je v delu, katere dele so porabili in ali je račun že izdan. Stranka pa nima pregleda nad stanjem svojega popravila.

Rešitev združi vse na enem mestu. delovni nalog, porabljene dele, račun in evidenco strank z njihovimi vozili.

## Ciljni uporabniki

- **Mehaniki** ki odpirajo naloge, beležijo opravljeno delo in porabljene dele.
- **Vodja delavnice**, ki ureja katalog delov, zalogo in dodeljuje naloge.
- **Stranke**, ki želijo videti stanje svojega vozila, zgodovino servisov in račune.

## Kratek opis delovanja

1. Stranka pripelje vozilo. Vodja delavnice jo najde v evidenci (ali jo doda) in odpre delovni nalog za njeno vozilo z opisom težave.
2. Mehanik nalog postavi v stanje "v delu".
3. Med delom mehanik na nalog dodaja dele iz kataloga. Zaloga se samodejno zmanjša. Če dela ni dovolj na zalogi, aplikacija to javi. Vodja del naroči z enim klikom, aplikacija po "dobavi" zalogo poveča.
4. Ko je delo končano, mehanik vpiše opis opravljenega dela in nalog zaključi.
5. Vodja vpiše ure dela in izda račun (vsota delov in ur dela). Račun je viden stranki v aplikaciji, vodja ga označi kot plačanega, ko je poravnan.
6. Stranka se prijavi in vidi svoja vozila, naloge, stanje popravila in račune.
