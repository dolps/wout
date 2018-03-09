import { BaseEntity } from './../../shared';

export class Program implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public userId?: number,
        public workOuts?: BaseEntity[],
    ) {
    }
}
