import { Product } from "./Product";

export interface Image {
    imageId: number;
    ref: string;
    imgMin?: string;
    imgMax?: string;
    productImage?: string;
    modelImage?: string;
    childImage?: string;
    detailsImages?: string;
    viewsImages?: string;
    otherImages?: string;
    main?: number;
    product?: Product; // Debes definir la interfaz Product si no está definida
}
