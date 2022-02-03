import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Langue from './langue';
import LangueDetail from './langue-detail';
import LangueUpdate from './langue-update';
import LangueDeleteDialog from './langue-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LangueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LangueUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LangueDetail} />
      <ErrorBoundaryRoute path={match.url} component={Langue} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LangueDeleteDialog} />
  </>
);

export default Routes;
