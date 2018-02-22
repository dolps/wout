import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WoutSharedModule } from '../../shared';
import { WoutAdminModule } from '../../admin/admin.module';
import {
    ProgramService,
    ProgramPopupService,
    ProgramComponent,
    ProgramDetailComponent,
    ProgramDialogComponent,
    ProgramPopupComponent,
    ProgramDeletePopupComponent,
    ProgramDeleteDialogComponent,
    programRoute,
    programPopupRoute,
} from './';

const ENTITY_STATES = [
    ...programRoute,
    ...programPopupRoute,
];

@NgModule({
    imports: [
        WoutSharedModule,
        WoutAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProgramComponent,
        ProgramDetailComponent,
        ProgramDialogComponent,
        ProgramDeleteDialogComponent,
        ProgramPopupComponent,
        ProgramDeletePopupComponent,
    ],
    entryComponents: [
        ProgramComponent,
        ProgramDialogComponent,
        ProgramPopupComponent,
        ProgramDeleteDialogComponent,
        ProgramDeletePopupComponent,
    ],
    providers: [
        ProgramService,
        ProgramPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WoutProgramModule {}
