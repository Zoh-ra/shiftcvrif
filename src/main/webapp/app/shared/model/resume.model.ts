import dayjs from 'dayjs';
import { IPortfolio } from 'app/shared/model/portfolio.model';
import { IProgrammation } from 'app/shared/model/programmation.model';
import { IProfil } from 'app/shared/model/profil.model';
import { IDesign } from 'app/shared/model/design.model';
import { IExperience } from 'app/shared/model/experience.model';
import { IEtude } from 'app/shared/model/etude.model';
import { IContact } from 'app/shared/model/contact.model';
import { ILangue } from 'app/shared/model/langue.model';
import { IAvis } from 'app/shared/model/avis.model';
import { IAdresse } from 'app/shared/model/adresse.model';
import { IUser } from 'app/shared/model/user.model';

export interface IResume {
  id?: number;
  titre?: string | null;
  dateCreation?: string | null;
  porfolio?: IPortfolio | null;
  programmation?: IProgrammation | null;
  profil?: IProfil | null;
  design?: IDesign | null;
  experience?: IExperience | null;
  etude?: IEtude | null;
  contact?: IContact | null;
  langue?: ILangue | null;
  avis?: IAvis | null;
  adresse?: IAdresse | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IResume> = {};
