import dayjs from 'dayjs';
import { IProduct } from 'app/shared/model/product.model';
import { ICustomer } from 'app/shared/model/customer.model';
import { IDelivery } from 'app/shared/model/delivery.model';

export interface IOrder {
  id?: number;
  date?: string;
  status?: string;
  totalPrice?: number;
  products?: IProduct[] | null;
  customer?: ICustomer | null;
  deliveries?: IDelivery | null;
}

export const defaultValue: Readonly<IOrder> = {};
