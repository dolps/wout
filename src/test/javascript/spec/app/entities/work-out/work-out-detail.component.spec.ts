/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WoutTestModule } from '../../../test.module';
import { WorkOutDetailComponent } from '../../../../../../main/webapp/app/entities/work-out/work-out-detail.component';
import { WorkOutService } from '../../../../../../main/webapp/app/entities/work-out/work-out.service';
import { WorkOut } from '../../../../../../main/webapp/app/entities/work-out/work-out.model';

describe('Component Tests', () => {

    describe('WorkOut Management Detail Component', () => {
        let comp: WorkOutDetailComponent;
        let fixture: ComponentFixture<WorkOutDetailComponent>;
        let service: WorkOutService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WoutTestModule],
                declarations: [WorkOutDetailComponent],
                providers: [
                    WorkOutService
                ]
            })
            .overrideTemplate(WorkOutDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WorkOutDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WorkOutService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new WorkOut(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.workOut).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
