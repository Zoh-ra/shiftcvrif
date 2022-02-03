export interface IAdresse {
  id?: number;
  adresse?: string | null;
  nomVille?: string | null;
  codePostale?: number | null;
}

export const defaultValue: Readonly<IAdresse> = {};
