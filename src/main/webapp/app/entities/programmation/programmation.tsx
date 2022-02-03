import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './programmation.reducer';
import { IProgrammation } from 'app/shared/model/programmation.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Programmation = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const programmationList = useAppSelector(state => state.programmation.entities);
  const loading = useAppSelector(state => state.programmation.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="programmation-heading" data-cy="ProgrammationHeading">
        <Translate contentKey="cvthequeApp.programmation.home.title">Programmations</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="cvthequeApp.programmation.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="cvthequeApp.programmation.home.createLabel">Create new Programmation</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {programmationList && programmationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="cvthequeApp.programmation.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.programmation.nomLangage">Nom Langage</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.programmation.tauxDeLangage">Taux De Langage</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {programmationList.map((programmation, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${programmation.id}`} color="link" size="sm">
                      {programmation.id}
                    </Button>
                  </td>
                  <td>{programmation.nomLangage}</td>
                  <td>{programmation.tauxDeLangage}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${programmation.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${programmation.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${programmation.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="cvthequeApp.programmation.home.notFound">No Programmations found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Programmation;
