export interface IPortfolio {
  id?: number;
  image?: string | null;
  lien?: string | null;
}

export const defaultValue: Readonly<IPortfolio> = {};
