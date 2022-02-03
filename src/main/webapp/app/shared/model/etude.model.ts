import dayjs from 'dayjs';
import { IAdresse } from 'app/shared/model/adresse.model';

export interface IEtude {
  id?: number;
  nomEtude?: string | null;
  anneeEtude?: string | null;
  adresseEtude?: IAdresse | null;
}

export const defaultValue: Readonly<IEtude> = {};
