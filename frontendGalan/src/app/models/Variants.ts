import { Color } from "./Color";
import { Product } from "./Product";

export interface Variants {
    variantId?: number;
    matnr: string;
    uniqueRef: string;
    ref: string;
    color: string;
    size: string;
    img100: string;
    product?: Product;
    colorSet?: Color;
  }