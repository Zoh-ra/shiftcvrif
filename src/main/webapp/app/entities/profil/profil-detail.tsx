import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './profil.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProfilDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const profilEntity = useAppSelector(state => state.profil.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="profilDetailsHeading">
          <Translate contentKey="cvthequeApp.profil.detail.title">Profil</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{profilEntity.id}</dd>
          <dt>
            <span id="dateNaissance">
              <Translate contentKey="cvthequeApp.profil.dateNaissance">Date Naissance</Translate>
            </span>
          </dt>
          <dd>
            {profilEntity.dateNaissance ? <TextFormat value={profilEntity.dateNaissance} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="telResume">
              <Translate contentKey="cvthequeApp.profil.telResume">Tel Resume</Translate>
            </span>
          </dt>
          <dd>{profilEntity.telResume}</dd>
          <dt>
            <span id="addressLine1">
              <Translate contentKey="cvthequeApp.profil.addressLine1">Address Line 1</Translate>
            </span>
          </dt>
          <dd>{profilEntity.addressLine1}</dd>
          <dt>
            <span id="addressLine2">
              <Translate contentKey="cvthequeApp.profil.addressLine2">Address Line 2</Translate>
            </span>
          </dt>
          <dd>{profilEntity.addressLine2}</dd>
          <dt>
            <span id="city">
              <Translate contentKey="cvthequeApp.profil.city">City</Translate>
            </span>
          </dt>
          <dd>{profilEntity.city}</dd>
          <dt>
            <span id="country">
              <Translate contentKey="cvthequeApp.profil.country">Country</Translate>
            </span>
          </dt>
          <dd>{profilEntity.country}</dd>
          <dt>
            <span id="profession">
              <Translate contentKey="cvthequeApp.profil.profession">Profession</Translate>
            </span>
          </dt>
          <dd>{profilEntity.profession}</dd>
          <dt>
            <span id="website">
              <Translate contentKey="cvthequeApp.profil.website">Website</Translate>
            </span>
          </dt>
          <dd>{profilEntity.website}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="cvthequeApp.profil.description">Description</Translate>
            </span>
          </dt>
          <dd>{profilEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/profil" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/profil/${profilEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProfilDetail;
