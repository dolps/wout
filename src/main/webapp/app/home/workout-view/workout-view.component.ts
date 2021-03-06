import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {WorkOut, WorkOutService} from '../../entities/work-out';
import {Program} from '../../entities/program';

@Component({
    selector: 'jhi-workout-view',
    templateUrl: './workout-view.component.html',
    styles: []
})
export class WorkoutViewComponent implements OnInit, OnChanges {
    @Input() program: Program;
    workouts: WorkOut[];
    selectedWorkout: WorkOut;
    btnCreateEntity = 'work-out-new';
    btnCreateTxt = 'create workout';

    constructor(private workOutService: WorkOutService) {
    }

    ngOnInit() {
    }

    ngOnChanges(changes: SimpleChanges) {
        console.log(JSON.stringify(changes));
        this.selectedWorkout = null;
        this.getAllWorkOuts();
    }

    onSelectWorkout(workOut: WorkOut) {
        this.selectedWorkout = workOut;
    }

    getAllWorkOuts() {
        this.workOutService.query({'programId.in': this.program.id}).subscribe((res) => {
            this.workouts = res.body;
        });
    }

    onCreate() {
        console.log('Creating new Workout');
    }
}
