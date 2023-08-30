import product from 'app/entities/product/product.reducer';
import category from 'app/entities/category/category.reducer';
import customer from 'app/entities/customer/customer.reducer';
import address from 'app/entities/address/address.reducer';
import order from 'app/entities/order/order.reducer';
import delivery from 'app/entities/delivery/delivery.reducer';
import orderStat from 'app/entities/order-stat/order-stat.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  product,
  category,
  customer,
  address,
  order,
  delivery,
  orderStat,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
