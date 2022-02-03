import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './portfolio.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PortfolioDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const portfolioEntity = useAppSelector(state => state.portfolio.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="portfolioDetailsHeading">
          <Translate contentKey="cvthequeApp.portfolio.detail.title">Portfolio</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{portfolioEntity.id}</dd>
          <dt>
            <span id="image">
              <Translate contentKey="cvthequeApp.portfolio.image">Image</Translate>
            </span>
          </dt>
          <dd>{portfolioEntity.image}</dd>
          <dt>
            <span id="lien">
              <Translate contentKey="cvthequeApp.portfolio.lien">Lien</Translate>
            </span>
          </dt>
          <dd>{portfolioEntity.lien}</dd>
        </dl>
        <Button tag={Link} to="/portfolio" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/portfolio/${portfolioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PortfolioDetail;
