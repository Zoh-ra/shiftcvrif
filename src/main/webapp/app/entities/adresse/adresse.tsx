import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './adresse.reducer';
import { IAdresse } from 'app/shared/model/adresse.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Adresse = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const adresseList = useAppSelector(state => state.adresse.entities);
  const loading = useAppSelector(state => state.adresse.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="adresse-heading" data-cy="AdresseHeading">
        <Translate contentKey="cvthequeApp.adresse.home.title">Adresses</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="cvthequeApp.adresse.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="cvthequeApp.adresse.home.createLabel">Create new Adresse</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {adresseList && adresseList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="cvthequeApp.adresse.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.adresse.adresse">Adresse</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.adresse.nomVille">Nom Ville</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.adresse.codePostale">Code Postale</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {adresseList.map((adresse, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${adresse.id}`} color="link" size="sm">
                      {adresse.id}
                    </Button>
                  </td>
                  <td>{adresse.adresse}</td>
                  <td>{adresse.nomVille}</td>
                  <td>{adresse.codePostale}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${adresse.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${adresse.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${adresse.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="cvthequeApp.adresse.home.notFound">No Adresses found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Adresse;
