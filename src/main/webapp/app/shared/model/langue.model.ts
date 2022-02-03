import { Language } from 'app/shared/model/enumerations/language.model';

export interface ILangue {
  id?: number;
  langue?: Language | null;
}

export const defaultValue: Readonly<ILangue> = {};
