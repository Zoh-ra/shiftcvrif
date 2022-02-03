import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPortfolio } from 'app/shared/model/portfolio.model';
import { getEntities as getPortfolios } from 'app/entities/portfolio/portfolio.reducer';
import { IProgrammation } from 'app/shared/model/programmation.model';
import { getEntities as getProgrammations } from 'app/entities/programmation/programmation.reducer';
import { IProfil } from 'app/shared/model/profil.model';
import { getEntities as getProfils } from 'app/entities/profil/profil.reducer';
import { IDesign } from 'app/shared/model/design.model';
import { getEntities as getDesigns } from 'app/entities/design/design.reducer';
import { IExperience } from 'app/shared/model/experience.model';
import { getEntities as getExperiences } from 'app/entities/experience/experience.reducer';
import { IEtude } from 'app/shared/model/etude.model';
import { getEntities as getEtudes } from 'app/entities/etude/etude.reducer';
import { IContact } from 'app/shared/model/contact.model';
import { getEntities as getContacts } from 'app/entities/contact/contact.reducer';
import { ILangue } from 'app/shared/model/langue.model';
import { getEntities as getLangues } from 'app/entities/langue/langue.reducer';
import { IAvis } from 'app/shared/model/avis.model';
import { getEntities as getAvis } from 'app/entities/avis/avis.reducer';
import { IAdresse } from 'app/shared/model/adresse.model';
import { getEntities as getAdresses } from 'app/entities/adresse/adresse.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './resume.reducer';
import { IResume } from 'app/shared/model/resume.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ResumeUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const portfolios = useAppSelector(state => state.portfolio.entities);
  const programmations = useAppSelector(state => state.programmation.entities);
  const profils = useAppSelector(state => state.profil.entities);
  const designs = useAppSelector(state => state.design.entities);
  const experiences = useAppSelector(state => state.experience.entities);
  const etudes = useAppSelector(state => state.etude.entities);
  const contacts = useAppSelector(state => state.contact.entities);
  const langues = useAppSelector(state => state.langue.entities);
  const avis = useAppSelector(state => state.avis.entities);
  const adresses = useAppSelector(state => state.adresse.entities);
  const users = useAppSelector(state => state.userManagement.users);
  const resumeEntity = useAppSelector(state => state.resume.entity);
  const loading = useAppSelector(state => state.resume.loading);
  const updating = useAppSelector(state => state.resume.updating);
  const updateSuccess = useAppSelector(state => state.resume.updateSuccess);
  const handleClose = () => {
    props.history.push('/resume');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPortfolios({}));
    dispatch(getProgrammations({}));
    dispatch(getProfils({}));
    dispatch(getDesigns({}));
    dispatch(getExperiences({}));
    dispatch(getEtudes({}));
    dispatch(getContacts({}));
    dispatch(getLangues({}));
    dispatch(getAvis({}));
    dispatch(getAdresses({}));
    dispatch(getUsers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dateCreation = convertDateTimeToServer(values.dateCreation);

    const entity = {
      ...resumeEntity,
      ...values,
      porfolio: portfolios.find(it => it.id.toString() === values.porfolio.toString()),
      programmation: programmations.find(it => it.id.toString() === values.programmation.toString()),
      profil: profils.find(it => it.id.toString() === values.profil.toString()),
      design: designs.find(it => it.id.toString() === values.design.toString()),
      experience: experiences.find(it => it.id.toString() === values.experience.toString()),
      etude: etudes.find(it => it.id.toString() === values.etude.toString()),
      contact: contacts.find(it => it.id.toString() === values.contact.toString()),
      langue: langues.find(it => it.id.toString() === values.langue.toString()),
      avis: avis.find(it => it.id.toString() === values.avis.toString()),
      adresse: adresses.find(it => it.id.toString() === values.adresse.toString()),
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          dateCreation: displayDefaultDateTime(),
        }
      : {
          ...resumeEntity,
          dateCreation: convertDateTimeFromServer(resumeEntity.dateCreation),
          porfolio: resumeEntity?.porfolio?.id,
          programmation: resumeEntity?.programmation?.id,
          profil: resumeEntity?.profil?.id,
          design: resumeEntity?.design?.id,
          experience: resumeEntity?.experience?.id,
          etude: resumeEntity?.etude?.id,
          contact: resumeEntity?.contact?.id,
          langue: resumeEntity?.langue?.id,
          avis: resumeEntity?.avis?.id,
          adresse: resumeEntity?.adresse?.id,
          user: resumeEntity?.user?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="cvthequeApp.resume.home.createOrEditLabel" data-cy="ResumeCreateUpdateHeading">
            <Translate contentKey="cvthequeApp.resume.home.createOrEditLabel">Create or edit a Resume</Translate>
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
                  id="resume-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('cvthequeApp.resume.titre')} id="resume-titre" name="titre" data-cy="titre" type="text" />
              <ValidatedField
                label={translate('cvthequeApp.resume.dateCreation')}
                id="resume-dateCreation"
                name="dateCreation"
                data-cy="dateCreation"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="resume-porfolio"
                name="porfolio"
                data-cy="porfolio"
                label={translate('cvthequeApp.resume.porfolio')}
                type="select"
              >
                <option value="" key="0" />
                {portfolios
                  ? portfolios.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="resume-programmation"
                name="programmation"
                data-cy="programmation"
                label={translate('cvthequeApp.resume.programmation')}
                type="select"
              >
                <option value="" key="0" />
                {programmations
                  ? programmations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="resume-profil"
                name="profil"
                data-cy="profil"
                label={translate('cvthequeApp.resume.profil')}
                type="select"
              >
                <option value="" key="0" />
                {profils
                  ? profils.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="resume-design"
                name="design"
                data-cy="design"
                label={translate('cvthequeApp.resume.design')}
                type="select"
              >
                <option value="" key="0" />
                {designs
                  ? designs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="resume-experience"
                name="experience"
                data-cy="experience"
                label={translate('cvthequeApp.resume.experience')}
                type="select"
              >
                <option value="" key="0" />
                {experiences
                  ? experiences.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="resume-etude" name="etude" data-cy="etude" label={translate('cvthequeApp.resume.etude')} type="select">
                <option value="" key="0" />
                {etudes
                  ? etudes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="resume-contact"
                name="contact"
                data-cy="contact"
                label={translate('cvthequeApp.resume.contact')}
                type="select"
              >
                <option value="" key="0" />
                {contacts
                  ? contacts.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="resume-langue"
                name="langue"
                data-cy="langue"
                label={translate('cvthequeApp.resume.langue')}
                type="select"
              >
                <option value="" key="0" />
                {langues
                  ? langues.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="resume-avis" name="avis" data-cy="avis" label={translate('cvthequeApp.resume.avis')} type="select">
                <option value="" key="0" />
                {avis
                  ? avis.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="resume-adresse"
                name="adresse"
                data-cy="adresse"
                label={translate('cvthequeApp.resume.adresse')}
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
              <ValidatedField id="resume-user" name="user" data-cy="user" label={translate('cvthequeApp.resume.user')} type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/resume" replace color="info">
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

export default ResumeUpdate;
