import { IAddress } from 'app/shared/model/address.model';
import { IOrder } from 'app/shared/model/order.model';

export interface ICustomer {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phoneNumber?: string | null;
  address?: string | null;
  addresses?: IAddress[] | null;
  orders?: IOrder[] | null;
}

export const defaultValue: Readonly<ICustomer> = {};
