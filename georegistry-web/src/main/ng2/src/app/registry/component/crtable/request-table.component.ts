import { Component, OnInit, EventEmitter, Output, ViewEncapsulation } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';

import { ErrorModalComponent } from '../../../shared/component/modals/error-modal.component';
import { ChangeRequest, PageEvent, AbstractAction, AddChildAction, SetParentAction, CreateGeoObjectAction, RemoveChildAction, UpdateGeoObjectAction } from '../../model/crtable';

import { ChangeRequestService } from '../../service/change-request.service';
import { LocalizationService } from '../../../shared/service/localization.service';
import { ActionDetailModalComponent } from './action-detail/action-detail-modal.component'

@Component( {

    selector: 'request-table',
    templateUrl: './request-table.component.html',
    styleUrls: ['./request-table.css'],
    encapsulation: ViewEncapsulation.None
} )
export class RequestTableComponent {

	objectKeys = Object.keys;

    bsModalRef: BsModalRef;

	requests: ChangeRequest[] = [];

	actions: AbstractAction[] | SetParentAction[] | AddChildAction[] | CreateGeoObjectAction[] | RemoveChildAction[] | UpdateGeoObjectAction[];

	columns: any[] = [];
	
	toggleId: string;

	filterCriteria: string = 'ALL';

    constructor( private service: ChangeRequestService, private modalService: BsModalService, private localizationService: LocalizationService ) {
        this.columns = [
            { name: localizationService.decode( 'change.request.user' ), prop: 'createdBy', sortable: false },
            { name: localizationService.decode( 'change.request.createDate' ), prop: 'createDate', sortable: false, width: 195 },
            { name: localizationService.decode( 'change.request.status' ), prop: 'approvalStatus', sortable: false }
        ];

        this.refresh();
    }

    refresh(): void {

            this.service.getAllRequests("ALL").then( requests => {

				this.requests = requests;

            } ).catch(( response: HttpErrorResponse ) => {
                this.error( response );
            } )

    }


    onSelect( selected: any ): void {

		// this.request = selected.selected;

        this.service.getAllActions( selected.selected[0].oid ).then(actions => {
			
			this.actions = actions;
        } ).catch(( err: HttpErrorResponse) => {
            this.error( err );
        } );
    }

    onExecute(changeRequest: ChangeRequest): void {

        if ( changeRequest != null ) {
            this.service.execute( changeRequest.oid ).then( request => {
                changeRequest = request;

                // TODO: Determine if there is a way to update an individual record
                this.refresh();
            } ).catch(( response: HttpErrorResponse ) => {
                this.error( response );
            } );
        }
	}
	
	// onConfirmChangeRequest(request: any): void {

    //     if ( request != null ) {
    //         this.service.confirmChangeRequest( request.oid ).then( request => {
    //             this.request = request;

    //             // TODO: Determine if there is a way to update an individual record
    //             this.refresh();
    //         } ).catch(( response: HttpErrorResponse ) => {
    //             this.error( response );
    //         } );
    //     }
	// }
	
	applyActionStatusProperties(action: any ): void {
		// var action = JSON.parse(JSON.stringify(this.action));
		// action.geoObjectJson = this.attributeEditor.getGeoObject();

		this.service.applyActionStatusProperties(action).then( response => {
			// this.crtable.refresh()
		} ).catch(( err: HttpErrorResponse ) => {
			this.error( err );
		} );
	}

    onApproveAll(changeRequest: ChangeRequest): void {

        if ( changeRequest != null ) {
            this.service.approveAllActions( changeRequest.oid, this.actions ).then( actions => {
                this.actions = actions;
            } ).catch(( response: HttpErrorResponse ) => {
                this.error( response );
            } );
        }
    }

    onRejectAll(changeRequest: ChangeRequest): void {
        if ( changeRequest != null ) {
            this.service.rejectAllActions( changeRequest.oid, this.actions ).then( actions => {
                this.actions = actions;

                // TODO: Determine if there is a way to update an individual record
                // this.refresh();
            } ).catch(( response: HttpErrorResponse ) => {
                this.error( response );
            } );
        }
    }

    public error( err: HttpErrorResponse ): void {
        // Handle error
        if ( err !== null ) {
            let bsModalRef = this.modalService.show( ErrorModalComponent, { backdrop: true } );
            bsModalRef.content.message = ( err.error.localizedMessage || err.error.message || err.message );
        }
	}
	
	requestTrackBy(index: number, request: ChangeRequest) {
    	return request.oid;
	}
	
	toggle(event: any, oid: string): void {

		if(!event.target.parentElement.className.includes("btn") && !event.target.className.includes("btn")){
			if(this.toggleId === oid){
				this.toggleId = null;
			}
			else {
				this.toggleId = oid;
				this.onSelect({selected:[{oid:oid}]});
			}
		}
	}

	filter(criteria: string): void {

		   this.service.getAllRequests(criteria).then( requests => {
				this.requests = requests;
            } ).catch(( response: HttpErrorResponse ) => {
                this.error( response );
			} )
			
		this.filterCriteria = criteria;
	}

	setActionStatus(action: AbstractAction, status:string): void {
		action.approvalStatus = status;

		this.applyActionStatusProperties(action);
	}

	getActiveDetailComponent(action: AbstractAction) : any {
      // TODO: I know this scales poorly to lots of different action types but I'm not sure how to do it better
      if (action.actionType.endsWith('CreateGeoObjectAction') || action.actionType.endsWith('UpdateGeoObjectAction'))
      {
        // return this.cuDetail;
      }
    //   if (this.arDetail != null && (this.action.actionType.endsWith('AddChildAction') || this.action.actionType.endsWith('RemoveChildAction')))
    //   {
    //     return this.arDetail;
	//   }
	
	  return action;
	}
	
    showActionDetail( action: any ) {

		this.bsModalRef = this.modalService.show( ActionDetailModalComponent, {
            animated: true,
            backdrop: true,
            ignoreBackdropClick: true,
        } );
		this.bsModalRef.content.curAction = action;
      
    //   var detail = this.getActiveDetailComponent();
    //   if (detail != null)
    //   {
        // action.onSelect(action);
    //   }
    }

}
