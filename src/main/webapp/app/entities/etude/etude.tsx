import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './etude.reducer';
import { IEtude } from 'app/shared/model/etude.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Etude = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const etudeList = useAppSelector(state => state.etude.entities);
  const loading = useAppSelector(state => state.etude.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="etude-heading" data-cy="EtudeHeading">
        <Translate contentKey="cvthequeApp.etude.home.title">Etudes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="cvthequeApp.etude.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="cvthequeApp.etude.home.createLabel">Create new Etude</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {etudeList && etudeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="cvthequeApp.etude.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.etude.nomEtude">Nom Etude</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.etude.anneeEtude">Annee Etude</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.etude.adresseEtude">Adresse Etude</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {etudeList.map((etude, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${etude.id}`} color="link" size="sm">
                      {etude.id}
                    </Button>
                  </td>
                  <td>{etude.nomEtude}</td>
                  <td>{etude.anneeEtude ? <TextFormat type="date" value={etude.anneeEtude} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{etude.adresseEtude ? <Link to={`adresse/${etude.adresseEtude.id}`}>{etude.adresseEtude.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${etude.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${etude.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${etude.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="cvthequeApp.etude.home.notFound">No Etudes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Etude;
