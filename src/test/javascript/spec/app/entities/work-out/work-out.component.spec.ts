/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { WoutTestModule } from '../../../test.module';
import { WorkOutComponent } from '../../../../../../main/webapp/app/entities/work-out/work-out.component';
import { WorkOutService } from '../../../../../../main/webapp/app/entities/work-out/work-out.service';
import { WorkOut } from '../../../../../../main/webapp/app/entities/work-out/work-out.model';

describe('Component Tests', () => {

    describe('WorkOut Management Component', () => {
        let comp: WorkOutComponent;
        let fixture: ComponentFixture<WorkOutComponent>;
        let service: WorkOutService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WoutTestModule],
                declarations: [WorkOutComponent],
                providers: [
                    WorkOutService
                ]
            })
            .overrideTemplate(WorkOutComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WorkOutComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WorkOutService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new WorkOut(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.workOuts[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
