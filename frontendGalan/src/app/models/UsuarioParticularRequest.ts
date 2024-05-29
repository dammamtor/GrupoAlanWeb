import { AccountType } from "./AccountType";

export interface UsuarioParticularRequest {
    // Datos de contacto
    name: string;
    email: string;
    phoneNumber: string;

    // Dirección de envío
    country: string;
    city: string;
    address: string;
    postalCode: string;

    username: string;
    password: string;
    repeatPassword: string;
    accountType: AccountType;
}