import { Product } from "./Product";

export interface Color {
    id: number;
    code: string;
    name: string;
    url: string;
    products?: Product[]; // Debes definir la interfaz Product si no está definida
}
