import {Input, Component, OnInit} from '@angular/core';

@Component({
    selector: 'jhi-create-entity-button',
    templateUrl: './create-entity-button.component.html',
    styles: []
})
export class CreateEntityButtonComponent implements OnInit {
    @Input()
    entity_new: string;
    @Input()
    buttonText: string;

    constructor() {
    }

    ngOnInit() {
    }

}
