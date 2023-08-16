import dayjs from 'dayjs';
import { IOrder } from 'app/shared/model/order.model';

export interface IDelivery {
  id?: number;
  trackingNumber?: string;
  carrier?: string;
  shippingDate?: string;
  orders?: IOrder[] | null;
}

export const defaultValue: Readonly<IDelivery> = {};
