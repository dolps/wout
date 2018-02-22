import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { WoutSharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';
import { ProgramViewComponent } from './program-view/program-view.component';
import { WorkoutViewComponent } from './workout-view/workout-view.component';
import { ExerciseViewComponent } from './exercise-view/exercise-view.component';
import { SettViewComponent } from './sett-view/sett-view.component';
import { CreateEntityButtonComponent } from './create-entity-button/create-entity-button.component';

@NgModule({
    imports: [
        WoutSharedModule,
        RouterModule.forChild([ HOME_ROUTE ])
    ],
    declarations: [
        HomeComponent,
        ProgramViewComponent,
        WorkoutViewComponent,
        ExerciseViewComponent,
        SettViewComponent,
        CreateEntityButtonComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class WoutHomeModule {}
