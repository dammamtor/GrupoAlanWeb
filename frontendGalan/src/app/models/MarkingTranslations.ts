import { Marking } from "./Marking";

export interface MarkingsTranslations {
    id: number;
    print_area_id: number;
    lang: number;
    txt: string;
    markings?: Marking[];
}