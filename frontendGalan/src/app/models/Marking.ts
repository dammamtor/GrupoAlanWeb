import { MarkingTechnique } from "./MarkingTechnique";

export interface Marking {
    id: number;
    ref: string;
    print_area_id: number;
    max_colors: string;
    position: string;
    width: number;
    height: number;
    area_img: string;
    technique_ref: string;
    markingTechniques: MarkingTechnique
}