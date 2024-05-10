import { Product } from "./Product";

export interface Category {
    replace: any;
    category_id: number;
    ref: string;
    category: string;
    products?: Product[]; // Representa la relación many-to-many con la clase Products
}
