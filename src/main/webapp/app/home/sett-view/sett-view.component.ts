import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Exercise} from '../../entities/exercise';
import {HttpResponse} from '@angular/common/http';
import {Sett, SettService} from '../../entities/sett';

@Component({
    selector: 'jhi-sett-view',
    templateUrl: './sett-view.component.html',
    styles: []
})
export class SettViewComponent implements OnInit, OnChanges {
    @Input() exercise: Exercise;
    setts: Sett[];
    selectedSett: Sett;
    btnCreateEntity = 'sett-new';
    btnCreateTxt = 'create set';

    constructor(private settService: SettService) {
    }

    ngOnInit() {
    }

    ngOnChanges(changes: SimpleChanges) {
        console.log(JSON.stringify(changes));
        this.getAllSetts();
    }

    onSelect(sett) {
        this.selectedSett = sett;
    }

    onCreate() {
        console.log('creating new exercise');
    }

    getAllSetts() {
        this.settService.query({'exerciseId.in': this.exercise.id}).subscribe((res: HttpResponse<Exercise[]>) => {
            this.setts = res.body;
        });
    }

}
