import dayjs from 'dayjs';
import { IAdresse } from 'app/shared/model/adresse.model';
import { IOutil } from 'app/shared/model/outil.model';

export interface IExperience {
  id?: number;
  nomEntreprise?: string | null;
  nomPoste?: string | null;
  dateExperience?: string | null;
  descriptionExperience?: string | null;
  adresseExperience?: IAdresse | null;
  outil?: IOutil | null;
}

export const defaultValue: Readonly<IExperience> = {};
