import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale from './locale';
import authentication from './authentication';
import applicationProfile from './application-profile';

import administration from 'app/modules/administration/administration.reducer';
import userManagement from 'app/modules/administration/user-management/user-management.reducer';
import register from 'app/modules/account/register/register.reducer';
import activate from 'app/modules/account/activate/activate.reducer';
import password from 'app/modules/account/password/password.reducer';
import settings from 'app/modules/account/settings/settings.reducer';
import passwordReset from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import langue from 'app/entities/langue/langue.reducer';
// prettier-ignore
import resume from 'app/entities/resume/resume.reducer';
// prettier-ignore
import avis from 'app/entities/avis/avis.reducer';
// prettier-ignore
import outil from 'app/entities/outil/outil.reducer';
// prettier-ignore
import adresse from 'app/entities/adresse/adresse.reducer';
// prettier-ignore
import contact from 'app/entities/contact/contact.reducer';
// prettier-ignore
import etude from 'app/entities/etude/etude.reducer';
// prettier-ignore
import experience from 'app/entities/experience/experience.reducer';
// prettier-ignore
import profil from 'app/entities/profil/profil.reducer';
// prettier-ignore
import portfolio from 'app/entities/portfolio/portfolio.reducer';
// prettier-ignore
import programmation from 'app/entities/programmation/programmation.reducer';
// prettier-ignore
import design from 'app/entities/design/design.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const rootReducer = {
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  langue,
  resume,
  avis,
  outil,
  adresse,
  contact,
  etude,
  experience,
  profil,
  portfolio,
  programmation,
  design,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
};

export default rootReducer;
