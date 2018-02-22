import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WorkOut } from './work-out.model';
import { WorkOutPopupService } from './work-out-popup.service';
import { WorkOutService } from './work-out.service';
import { Program, ProgramService } from '../program';

@Component({
    selector: 'jhi-work-out-dialog',
    templateUrl: './work-out-dialog.component.html'
})
export class WorkOutDialogComponent implements OnInit {

    workOut: WorkOut;
    isSaving: boolean;

    programs: Program[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private workOutService: WorkOutService,
        private programService: ProgramService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.programService.query()
            .subscribe((res: HttpResponse<Program[]>) => { this.programs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.workOut.id !== undefined) {
            this.subscribeToSaveResponse(
                this.workOutService.update(this.workOut));
        } else {
            this.subscribeToSaveResponse(
                this.workOutService.create(this.workOut));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<WorkOut>>) {
        result.subscribe((res: HttpResponse<WorkOut>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: WorkOut) {
        this.eventManager.broadcast({ name: 'workOutListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProgramById(index: number, item: Program) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-work-out-popup',
    template: ''
})
export class WorkOutPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private workOutPopupService: WorkOutPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.workOutPopupService
                    .open(WorkOutDialogComponent as Component, params['id']);
            } else {
                this.workOutPopupService
                    .open(WorkOutDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
