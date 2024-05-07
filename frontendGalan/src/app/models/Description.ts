import { Product } from "./Product";

export interface Description {
    id: number;
    ref: string;
    details: string;
    product?: Product;
}