import { BaseEntity } from './../../shared';

export class Exercise implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public setts?: BaseEntity[],
        public workOutId?: number,
    ) {
    }
}
