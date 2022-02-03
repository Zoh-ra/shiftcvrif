import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './avis.reducer';
import { IAvis } from 'app/shared/model/avis.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Avis = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const avisList = useAppSelector(state => state.avis.entities);
  const loading = useAppSelector(state => state.avis.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="avis-heading" data-cy="AvisHeading">
        <Translate contentKey="cvthequeApp.avis.home.title">Avis</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="cvthequeApp.avis.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="cvthequeApp.avis.home.createLabel">Create new Avis</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {avisList && avisList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="cvthequeApp.avis.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.avis.nomAvis">Nom Avis</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.avis.prenomAvis">Prenom Avis</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.avis.photoAvis">Photo Avis</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.avis.descriptionAvis">Description Avis</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.avis.dateAvis">Date Avis</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {avisList.map((avis, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${avis.id}`} color="link" size="sm">
                      {avis.id}
                    </Button>
                  </td>
                  <td>{avis.nomAvis}</td>
                  <td>{avis.prenomAvis}</td>
                  <td>{avis.photoAvis}</td>
                  <td>{avis.descriptionAvis}</td>
                  <td>{avis.dateAvis}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${avis.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${avis.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${avis.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="cvthequeApp.avis.home.notFound">No Avis found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Avis;
