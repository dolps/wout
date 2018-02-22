import { BaseEntity } from './../../shared';

export class WorkOut implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public programId?: number,
    ) {
    }
}
