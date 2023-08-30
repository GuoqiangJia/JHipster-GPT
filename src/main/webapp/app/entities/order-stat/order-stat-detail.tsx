import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './order-stat.reducer';

export const OrderStatDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const orderStatEntity = useAppSelector(state => state.orderStat.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderStatDetailsHeading">
          <Translate contentKey="shopperApp.orderStat.detail.title">OrderStat</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{orderStatEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="shopperApp.orderStat.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{orderStatEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="shopperApp.orderStat.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{orderStatEntity.lastName}</dd>
          <dt>
            <span id="totalCost">
              <Translate contentKey="shopperApp.orderStat.totalCost">Total Cost</Translate>
            </span>
          </dt>
          <dd>{orderStatEntity.totalCost}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="shopperApp.orderStat.type">Type</Translate>
            </span>
          </dt>
          <dd>{orderStatEntity.type}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="shopperApp.orderStat.email">Email</Translate>
            </span>
          </dt>
          <dd>{orderStatEntity.email}</dd>
        </dl>
        <Button tag={Link} to="/order-stat" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order-stat/${orderStatEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrderStatDetail;
