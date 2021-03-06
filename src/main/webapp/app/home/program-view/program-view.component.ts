import {Component, Input, OnInit} from '@angular/core';
import {Program} from '../../entities/program';

@Component({
    selector: 'jhi-program-view',
    templateUrl: './program-view.component.html',
    styles: []
})
export class ProgramViewComponent implements OnInit {
    @Input() programs: Program[];
    selectedProgram: Program;
    btnCreateEntity = 'program-new';
    btnCreateTxt = 'create program';

    constructor() {
    }

    ngOnInit() {
    }

    onSelectProgram(program: Program) {
        this.selectedProgram = program;
    }
}
