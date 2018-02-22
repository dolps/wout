import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { WoutProgramModule } from './program/program.module';
import { WoutWorkOutModule } from './work-out/work-out.module';
import { WoutExerciseModule } from './exercise/exercise.module';
import { WoutSettModule } from './sett/sett.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        WoutProgramModule,
        WoutWorkOutModule,
        WoutExerciseModule,
        WoutSettModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WoutEntityModule {}
