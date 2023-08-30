import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Product from './product';
import Category from './category';
import Customer from './customer';
import Address from './address';
import Order from './order';
import Delivery from './delivery';
import OrderStat from './order-stat';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="product/*" element={<Product />} />
        <Route path="category/*" element={<Category />} />
        <Route path="customer/*" element={<Customer />} />
        <Route path="address/*" element={<Address />} />
        <Route path="order/*" element={<Order />} />
        <Route path="delivery/*" element={<Delivery />} />
        <Route path="order-stat/*" element={<OrderStat />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
