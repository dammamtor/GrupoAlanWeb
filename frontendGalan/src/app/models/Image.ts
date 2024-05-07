import { Product } from "./Product";

export interface Image {
    imageId: number;
    ref: string;
    img_min?: string;
    img_max?: string;
    productImage?: string;
    modelImage?: string;
    childImage?: string;
    detailsImages?: string;
    viewsImages?: string;
    otherImages?: string;
    product?: Product; // Debes definir la interfaz Product si no está definida
}
