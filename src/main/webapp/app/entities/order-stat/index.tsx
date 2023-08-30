import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import OrderStat from './order-stat';
import OrderStatDetail from './order-stat-detail';

const OrderStatRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<OrderStat />} />
    <Route path=":id">
      <Route index element={<OrderStatDetail />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OrderStatRoutes;
