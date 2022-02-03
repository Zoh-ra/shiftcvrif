import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './contact.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ContactDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const contactEntity = useAppSelector(state => state.contact.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="contactDetailsHeading">
          <Translate contentKey="cvthequeApp.contact.detail.title">Contact</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{contactEntity.id}</dd>
          <dt>
            <span id="geolocalisation">
              <Translate contentKey="cvthequeApp.contact.geolocalisation">Geolocalisation</Translate>
            </span>
          </dt>
          <dd>{contactEntity.geolocalisation}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="cvthequeApp.contact.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{contactEntity.nom}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="cvthequeApp.contact.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{contactEntity.prenom}</dd>
          <dt>
            <span id="mail">
              <Translate contentKey="cvthequeApp.contact.mail">Mail</Translate>
            </span>
          </dt>
          <dd>{contactEntity.mail}</dd>
          <dt>
            <span id="message">
              <Translate contentKey="cvthequeApp.contact.message">Message</Translate>
            </span>
          </dt>
          <dd>{contactEntity.message}</dd>
        </dl>
        <Button tag={Link} to="/contact" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/contact/${contactEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ContactDetail;
