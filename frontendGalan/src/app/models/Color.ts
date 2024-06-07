import { Product } from "./Product";

export interface Color {
    value: any;
    replace: any;
    id: number;
    code: string;
    name: string;
    lang: number;
    url: string;
    products?: Product[]; // Debes definir la interfaz Product si no está definida
}
