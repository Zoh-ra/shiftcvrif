import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Design from './design';
import DesignDetail from './design-detail';
import DesignUpdate from './design-update';
import DesignDeleteDialog from './design-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DesignUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DesignUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DesignDetail} />
      <ErrorBoundaryRoute path={match.url} component={Design} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DesignDeleteDialog} />
  </>
);

export default Routes;
