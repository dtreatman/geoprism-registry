///
/// Copyright (c) 2015 TerraFrame, Inc. All rights reserved.
///
/// This file is part of Runway SDK(tm).
///
/// Runway SDK(tm) is free software: you can redistribute it and/or modify
/// it under the terms of the GNU Lesser General Public License as
/// published by the Free Software Foundation, either version 3 of the
/// License, or (at your option) any later version.
///
/// Runway SDK(tm) is distributed in the hope that it will be useful, but
/// WITHOUT ANY WARRANTY; without even the implied warranty of
/// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
/// GNU Lesser General Public License for more details.
///
/// You should have received a copy of the GNU Lesser General Public
/// License along with Runway SDK(tm).  If not, see <ehttp://www.gnu.org/licenses/>.
///

import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/finally';

import { HierarchyType } from '../model/hierarchy';
import { TreeEntity } from '../model/registry';
import { EventService } from '../../shared/service/event.service';

declare var acp: any;

@Injectable()
export class HierarchyService {

    constructor( private http: HttpClient, private eventService: EventService ) { }

    getHierarchyTypes( types: any ): Promise<HierarchyType[]> {
        let params: HttpParams = new HttpParams();
        params = params.set( 'types', JSON.stringify(types) );
        
        return this.http
            .get<HierarchyType[]>( acp + '/cgr/hierarchytype/get-all', {params: params})
            .toPromise();
    }
    
    addChildToHierarchy( hierarchyCode: string, parentGeoObjectTypeCode: string, childGeoObjectTypeCode: string ): Promise<HierarchyType> {

	   let headers = new HttpHeaders( {
           'Content-Type': 'application/json'
       } );

       this.eventService.start();

       return this.http
           .post<HierarchyType>( acp + '/cgr/hierarchytype/add', JSON.stringify( { hierarchyCode : hierarchyCode, parentGeoObjectTypeCode : parentGeoObjectTypeCode, childGeoObjectTypeCode : childGeoObjectTypeCode } ), { headers: headers } )
           .finally(() => {
               this.eventService.complete();
           } )
           .toPromise();
    }
    
    removeFromHierarchy( hierarchyCode: string, parentGeoObjectTypeCode: string, childGeoObjectTypeCode: string ): Promise<HierarchyType> {

 	   let headers = new HttpHeaders( {
            'Content-Type': 'application/json'
        } );

        this.eventService.start();

        return this.http
            .post<HierarchyType>( acp + '/cgr/hierarchytype/remove', JSON.stringify( { hierarchyCode : hierarchyCode, parentGeoObjectTypeCode : parentGeoObjectTypeCode, childGeoObjectTypeCode : childGeoObjectTypeCode } ), { headers: headers } )
            .finally(() => {
                this.eventService.complete();
            } )
            .toPromise();
     }
    
    createHierarchyType( htJSON: string): Promise<HierarchyType> {

        let headers = new HttpHeaders( {
            'Content-Type': 'application/json'
        });
        
        this.eventService.start();

        return this.http
            .post<HierarchyType>( acp + '/cgr/hierarchytype/create', JSON.stringify({ 'htJSON': htJSON }), { headers: headers } )
            .finally(() => {
                this.eventService.complete();
            } )
            .toPromise();
    }
    
    updateHierarchyType( htJSON: string): Promise<HierarchyType> {

        let headers = new HttpHeaders( {
            'Content-Type': 'application/json'
        });
        
        this.eventService.start();

        return this.http
            .post<HierarchyType>( acp + '/cgr/hierarchytype/update', JSON.stringify({ 'htJSON': htJSON }), { headers: headers } )
            .finally(() => {
                this.eventService.complete();
            } )
            .toPromise();
    }
    
    deleteHierarchyType( code: string ): Promise<TreeEntity> {

        let headers = new HttpHeaders( {
            'Content-Type': 'application/json'
        } );
        
        this.eventService.start();

        return this.http
            .post<TreeEntity>( acp + '/cgr/hierarchytype/delete', { 'code': code }, { headers: headers } )
            .finally(() => {
                this.eventService.complete();
            } )
            .toPromise()
    }

}
