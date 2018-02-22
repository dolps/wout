import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WorkOut } from './work-out.model';
import { WorkOutPopupService } from './work-out-popup.service';
import { WorkOutService } from './work-out.service';

@Component({
    selector: 'jhi-work-out-delete-dialog',
    templateUrl: './work-out-delete-dialog.component.html'
})
export class WorkOutDeleteDialogComponent {

    workOut: WorkOut;

    constructor(
        private workOutService: WorkOutService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.workOutService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'workOutListModification',
                content: 'Deleted an workOut'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-work-out-delete-popup',
    template: ''
})
export class WorkOutDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private workOutPopupService: WorkOutPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.workOutPopupService
                .open(WorkOutDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
