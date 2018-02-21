import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Program } from './program.model';
import { ProgramService } from './program.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-program',
    templateUrl: './program.component.html'
})
export class ProgramComponent implements OnInit, OnDestroy {
programs: Program[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private programService: ProgramService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.programService.query().subscribe(
            (res: HttpResponse<Program[]>) => {
                this.programs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPrograms();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Program) {
        return item.id;
    }
    registerChangeInPrograms() {
        this.eventSubscriber = this.eventManager.subscribe('programListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
