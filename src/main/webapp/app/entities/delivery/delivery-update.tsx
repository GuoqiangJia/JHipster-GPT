import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDelivery } from 'app/shared/model/delivery.model';
import { getEntity, updateEntity, createEntity, reset } from './delivery.reducer';

export const DeliveryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const deliveryEntity = useAppSelector(state => state.delivery.entity);
  const loading = useAppSelector(state => state.delivery.loading);
  const updating = useAppSelector(state => state.delivery.updating);
  const updateSuccess = useAppSelector(state => state.delivery.updateSuccess);

  const handleClose = () => {
    navigate('/delivery' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.shippingDate = convertDateTimeToServer(values.shippingDate);

    const entity = {
      ...deliveryEntity,
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
          shippingDate: displayDefaultDateTime(),
        }
      : {
          ...deliveryEntity,
          shippingDate: convertDateTimeFromServer(deliveryEntity.shippingDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="shopperApp.delivery.home.createOrEditLabel" data-cy="DeliveryCreateUpdateHeading">
            <Translate contentKey="shopperApp.delivery.home.createOrEditLabel">Create or edit a Delivery</Translate>
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
                  id="delivery-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('shopperApp.delivery.trackingNumber')}
                id="delivery-trackingNumber"
                name="trackingNumber"
                data-cy="trackingNumber"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('shopperApp.delivery.carrier')}
                id="delivery-carrier"
                name="carrier"
                data-cy="carrier"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('shopperApp.delivery.shippingDate')}
                id="delivery-shippingDate"
                name="shippingDate"
                data-cy="shippingDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/delivery" replace color="info">
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

export default DeliveryUpdate;
