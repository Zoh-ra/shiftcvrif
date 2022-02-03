import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './etude.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const EtudeDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const etudeEntity = useAppSelector(state => state.etude.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="etudeDetailsHeading">
          <Translate contentKey="cvthequeApp.etude.detail.title">Etude</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{etudeEntity.id}</dd>
          <dt>
            <span id="nomEtude">
              <Translate contentKey="cvthequeApp.etude.nomEtude">Nom Etude</Translate>
            </span>
          </dt>
          <dd>{etudeEntity.nomEtude}</dd>
          <dt>
            <span id="anneeEtude">
              <Translate contentKey="cvthequeApp.etude.anneeEtude">Annee Etude</Translate>
            </span>
          </dt>
          <dd>{etudeEntity.anneeEtude ? <TextFormat value={etudeEntity.anneeEtude} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="cvthequeApp.etude.adresseEtude">Adresse Etude</Translate>
          </dt>
          <dd>{etudeEntity.adresseEtude ? etudeEntity.adresseEtude.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/etude" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/etude/${etudeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EtudeDetail;
