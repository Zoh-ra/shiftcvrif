import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Experience from './experience';
import ExperienceDetail from './experience-detail';
import ExperienceUpdate from './experience-update';
import ExperienceDeleteDialog from './experience-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ExperienceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ExperienceUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ExperienceDetail} />
      <ErrorBoundaryRoute path={match.url} component={Experience} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ExperienceDeleteDialog} />
  </>
);

export default Routes;
