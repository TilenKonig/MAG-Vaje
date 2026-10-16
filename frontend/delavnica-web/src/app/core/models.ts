export type Vloga = 'MEHANIK' | 'STRANKA' | 'VODJA';
export type Stanje = 'ODPRT' | 'V_DELU' | 'ZAKLJUCEN' | 'OBRACUNAN';

export interface Uporabnik {
  id: string;
  email: string;
  ime: string;
  priimek: string;
  telefon?: string;
  vloga: Vloga;
}

export interface Vozilo {
  id: string;
  lastnik: Uporabnik;
  znamka: string;
  model: string;
  letnik: number;
  registrska: string;
}

export interface RezervniDel {
  id: string;
  sifra: string;
  naziv: string;
  cena: number;
  zaloga: number;
  minZaloga: number;
}

export interface DelovniNalog {
  id: string;
  vozilo: Vozilo;
  mehanik: Uporabnik;
  opis: string;
  opisDela?: string;
  stanje: Stanje;
  ureDela: number;
  datumOdprtja: string;
}

export interface PostavkaNaloga {
  id: string;
  del: RezervniDel;
  kolicina: number;
  cena: number;
}

export interface Racun {
  id: string;
  nalog: DelovniNalog;
  znesek: number;
  datum: string;
  placan: boolean;
}
