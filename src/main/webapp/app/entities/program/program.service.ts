import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Program } from './program.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Program>;

@Injectable()
export class ProgramService {

    private resourceUrl =  SERVER_API_URL + 'api/programs';

    constructor(private http: HttpClient) { }

    create(program: Program): Observable<EntityResponseType> {
        const copy = this.convert(program);
        return this.http.post<Program>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(program: Program): Observable<EntityResponseType> {
        const copy = this.convert(program);
        return this.http.put<Program>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Program>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Program[]>> {
        const options = createRequestOption(req);
        return this.http.get<Program[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Program[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Program = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Program[]>): HttpResponse<Program[]> {
        const jsonResponse: Program[] = res.body;
        const body: Program[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Program.
     */
    private convertItemFromServer(program: Program): Program {
        const copy: Program = Object.assign({}, program);
        return copy;
    }

    /**
     * Convert a Program to a JSON which can be sent to the server.
     */
    private convert(program: Program): Program {
        const copy: Program = Object.assign({}, program);
        return copy;
    }
}
