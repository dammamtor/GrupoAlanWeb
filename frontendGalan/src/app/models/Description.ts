import { Product } from "./Product";

export interface Description {
    replace: any;
    id: number;
    ref: string;
    details: string;
    comp: string;
    type: string;
    product?: Product;
}