import dayjs from 'dayjs';

export interface IProfil {
  id?: number;
  dateNaissance?: string;
  telResume?: number | null;
  addressLine1?: string | null;
  addressLine2?: string | null;
  city?: string | null;
  country?: string | null;
  profession?: string | null;
  website?: string | null;
  description?: string | null;
}

export const defaultValue: Readonly<IProfil> = {};
