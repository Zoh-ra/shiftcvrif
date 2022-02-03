import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './experience.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ExperienceDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const experienceEntity = useAppSelector(state => state.experience.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="experienceDetailsHeading">
          <Translate contentKey="cvthequeApp.experience.detail.title">Experience</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{experienceEntity.id}</dd>
          <dt>
            <span id="nomEntreprise">
              <Translate contentKey="cvthequeApp.experience.nomEntreprise">Nom Entreprise</Translate>
            </span>
          </dt>
          <dd>{experienceEntity.nomEntreprise}</dd>
          <dt>
            <span id="nomPoste">
              <Translate contentKey="cvthequeApp.experience.nomPoste">Nom Poste</Translate>
            </span>
          </dt>
          <dd>{experienceEntity.nomPoste}</dd>
          <dt>
            <span id="dateExperience">
              <Translate contentKey="cvthequeApp.experience.dateExperience">Date Experience</Translate>
            </span>
          </dt>
          <dd>
            {experienceEntity.dateExperience ? (
              <TextFormat value={experienceEntity.dateExperience} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="descriptionExperience">
              <Translate contentKey="cvthequeApp.experience.descriptionExperience">Description Experience</Translate>
            </span>
          </dt>
          <dd>{experienceEntity.descriptionExperience}</dd>
          <dt>
            <Translate contentKey="cvthequeApp.experience.adresseExperience">Adresse Experience</Translate>
          </dt>
          <dd>{experienceEntity.adresseExperience ? experienceEntity.adresseExperience.id : ''}</dd>
          <dt>
            <Translate contentKey="cvthequeApp.experience.outil">Outil</Translate>
          </dt>
          <dd>{experienceEntity.outil ? experienceEntity.outil.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/experience" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/experience/${experienceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ExperienceDetail;
