export interface IContact {
  id?: number;
  geolocalisation?: string | null;
  nom?: string | null;
  prenom?: string | null;
  mail?: string | null;
  message?: string | null;
}

export const defaultValue: Readonly<IContact> = {};
