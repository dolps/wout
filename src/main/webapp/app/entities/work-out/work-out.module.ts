import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WoutSharedModule } from '../../shared';
import {
    WorkOutService,
    WorkOutPopupService,
    WorkOutComponent,
    WorkOutDetailComponent,
    WorkOutDialogComponent,
    WorkOutPopupComponent,
    WorkOutDeletePopupComponent,
    WorkOutDeleteDialogComponent,
    workOutRoute,
    workOutPopupRoute,
} from './';

const ENTITY_STATES = [
    ...workOutRoute,
    ...workOutPopupRoute,
];

@NgModule({
    imports: [
        WoutSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        WorkOutComponent,
        WorkOutDetailComponent,
        WorkOutDialogComponent,
        WorkOutDeleteDialogComponent,
        WorkOutPopupComponent,
        WorkOutDeletePopupComponent,
    ],
    entryComponents: [
        WorkOutComponent,
        WorkOutDialogComponent,
        WorkOutPopupComponent,
        WorkOutDeleteDialogComponent,
        WorkOutDeletePopupComponent,
    ],
    providers: [
        WorkOutService,
        WorkOutPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WoutWorkOutModule {}
