import { AccountType } from "./AccountType";

export interface UsuarioProfesionalRequest {
    email: string;
    password: string;
    repeatPassword: string;
    username: string;
    companyName: string;
    companyAddress: string;
    companyPhoneNumber: string;
    accountType: AccountType;
    country: string;
    city: string;
    postalCode: string;
}