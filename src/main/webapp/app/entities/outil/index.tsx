import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Outil from './outil';
import OutilDetail from './outil-detail';
import OutilUpdate from './outil-update';
import OutilDeleteDialog from './outil-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OutilUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OutilUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OutilDetail} />
      <ErrorBoundaryRoute path={match.url} component={Outil} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OutilDeleteDialog} />
  </>
);

export default Routes;
