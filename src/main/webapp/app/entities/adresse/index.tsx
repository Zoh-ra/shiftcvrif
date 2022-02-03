import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Adresse from './adresse';
import AdresseDetail from './adresse-detail';
import AdresseUpdate from './adresse-update';
import AdresseDeleteDialog from './adresse-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AdresseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AdresseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AdresseDetail} />
      <ErrorBoundaryRoute path={match.url} component={Adresse} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AdresseDeleteDialog} />
  </>
);

export default Routes;
