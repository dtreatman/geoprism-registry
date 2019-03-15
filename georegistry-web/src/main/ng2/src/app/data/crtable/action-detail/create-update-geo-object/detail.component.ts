import { Input, Component, OnInit, ViewChild, ElementRef, TemplateRef, ChangeDetectorRef } from '@angular/core';
import { Headers, Http, RequestOptions, Response, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';

import { EventService } from '../../../../event/event.service';

import { ChangeRequestService } from '../../../../service/change-request.service';
import { ActionTableComponent } from '../../action-table.component';

import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';

import { ErrorModalComponent } from '../../../../core/modals/error-modal.component';

import { GeoObject, GeoObjectType } from '../../../../model/registry';

import { RegistryService } from '../../../../service/registry.service';

import { AbstractAction } from '../../crtable';

declare var acp: any;

@Component({
  
  selector: 'crtable-detail-create-geo-object',
  templateUrl: './detail.component.html',
  styleUrls: ['../all-action-detail.css']
})
export class CreateUpdateGeoObjectDetailComponent {

  @Input() action: any;
  
  preGeoObject: GeoObject = null;
  
  postGeoObject: GeoObject = null;
  
  geoObjectType : GeoObjectType = null;
  
  @Input() crtable: ActionTableComponent;
  
  private bsModalRef: BsModalRef;

  constructor(private router: Router, private eventService: EventService, private http: Http, private changeRequestService: ChangeRequestService, private modalService: BsModalService, private registryService: RegistryService) { 
	  
  }
  
  ngOnInit(): void {
    this.onSelect(this.action);
  }
  
  applyAction()
  {
    this.changeRequestService.applyAction(this.action).then( response => {
          this.crtable.refresh()
      } ).catch(( err: Response ) => {
          this.error( err.json() );
      } );
  }
  
  onSelect(action: AbstractAction)
  {
    this.action = action;
    
    this.postGeoObject = this.action.geoObjectJson;
    this.geoObjectType = this.action.geoObjectType;
    
    
    
    this.preGeoObject = JSON.parse(JSON.stringify(this.postGeoObject));
    
    // TODO : If we decide we want the diffing logic to diff based on what exists currently in the DB, use this code instead to set the preGeoObject:  
    //this.registryService.getGeoObjectByCode(this.postGeoObject.properties.code, this.geoObjectType.code)
    //    .then(geoObject => {
    //        this.preGeoObject = geoObject;
    //
    //    }).catch((err: Response) => {
    //        this.error(err.json());
    //    });
  }
  
  unlockAction()
  {
    this.changeRequestService.unlockAction(this.action.oid).then( response => {
          this.crtable.refresh();
      } ).catch(( err: Response ) => {
          this.error( err.json() );
      } );
  }
  
  public error( err: any ): void {
      // Handle error
      if ( err !== null ) {
        // TODO: add error modal
          this.bsModalRef = this.modalService.show( ErrorModalComponent, { backdrop: true } );
          this.bsModalRef.content.message = ( err.localizedMessage || err.message );
      }

  }

}
