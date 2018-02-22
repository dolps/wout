import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Exercise, ExerciseService} from '../../entities/exercise';
import {WorkOut} from '../../entities/work-out';
import {HttpResponse} from '@angular/common/http';

@Component({
    selector: 'jhi-exercise-view',
    templateUrl: './exercise-view.component.html',
    styles: []
})
export class ExerciseViewComponent implements OnInit, OnChanges {
    @Input() workOut: WorkOut;
    exercises: Exercise[];
    selectedExercise: Exercise;
    btnCreateEntity = 'exercise-new';
    btnCreateTxt = 'create exercise';

    constructor(private exerciseService: ExerciseService) {
    }

    ngOnInit() {
    }

    ngOnChanges(changes: SimpleChanges) {
        console.log(JSON.stringify(changes));
        this.getAllExercises();
    }

    onSelect(exercise) {
        this.selectedExercise = exercise;
    }

    onCreate() {
        console.log('creating new exercise');
    }

    getAllExercises() {
        this.exerciseService.query({'workOutId.in': this.workOut.id}).subscribe((res: HttpResponse<Exercise[]>) => {
            this.exercises = res.body;
        });
    }
}
