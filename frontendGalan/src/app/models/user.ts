import { AccountType } from "./AccountType";

export interface User {
  id: number;
  email: string;
  password: string;
  username: string;
  accountType: AccountType;
  isEnabled?: boolean;
  firstName?: string;
  lastName?: string;
  createdAt?: string; // Utiliza string para ISO 8601 format date
  companyName?: string;
  companyAddress?: string;
  companyPhoneNumber?: string;
}
