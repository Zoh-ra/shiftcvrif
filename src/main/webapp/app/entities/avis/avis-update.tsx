import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './avis.reducer';
import { IAvis } from 'app/shared/model/avis.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AvisUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const avisEntity = useAppSelector(state => state.avis.entity);
  const loading = useAppSelector(state => state.avis.loading);
  const updating = useAppSelector(state => state.avis.updating);
  const updateSuccess = useAppSelector(state => state.avis.updateSuccess);
  const handleClose = () => {
    props.history.push('/avis');
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
      ...avisEntity,
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
          ...avisEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="cvthequeApp.avis.home.createOrEditLabel" data-cy="AvisCreateUpdateHeading">
            <Translate contentKey="cvthequeApp.avis.home.createOrEditLabel">Create or edit a Avis</Translate>
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
                  id="avis-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('cvthequeApp.avis.nomAvis')}
                id="avis-nomAvis"
                name="nomAvis"
                data-cy="nomAvis"
                type="text"
              />
              <ValidatedField
                label={translate('cvthequeApp.avis.prenomAvis')}
                id="avis-prenomAvis"
                name="prenomAvis"
                data-cy="prenomAvis"
                type="text"
              />
              <ValidatedField
                label={translate('cvthequeApp.avis.photoAvis')}
                id="avis-photoAvis"
                name="photoAvis"
                data-cy="photoAvis"
                type="text"
              />
              <ValidatedField
                label={translate('cvthequeApp.avis.descriptionAvis')}
                id="avis-descriptionAvis"
                name="descriptionAvis"
                data-cy="descriptionAvis"
                type="text"
              />
              <ValidatedField
                label={translate('cvthequeApp.avis.dateAvis')}
                id="avis-dateAvis"
                name="dateAvis"
                data-cy="dateAvis"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/avis" replace color="info">
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

export default AvisUpdate;
