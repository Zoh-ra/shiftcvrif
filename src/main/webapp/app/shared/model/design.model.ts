export interface IDesign {
  id?: number;
  nomDesign?: string | null;
  tauxDeDesign?: number | null;
}

export const defaultValue: Readonly<IDesign> = {};
