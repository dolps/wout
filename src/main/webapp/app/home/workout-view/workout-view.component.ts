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

    constructor(private workOutService: WorkOutService) {
    }

    ngOnInit() {
        console.log('programID: ' + this.program.id);
    }

    ngOnChanges(changes: SimpleChanges) {
        console.log(JSON.stringify(changes));
        this.getAllWorkOuts();
        // changes.prop contains the old and the new value...
    }

    onSelectWorkout(workOut: WorkOut) {
        this.selectedWorkout = workOut;
    }

    getAllWorkOuts() {
        this.workOutService.query({'programId.in': this.program.id}).subscribe((res) => {
            this.workouts = res.body;
        });
    }

}
