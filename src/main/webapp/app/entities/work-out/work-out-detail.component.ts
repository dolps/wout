import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { WorkOut } from './work-out.model';
import { WorkOutService } from './work-out.service';

@Component({
    selector: 'jhi-work-out-detail',
    templateUrl: './work-out-detail.component.html'
})
export class WorkOutDetailComponent implements OnInit, OnDestroy {

    workOut: WorkOut;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private workOutService: WorkOutService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWorkOuts();
    }

    load(id) {
        this.workOutService.find(id)
            .subscribe((workOutResponse: HttpResponse<WorkOut>) => {
                this.workOut = workOutResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWorkOuts() {
        this.eventSubscriber = this.eventManager.subscribe(
            'workOutListModification',
            (response) => this.load(this.workOut.id)
        );
    }
}
