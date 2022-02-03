import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './resume.reducer';
import { IResume } from 'app/shared/model/resume.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Resume = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const resumeList = useAppSelector(state => state.resume.entities);
  const loading = useAppSelector(state => state.resume.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="resume-heading" data-cy="ResumeHeading">
        <Translate contentKey="cvthequeApp.resume.home.title">Resumes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="cvthequeApp.resume.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="cvthequeApp.resume.home.createLabel">Create new Resume</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {resumeList && resumeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="cvthequeApp.resume.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.resume.titre">Titre</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.resume.dateCreation">Date Creation</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.resume.porfolio">Porfolio</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.resume.programmation">Programmation</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.resume.profil">Profil</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.resume.design">Design</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.resume.experience">Experience</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.resume.etude">Etude</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.resume.contact">Contact</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.resume.langue">Langue</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.resume.avis">Avis</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.resume.adresse">Adresse</Translate>
                </th>
                <th>
                  <Translate contentKey="cvthequeApp.resume.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {resumeList.map((resume, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${resume.id}`} color="link" size="sm">
                      {resume.id}
                    </Button>
                  </td>
                  <td>{resume.titre}</td>
                  <td>{resume.dateCreation ? <TextFormat type="date" value={resume.dateCreation} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{resume.porfolio ? <Link to={`portfolio/${resume.porfolio.id}`}>{resume.porfolio.id}</Link> : ''}</td>
                  <td>
                    {resume.programmation ? <Link to={`programmation/${resume.programmation.id}`}>{resume.programmation.id}</Link> : ''}
                  </td>
                  <td>{resume.profil ? <Link to={`profil/${resume.profil.id}`}>{resume.profil.id}</Link> : ''}</td>
                  <td>{resume.design ? <Link to={`design/${resume.design.id}`}>{resume.design.id}</Link> : ''}</td>
                  <td>{resume.experience ? <Link to={`experience/${resume.experience.id}`}>{resume.experience.id}</Link> : ''}</td>
                  <td>{resume.etude ? <Link to={`etude/${resume.etude.id}`}>{resume.etude.id}</Link> : ''}</td>
                  <td>{resume.contact ? <Link to={`contact/${resume.contact.id}`}>{resume.contact.id}</Link> : ''}</td>
                  <td>{resume.langue ? <Link to={`langue/${resume.langue.id}`}>{resume.langue.id}</Link> : ''}</td>
                  <td>{resume.avis ? <Link to={`avis/${resume.avis.id}`}>{resume.avis.id}</Link> : ''}</td>
                  <td>{resume.adresse ? <Link to={`adresse/${resume.adresse.id}`}>{resume.adresse.id}</Link> : ''}</td>
                  <td>{resume.user ? resume.user.login : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${resume.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${resume.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${resume.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="cvthequeApp.resume.home.notFound">No Resumes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Resume;
