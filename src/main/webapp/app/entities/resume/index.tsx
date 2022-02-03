import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Resume from './resume';
import ResumeDetail from './resume-detail';
import ResumeUpdate from './resume-update';
import ResumeDeleteDialog from './resume-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ResumeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ResumeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ResumeDetail} />
      <ErrorBoundaryRoute path={match.url} component={Resume} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ResumeDeleteDialog} />
  </>
);

export default Routes;
