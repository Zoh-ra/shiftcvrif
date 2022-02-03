import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './contact.reducer';
import { IContact } from 'app/shared/model/contact.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ContactUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const contactEntity = useAppSelector(state => state.contact.entity);
  const loading = useAppSelector(state => state.contact.loading);
  const updating = useAppSelector(state => state.contact.updating);
  const updateSuccess = useAppSelector(state => state.contact.updateSuccess);
  const handleClose = () => {
    props.history.push('/contact');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...contactEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...contactEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="cvthequeApp.contact.home.createOrEditLabel" data-cy="ContactCreateUpdateHeading">
            <Translate contentKey="cvthequeApp.contact.home.createOrEditLabel">Create or edit a Contact</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="contact-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('cvthequeApp.contact.geolocalisation')}
                id="contact-geolocalisation"
                name="geolocalisation"
                data-cy="geolocalisation"
                type="text"
              />
              <ValidatedField label={translate('cvthequeApp.contact.nom')} id="contact-nom" name="nom" data-cy="nom" type="text" />
              <ValidatedField
                label={translate('cvthequeApp.contact.prenom')}
                id="contact-prenom"
                name="prenom"
                data-cy="prenom"
                type="text"
              />
              <ValidatedField label={translate('cvthequeApp.contact.mail')} id="contact-mail" name="mail" data-cy="mail" type="text" />
              <ValidatedField
                label={translate('cvthequeApp.contact.message')}
                id="contact-message"
                name="message"
                data-cy="message"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/contact" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ContactUpdate;
