import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WorkOut } from './work-out.model';
import { WorkOutService } from './work-out.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-work-out',
    templateUrl: './work-out.component.html'
})
export class WorkOutComponent implements OnInit, OnDestroy {
workOuts: WorkOut[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private workOutService: WorkOutService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.workOutService.query().subscribe(
            (res: HttpResponse<WorkOut[]>) => {
                this.workOuts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInWorkOuts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: WorkOut) {
        return item.id;
    }
    registerChangeInWorkOuts() {
        this.eventSubscriber = this.eventManager.subscribe('workOutListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
