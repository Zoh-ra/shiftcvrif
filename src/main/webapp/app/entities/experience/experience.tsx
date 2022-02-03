import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './experience.reducer';
import { IExperience } from 'app/shared/model/experience.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Experience = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const experienceList = useAppSelector(state => state.experience.entities);
  const loading = useAppSelector(state => state.experience.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="experience-heading" data-cy="ExperienceHeading">
        <Translate contentKey="cvthequeApp.experience.home.title">Experiences</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="cvthequeApp.experience.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="cvthequeApp.experience.home.createLabel">Create new Experience</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {experienceList && experienceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="cvthequeApp.experience.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.experience.nomEntreprise">Nom Entreprise</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.experience.nomPoste">Nom Poste</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.experience.dateExperience">Date Experience</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.experience.descriptionExperience">Description Experience</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.experience.adresseExperience">Adresse Experience</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.experience.outil">Outil</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {experienceList.map((experience, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${experience.id}`} color="link" size="sm">
                      {experience.id}
                    </Button>
                  </td>
                  <td>{experience.nomEntreprise}</td>
                  <td>{experience.nomPoste}</td>
                  <td>
                    {experience.dateExperience ? (
                      <TextFormat type="date" value={experience.dateExperience} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{experience.descriptionExperience}</td>
                  <td>
                    {experience.adresseExperience ? (
                      <Link to={`adresse/${experience.adresseExperience.id}`}>{experience.adresseExperience.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{experience.outil ? <Link to={`outil/${experience.outil.id}`}>{experience.outil.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${experience.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${experience.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${experience.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="cvthequeApp.experience.home.notFound">No Experiences found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Experience;
