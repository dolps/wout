/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { WoutTestModule } from '../../../test.module';
import { ProgramDetailComponent } from '../../../../../../main/webapp/app/entities/program/program-detail.component';
import { ProgramService } from '../../../../../../main/webapp/app/entities/program/program.service';
import { Program } from '../../../../../../main/webapp/app/entities/program/program.model';

describe('Component Tests', () => {

    describe('Program Management Detail Component', () => {
        let comp: ProgramDetailComponent;
        let fixture: ComponentFixture<ProgramDetailComponent>;
        let service: ProgramService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [WoutTestModule],
                declarations: [ProgramDetailComponent],
                providers: [
                    ProgramService
                ]
            })
            .overrideTemplate(ProgramDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProgramDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProgramService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Program(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.program).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
