export interface IProgrammation {
  id?: number;
  nomLangage?: string | null;
  tauxDeLangage?: number | null;
}

export const defaultValue: Readonly<IProgrammation> = {};
