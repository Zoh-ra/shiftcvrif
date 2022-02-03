import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './programmation.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProgrammationDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const programmationEntity = useAppSelector(state => state.programmation.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="programmationDetailsHeading">
          <Translate contentKey="cvthequeApp.programmation.detail.title">Programmation</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{programmationEntity.id}</dd>
          <dt>
            <span id="nomLangage">
              <Translate contentKey="cvthequeApp.programmation.nomLangage">Nom Langage</Translate>
            </span>
          </dt>
          <dd>{programmationEntity.nomLangage}</dd>
          <dt>
            <span id="tauxDeLangage">
              <Translate contentKey="cvthequeApp.programmation.tauxDeLangage">Taux De Langage</Translate>
            </span>
          </dt>
          <dd>{programmationEntity.tauxDeLangage}</dd>
        </dl>
        <Button tag={Link} to="/programmation" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/programmation/${programmationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProgrammationDetail;
