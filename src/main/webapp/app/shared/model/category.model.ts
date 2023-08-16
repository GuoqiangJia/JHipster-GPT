import { IProduct } from 'app/shared/model/product.model';
import { CategoryStatus } from 'app/shared/model/enumerations/category-status.model';

export interface ICategory {
  id?: number;
  name?: string;
  description?: string | null;
  status?: keyof typeof CategoryStatus;
  products?: IProduct[] | null;
}

export const defaultValue: Readonly<ICategory> = {};
