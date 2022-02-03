import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './profil.reducer';
import { IProfil } from 'app/shared/model/profil.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Profil = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const profilList = useAppSelector(state => state.profil.entities);
  const loading = useAppSelector(state => state.profil.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="profil-heading" data-cy="ProfilHeading">
        <Translate contentKey="cvthequeApp.profil.home.title">Profils</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="cvthequeApp.profil.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="cvthequeApp.profil.home.createLabel">Create new Profil</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {profilList && profilList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="cvthequeApp.profil.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.profil.dateNaissance">Date Naissance</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.profil.telResume">Tel Resume</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.profil.addressLine1">Address Line 1</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.profil.addressLine2">Address Line 2</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.profil.city">City</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.profil.country">Country</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.profil.profession">Profession</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.profil.website">Website</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.profil.description">Description</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {profilList.map((profil, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${profil.id}`} color="link" size="sm">
                      {profil.id}
                    </Button>
                  </td>
                  <td>{profil.dateNaissance ? <TextFormat type="date" value={profil.dateNaissance} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{profil.telResume}</td>
                  <td>{profil.addressLine1}</td>
                  <td>{profil.addressLine2}</td>
                  <td>{profil.city}</td>
                  <td>{profil.country}</td>
                  <td>{profil.profession}</td>
                  <td>{profil.website}</td>
                  <td>{profil.description}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${profil.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${profil.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${profil.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="cvthequeApp.profil.home.notFound">No Profils found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Profil;
