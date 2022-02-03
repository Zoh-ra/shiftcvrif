import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './profil.reducer';
import { IProfil } from 'app/shared/model/profil.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProfilUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const profilEntity = useAppSelector(state => state.profil.entity);
  const loading = useAppSelector(state => state.profil.loading);
  const updating = useAppSelector(state => state.profil.updating);
  const updateSuccess = useAppSelector(state => state.profil.updateSuccess);
  const handleClose = () => {
    props.history.push('/profil');
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
    values.dateNaissance = convertDateTimeToServer(values.dateNaissance);

    const entity = {
      ...profilEntity,
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
      ? {
          dateNaissance: displayDefaultDateTime(),
        }
      : {
          ...profilEntity,
          dateNaissance: convertDateTimeFromServer(profilEntity.dateNaissance),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="cvthequeApp.profil.home.createOrEditLabel" data-cy="ProfilCreateUpdateHeading">
            <Translate contentKey="cvthequeApp.profil.home.createOrEditLabel">Create or edit a Profil</Translate>
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
                  id="profil-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('cvthequeApp.profil.dateNaissance')}
                id="profil-dateNaissance"
                name="dateNaissance"
                data-cy="dateNaissance"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('cvthequeApp.profil.telResume')}
                id="profil-telResume"
                name="telResume"
                data-cy="telResume"
                type="text"
              />
              <ValidatedField
                label={translate('cvthequeApp.profil.addressLine1')}
                id="profil-addressLine1"
                name="addressLine1"
                data-cy="addressLine1"
                type="text"
              />
              <ValidatedField
                label={translate('cvthequeApp.profil.addressLine2')}
                id="profil-addressLine2"
                name="addressLine2"
                data-cy="addressLine2"
                type="text"
              />
              <ValidatedField label={translate('cvthequeApp.profil.city')} id="profil-city" name="city" data-cy="city" type="text" />
              <ValidatedField
                label={translate('cvthequeApp.profil.country')}
                id="profil-country"
                name="country"
                data-cy="country"
                type="text"
              />
              <ValidatedField
                label={translate('cvthequeApp.profil.profession')}
                id="profil-profession"
                name="profession"
                data-cy="profession"
                type="text"
              />
              <ValidatedField
                label={translate('cvthequeApp.profil.website')}
                id="profil-website"
                name="website"
                data-cy="website"
                type="text"
              />
              <ValidatedField
                label={translate('cvthequeApp.profil.description')}
                id="profil-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/profil" replace color="info">
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

export default ProfilUpdate;
