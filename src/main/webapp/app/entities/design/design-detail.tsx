import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './design.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const DesignDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const designEntity = useAppSelector(state => state.design.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="designDetailsHeading">
          <Translate contentKey="cvthequeApp.design.detail.title">Design</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{designEntity.id}</dd>
          <dt>
            <span id="nomDesign">
              <Translate contentKey="cvthequeApp.design.nomDesign">Nom Design</Translate>
            </span>
          </dt>
          <dd>{designEntity.nomDesign}</dd>
          <dt>
            <span id="tauxDeDesign">
              <Translate contentKey="cvthequeApp.design.tauxDeDesign">Taux De Design</Translate>
            </span>
          </dt>
          <dd>{designEntity.tauxDeDesign}</dd>
        </dl>
        <Button tag={Link} to="/design" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/design/${designEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DesignDetail;
