import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAdresse } from 'app/shared/model/adresse.model';
import { getEntities as getAdresses } from 'app/entities/adresse/adresse.reducer';
import { getEntity, updateEntity, createEntity, reset } from './etude.reducer';
import { IEtude } from 'app/shared/model/etude.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const EtudeUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const adresses = useAppSelector(state => state.adresse.entities);
  const etudeEntity = useAppSelector(state => state.etude.entity);
  const loading = useAppSelector(state => state.etude.loading);
  const updating = useAppSelector(state => state.etude.updating);
  const updateSuccess = useAppSelector(state => state.etude.updateSuccess);
  const handleClose = () => {
    props.history.push('/etude');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAdresses({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.anneeEtude = convertDateTimeToServer(values.anneeEtude);

    const entity = {
      ...etudeEntity,
      ...values,
      adresseEtude: adresses.find(it => it.id.toString() === values.adresseEtude.toString()),
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
          anneeEtude: displayDefaultDateTime(),
        }
      : {
          ...etudeEntity,
          anneeEtude: convertDateTimeFromServer(etudeEntity.anneeEtude),
          adresseEtude: etudeEntity?.adresseEtude?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="cvthequeApp.etude.home.createOrEditLabel" data-cy="EtudeCreateUpdateHeading">
            <Translate contentKey="cvthequeApp.etude.home.createOrEditLabel">Create or edit a Etude</Translate>
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
                  id="etude-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('cvthequeApp.etude.nomEtude')}
                id="etude-nomEtude"
                name="nomEtude"
                data-cy="nomEtude"
                type="text"
              />
              <ValidatedField
                label={translate('cvthequeApp.etude.anneeEtude')}
                id="etude-anneeEtude"
                name="anneeEtude"
                data-cy="anneeEtude"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="etude-adresseEtude"
                name="adresseEtude"
                data-cy="adresseEtude"
                label={translate('cvthequeApp.etude.adresseEtude')}
                type="select"
              >
                <option value="" key="0" />
                {adresses
                  ? adresses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/etude" replace color="info">
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

export default EtudeUpdate;
