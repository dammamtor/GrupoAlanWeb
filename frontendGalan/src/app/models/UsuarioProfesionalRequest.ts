import { AccountType } from "./AccountType";

export interface UsuarioProfesionalRequest {
    email: string;
    password: string;
    username: string;
    companyName: string;
    companyAddress: string;
    companyPhoneNumber: string;
    accountType: AccountType;
    city: string;
    postalCode: string;
}