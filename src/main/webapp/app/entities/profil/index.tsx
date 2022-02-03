import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Profil from './profil';
import ProfilDetail from './profil-detail';
import ProfilUpdate from './profil-update';
import ProfilDeleteDialog from './profil-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProfilUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProfilUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProfilDetail} />
      <ErrorBoundaryRoute path={match.url} component={Profil} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProfilDeleteDialog} />
  </>
);

export default Routes;
