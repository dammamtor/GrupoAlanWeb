import { Category } from "./Category";
import { Color } from "./Color";
import { Description } from "./Description";
import { Image } from "./Image";

export interface Product {
    productId: number;
    name: string;
    ref: string;
    description?: string;
    price?: number;
    available?: boolean;
    weight?: string;
    length?: string;
    height?: string;
    width?: string;
    measures?: string;
    colors?: string; 
    descriptions?: Description[]; // Debes definir la interfaz Description si no está definida
    images?: Image[]; // Debes definir la interfaz Image si no está definida
    colorsSet?: Color[]; // Debes definir la interfaz Color si no está definida
    categories?: Category[]; // Debes definir la interfaz Category si no está definida
}







