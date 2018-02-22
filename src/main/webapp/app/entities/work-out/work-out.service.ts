import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { WorkOut } from './work-out.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<WorkOut>;

@Injectable()
export class WorkOutService {

    private resourceUrl =  SERVER_API_URL + 'api/work-outs';

    constructor(private http: HttpClient) { }

    create(workOut: WorkOut): Observable<EntityResponseType> {
        const copy = this.convert(workOut);
        return this.http.post<WorkOut>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(workOut: WorkOut): Observable<EntityResponseType> {
        const copy = this.convert(workOut);
        return this.http.put<WorkOut>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<WorkOut>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<WorkOut[]>> {
        const options = createRequestOption(req);
        return this.http.get<WorkOut[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<WorkOut[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: WorkOut = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<WorkOut[]>): HttpResponse<WorkOut[]> {
        const jsonResponse: WorkOut[] = res.body;
        const body: WorkOut[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to WorkOut.
     */
    private convertItemFromServer(workOut: WorkOut): WorkOut {
        const copy: WorkOut = Object.assign({}, workOut);
        return copy;
    }

    /**
     * Convert a WorkOut to a JSON which can be sent to the server.
     */
    private convert(workOut: WorkOut): WorkOut {
        const copy: WorkOut = Object.assign({}, workOut);
        return copy;
    }
}
