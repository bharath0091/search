import { Injectable }    from '@angular/core';


import {Observable} from "rxjs";

import {SearchResultModel} from "./search-result.model";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";

@Injectable()
export class SearchResultService {

    constructor(private http : HttpClient) {}

  search(searchText: string): Observable<SearchResultModel[]> {
    const options = { params: new HttpParams().set('q', searchText),
                      headers: new HttpHeaders({'Content-Type':  'application/json'})
                    };

    return this.http.get<SearchResultModel[]>(window.location.protocol +  "//" +
                                                window.location.host + "/search", options);
  }
}
