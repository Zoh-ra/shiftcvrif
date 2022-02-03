import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Langue from './langue';
import Resume from './resume';
import Avis from './avis';
import Outil from './outil';
import Adresse from './adresse';
import Contact from './contact';
import Etude from './etude';
import Experience from './experience';
import Profil from './profil';
import Portfolio from './portfolio';
import Programmation from './programmation';
import Design from './design';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}langue`} component={Langue} />
      <ErrorBoundaryRoute path={`${match.url}resume`} component={Resume} />
      <ErrorBoundaryRoute path={`${match.url}avis`} component={Avis} />
      <ErrorBoundaryRoute path={`${match.url}outil`} component={Outil} />
      <ErrorBoundaryRoute path={`${match.url}adresse`} component={Adresse} />
      <ErrorBoundaryRoute path={`${match.url}contact`} component={Contact} />
      <ErrorBoundaryRoute path={`${match.url}etude`} component={Etude} />
      <ErrorBoundaryRoute path={`${match.url}experience`} component={Experience} />
      <ErrorBoundaryRoute path={`${match.url}profil`} component={Profil} />
      <ErrorBoundaryRoute path={`${match.url}portfolio`} component={Portfolio} />
      <ErrorBoundaryRoute path={`${match.url}programmation`} component={Programmation} />
      <ErrorBoundaryRoute path={`${match.url}design`} component={Design} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
