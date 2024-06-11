import { Variants } from "./Variants";

export interface Stock {
    id: number;
    matnr: string;
    uniqueRef: string;
    ref: string;
    stockAvailability: string;
    stock: number;
    date: string;
    variants: Variants
}