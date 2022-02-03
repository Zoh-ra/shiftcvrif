import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAdresse } from 'app/shared/model/adresse.model';
import { getEntities as getAdresses } from 'app/entities/adresse/adresse.reducer';
import { IOutil } from 'app/shared/model/outil.model';
import { getEntities as getOutils } from 'app/entities/outil/outil.reducer';
import { getEntity, updateEntity, createEntity, reset } from './experience.reducer';
import { IExperience } from 'app/shared/model/experience.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ExperienceUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const adresses = useAppSelector(state => state.adresse.entities);
  const outils = useAppSelector(state => state.outil.entities);
  const experienceEntity = useAppSelector(state => state.experience.entity);
  const loading = useAppSelector(state => state.experience.loading);
  const updating = useAppSelector(state => state.experience.updating);
  const updateSuccess = useAppSelector(state => state.experience.updateSuccess);
  const handleClose = () => {
    props.history.push('/experience');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAdresses({}));
    dispatch(getOutils({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dateExperience = convertDateTimeToServer(values.dateExperience);

    const entity = {
      ...experienceEntity,
      ...values,
      adresseExperience: adresses.find(it => it.id.toString() === values.adresseExperience.toString()),
      outil: outils.find(it => it.id.toString() === values.outil.toString()),
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
          dateExperience: displayDefaultDateTime(),
        }
      : {
          ...experienceEntity,
          dateExperience: convertDateTimeFromServer(experienceEntity.dateExperience),
          adresseExperience: experienceEntity?.adresseExperience?.id,
          outil: experienceEntity?.outil?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="cvthequeApp.experience.home.createOrEditLabel" data-cy="ExperienceCreateUpdateHeading">
            <Translate contentKey="cvthequeApp.experience.home.createOrEditLabel">Create or edit a Experience</Translate>
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
                  id="experience-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('cvthequeApp.experience.nomEntreprise')}
                id="experience-nomEntreprise"
                name="nomEntreprise"
                data-cy="nomEntreprise"
                type="text"
              />
              <ValidatedField
                label={translate('cvthequeApp.experience.nomPoste')}
                id="experience-nomPoste"
                name="nomPoste"
                data-cy="nomPoste"
                type="text"
              />
              <ValidatedField
                label={translate('cvthequeApp.experience.dateExperience')}
                id="experience-dateExperience"
                name="dateExperience"
                data-cy="dateExperience"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('cvthequeApp.experience.descriptionExperience')}
                id="experience-descriptionExperience"
                name="descriptionExperience"
                data-cy="descriptionExperience"
                type="text"
              />
              <ValidatedField
                id="experience-adresseExperience"
                name="adresseExperience"
                data-cy="adresseExperience"
                label={translate('cvthequeApp.experience.adresseExperience')}
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
              <ValidatedField
                id="experience-outil"
                name="outil"
                data-cy="outil"
                label={translate('cvthequeApp.experience.outil')}
                type="select"
              >
                <option value="" key="0" />
                {outils
                  ? outils.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/experience" replace color="info">
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

export default ExperienceUpdate;
