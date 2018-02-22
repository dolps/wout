import {Component, Input, OnInit} from '@angular/core';
import {WorkOut, WorkOutService} from '../../entities/work-out';
import {Program} from '../../entities/program';

@Component({
    selector: 'jhi-workout-view',
    templateUrl: './workout-view.component.html',
    styles: []
})
export class WorkoutViewComponent implements OnInit {
    @Input() program: Program;
    workouts: WorkOut[];
    selectedWorkout: WorkOut;

    constructor(private workOutService: WorkOutService) {
    }

    ngOnInit() {
        this.workOutService.query({'program': this.program.id}).subscribe((res) => {
            this.workouts = res.body;
        });
    }

    onSelectWorkout(workOut: WorkOut) {
        this.selectedWorkout = workOut;
    }

}
