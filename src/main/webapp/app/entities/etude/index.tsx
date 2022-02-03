import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Etude from './etude';
import EtudeDetail from './etude-detail';
import EtudeUpdate from './etude-update';
import EtudeDeleteDialog from './etude-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EtudeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EtudeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EtudeDetail} />
      <ErrorBoundaryRoute path={match.url} component={Etude} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EtudeDeleteDialog} />
  </>
);

export default Routes;
