import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { WorkOutComponent } from './work-out.component';
import { WorkOutDetailComponent } from './work-out-detail.component';
import { WorkOutPopupComponent } from './work-out-dialog.component';
import { WorkOutDeletePopupComponent } from './work-out-delete-dialog.component';

export const workOutRoute: Routes = [
    {
        path: 'work-out',
        component: WorkOutComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WorkOuts'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'work-out/:id',
        component: WorkOutDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WorkOuts'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const workOutPopupRoute: Routes = [
    {
        path: 'work-out-new',
        component: WorkOutPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WorkOuts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'work-out/:id/edit',
        component: WorkOutPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WorkOuts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'work-out/:id/delete',
        component: WorkOutDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'WorkOuts'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
