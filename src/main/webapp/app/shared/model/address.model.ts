import { ICustomer } from 'app/shared/model/customer.model';

export interface IAddress {
  id?: number;
  address1?: string | null;
  city?: string | null;
  state?: string | null;
  zipcode?: string | null;
  country?: string | null;
  customer?: ICustomer | null;
}

export const defaultValue: Readonly<IAddress> = {};
