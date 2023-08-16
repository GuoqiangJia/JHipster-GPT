import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './delivery.reducer';

export const DeliveryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const deliveryEntity = useAppSelector(state => state.delivery.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="deliveryDetailsHeading">
          <Translate contentKey="shopperApp.delivery.detail.title">Delivery</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{deliveryEntity.id}</dd>
          <dt>
            <span id="trackingNumber">
              <Translate contentKey="shopperApp.delivery.trackingNumber">Tracking Number</Translate>
            </span>
          </dt>
          <dd>{deliveryEntity.trackingNumber}</dd>
          <dt>
            <span id="carrier">
              <Translate contentKey="shopperApp.delivery.carrier">Carrier</Translate>
            </span>
          </dt>
          <dd>{deliveryEntity.carrier}</dd>
          <dt>
            <span id="shippingDate">
              <Translate contentKey="shopperApp.delivery.shippingDate">Shipping Date</Translate>
            </span>
          </dt>
          <dd>
            {deliveryEntity.shippingDate ? <TextFormat value={deliveryEntity.shippingDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/delivery" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/delivery/${deliveryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DeliveryDetail;
