import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Programmation from './programmation';
import ProgrammationDetail from './programmation-detail';
import ProgrammationUpdate from './programmation-update';
import ProgrammationDeleteDialog from './programmation-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProgrammationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProgrammationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProgrammationDetail} />
      <ErrorBoundaryRoute path={match.url} component={Programmation} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProgrammationDeleteDialog} />
  </>
);

export default Routes;
