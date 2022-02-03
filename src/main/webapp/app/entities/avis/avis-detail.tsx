import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './avis.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AvisDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const avisEntity = useAppSelector(state => state.avis.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="avisDetailsHeading">
          <Translate contentKey="cvthequeApp.avis.detail.title">Avis</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{avisEntity.id}</dd>
          <dt>
            <span id="nomAvis">
              <Translate contentKey="cvthequeApp.avis.nomAvis">Nom Avis</Translate>
            </span>
          </dt>
          <dd>{avisEntity.nomAvis}</dd>
          <dt>
            <span id="prenomAvis">
              <Translate contentKey="cvthequeApp.avis.prenomAvis">Prenom Avis</Translate>
            </span>
          </dt>
          <dd>{avisEntity.prenomAvis}</dd>
          <dt>
            <span id="photoAvis">
              <Translate contentKey="cvthequeApp.avis.photoAvis">Photo Avis</Translate>
            </span>
          </dt>
          <dd>{avisEntity.photoAvis}</dd>
          <dt>
            <span id="descriptionAvis">
              <Translate contentKey="cvthequeApp.avis.descriptionAvis">Description Avis</Translate>
            </span>
          </dt>
          <dd>{avisEntity.descriptionAvis}</dd>
          <dt>
            <span id="dateAvis">
              <Translate contentKey="cvthequeApp.avis.dateAvis">Date Avis</Translate>
            </span>
          </dt>
          <dd>{avisEntity.dateAvis}</dd>
        </dl>
        <Button tag={Link} to="/avis" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/avis/${avisEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AvisDetail;
