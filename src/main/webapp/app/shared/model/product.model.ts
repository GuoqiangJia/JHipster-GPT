import { ICategory } from 'app/shared/model/category.model';
import { IOrder } from 'app/shared/model/order.model';

export interface IProduct {
  id?: number;
  name?: string;
  description?: string | null;
  price?: number;
  inventory?: number;
  categories?: ICategory[] | null;
  orders?: IOrder | null;
}

export const defaultValue: Readonly<IProduct> = {};
