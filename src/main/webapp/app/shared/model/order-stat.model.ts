export interface IOrderStat {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  totalCost?: number | null;
  type?: string | null;
  email?: string;
}

export const defaultValue: Readonly<IOrderStat> = {};
