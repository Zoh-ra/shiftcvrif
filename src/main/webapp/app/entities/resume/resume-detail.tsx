import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './resume.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ResumeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const resumeEntity = useAppSelector(state => state.resume.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="resumeDetailsHeading">
          <Translate contentKey="cvthequeApp.resume.detail.title">Resume</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{resumeEntity.id}</dd>
          <dt>
            <span id="titre">
              <Translate contentKey="cvthequeApp.resume.titre">Titre</Translate>
            </span>
          </dt>
          <dd>{resumeEntity.titre}</dd>
          <dt>
            <span id="dateCreation">
              <Translate contentKey="cvthequeApp.resume.dateCreation">Date Creation</Translate>
            </span>
          </dt>
          <dd>
            {resumeEntity.dateCreation ? <TextFormat value={resumeEntity.dateCreation} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="cvthequeApp.resume.porfolio">Porfolio</Translate>
          </dt>
          <dd>{resumeEntity.porfolio ? resumeEntity.porfolio.id : ''}</dd>
          <dt>
            <Translate contentKey="cvthequeApp.resume.programmation">Programmation</Translate>
          </dt>
          <dd>{resumeEntity.programmation ? resumeEntity.programmation.id : ''}</dd>
          <dt>
            <Translate contentKey="cvthequeApp.resume.profil">Profil</Translate>
          </dt>
          <dd>{resumeEntity.profil ? resumeEntity.profil.id : ''}</dd>
          <dt>
            <Translate contentKey="cvthequeApp.resume.design">Design</Translate>
          </dt>
          <dd>{resumeEntity.design ? resumeEntity.design.id : ''}</dd>
          <dt>
            <Translate contentKey="cvthequeApp.resume.experience">Experience</Translate>
          </dt>
          <dd>{resumeEntity.experience ? resumeEntity.experience.id : ''}</dd>
          <dt>
            <Translate contentKey="cvthequeApp.resume.etude">Etude</Translate>
          </dt>
          <dd>{resumeEntity.etude ? resumeEntity.etude.id : ''}</dd>
          <dt>
            <Translate contentKey="cvthequeApp.resume.contact">Contact</Translate>
          </dt>
          <dd>{resumeEntity.contact ? resumeEntity.contact.id : ''}</dd>
          <dt>
            <Translate contentKey="cvthequeApp.resume.langue">Langue</Translate>
          </dt>
          <dd>{resumeEntity.langue ? resumeEntity.langue.id : ''}</dd>
          <dt>
            <Translate contentKey="cvthequeApp.resume.avis">Avis</Translate>
          </dt>
          <dd>{resumeEntity.avis ? resumeEntity.avis.id : ''}</dd>
          <dt>
            <Translate contentKey="cvthequeApp.resume.adresse">Adresse</Translate>
          </dt>
          <dd>{resumeEntity.adresse ? resumeEntity.adresse.id : ''}</dd>
          <dt>
            <Translate contentKey="cvthequeApp.resume.user">User</Translate>
          </dt>
          <dd>{resumeEntity.user ? resumeEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/resume" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/resume/${resumeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ResumeDetail;
