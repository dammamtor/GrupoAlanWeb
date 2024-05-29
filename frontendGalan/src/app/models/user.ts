import { AccountType } from "./AccountType";

export interface User {
  id: number;
  email: string;
  password: string;
  username: string;
  accountType: AccountType;
  isEnabled?: boolean;
  name?: string;
  createdAt?: string; // Utiliza string para ISO 8601 format date
  phoneNumber?: string;
  country?: string;
  city?: string;
  address?: string;
  postalCode?: string;
  companyName?: string;
  companyAddress?: string;
  companyPhoneNumber?: string;
}
