import { Category } from './Category';
import { Description } from './Description';
import { Image } from './Image';
import { Marking } from './Marking';
import { Variants } from './Variants';

export interface Product {
  productId: number;
  ref: string;
  name: string;
  price: string; // ESTE CAMPO ESTA DE FORMA TEMPORAL HASTA QUE SEPAMOS COMO FUNCIONAN LOS PRECIOS DE LOS PRODUCTOS.
  printcode: string;
  length: string;
  height: string;
  width: string;
  diameter: string;
  weight: string;
  intrastat: string;
  pf_type: number;
  pf_units: number;
  pf_description: string;
  pf_length: string;
  pf_height: string;
  pf_width: string;
  pf_weight: string;
  pi2_type: number;
  pi2_units: number;
  pi2_description: string;
  pi2_length: string;
  pi2_height: string;
  pi2_width: string;
  pi2_weight: string;
  pi1_type: number;
  pi1_units: number;
  pi1_description: string;
  pi1_length: string;
  pi1_height: string;
  pi1_width: string;
  pi1_weight: string;
  ptc_type: number;
  ptc_units: number;
  ptc_description: string;
  ptc_length: string;
  ptc_height: string;
  ptc_width: string;
  ptc_wight: string;
  ptc_net_weight: string;
  pallet_units: number;
  bundle_pallets: number;
  pallet_weight: number;
  sizes: string;
  colors?: string;
  descriptions?: Description[]; // Debes definir la interfaz Description si no está definida
  images?: Image[]; // Debes definir la interfaz Image si no está definida
  categories?: Category[]; // Debes definir la interfaz Category si no está definida
  variants?: Variants[];
  markings?: Marking[]; // Añade esta línea para incluir markings
}
