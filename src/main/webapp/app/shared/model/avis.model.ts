export interface IAvis {
  id?: number;
  nomAvis?: string | null;
  prenomAvis?: string | null;
  photoAvis?: string | null;
  descriptionAvis?: string | null;
  dateAvis?: string | null;
}

export const defaultValue: Readonly<IAvis> = {};
