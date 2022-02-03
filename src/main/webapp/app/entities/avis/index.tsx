import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Avis from './avis';
import AvisDetail from './avis-detail';
import AvisUpdate from './avis-update';
import AvisDeleteDialog from './avis-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AvisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AvisUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AvisDetail} />
      <ErrorBoundaryRoute path={match.url} component={Avis} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AvisDeleteDialog} />
  </>
);

export default Routes;
