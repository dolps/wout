import { BaseEntity } from './../../shared';

export class Sett implements BaseEntity {
    constructor(
        public id?: number,
        public order?: number,
        public reps?: number,
        public weight?: number,
        public exerciseId?: number,
    ) {
    }
}
