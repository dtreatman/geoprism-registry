import { Input, Component, OnInit, OnDestroy, ViewChild, ElementRef, TemplateRef, ChangeDetectorRef, ViewEncapsulation, HostListener } from '@angular/core';
import { HttpHeaders, HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { Observable } from 'rxjs/Observable';

import { HierarchyOverTime } from '../../../../model/registry';
import { AbstractAction } from '../../../../model/crtable';

import { RegistryService } from '../../../../service/registry.service';
import { ChangeRequestService } from '../../../../service/change-request.service';

import { ErrorModalComponent } from '../../../../../shared/component/modals/error-modal.component';
import { ComponentCanDeactivate } from "../../../../../shared/service/pending-changes-guard";


import { ActionDetailComponent } from '../action-detail-modal.component';

declare var acp: any;
declare var $: any;

@Component( {

    selector: 'set-parent-detail',
    templateUrl: './set-parent-detail.component.html',
    styleUrls: [],
    encapsulation: ViewEncapsulation.None
} )
export class SetParentDetailComponent implements ComponentCanDeactivate, ActionDetailComponent {

    @Input() action: any;

    hierarchies: HierarchyOverTime[] = [];
    readOnly: boolean = true;

    bsModalRef: BsModalRef;
    
    /*
     * Date in which the modal is shown for
     */
    dateStr: string = null;

    /*
     * Date in which the modal is shown for
     */
    forDate: Date = null;    

    constructor( private router: Router, private changeRequestService: ChangeRequestService, private modalService: BsModalService, private registryService: RegistryService ) {
        this.forDate = new Date();

        const day = this.forDate.getUTCDate();
        this.dateStr = this.forDate.getUTCFullYear() + "-" + ( this.forDate.getUTCMonth() + 1 ) + "-" + ( day < 10 ? "0" : "" ) + day;
    }

    ngOnInit(): void {

        this.hierarchies = this.action.json;

        this.onSelect( this.action );
    }

    applyAction() {
        var action = JSON.parse( JSON.stringify( this.action ) );

        this.changeRequestService.applyAction( action ).then( response => {
            this.endEdit();
        } ).catch(( err: HttpErrorResponse ) => {
            this.error( err );
        } );
    }
    
    handleDateChange(): void {
        this.forDate = new Date( Date.parse( this.dateStr ) );
    }    

    onSelect( action: AbstractAction ) {

    }

    // Big thanks to https://stackoverflow.com/questions/35922071/warn-user-of-unsaved-changes-before-leaving-page
    @HostListener( 'window:beforeunload' )
    canDeactivate(): Observable<boolean> | boolean {
        if ( !this.readOnly ) {
            //event.preventDefault();
            //event.returnValue = 'Are you sure?';
            //return 'Are you sure?';

            return false;
        }

        return true;
    }

    afterDeactivate( isDeactivating: boolean ) {
        if ( isDeactivating && !this.readOnly ) {
            this.unlockActionSync();
        }
    }

    startEdit(): void {
        this.lockAction();
    }

    public endEdit(): void {
        this.unlockAction();
    }

    lockAction() {
        this.changeRequestService.lockAction( this.action.oid ).then( response => {
            this.readOnly = false;
        } ).catch(( err: HttpErrorResponse ) => {
            this.error( err );
        } );
    }

    unlockAction() {
        this.changeRequestService.unlockAction( this.action.oid ).then( response => {
            this.readOnly = true;
        } ).catch(( err: HttpErrorResponse ) => {
            this.error( err );
        } );
    }

    // https://stackoverflow.com/questions/4945932/window-onbeforeunload-ajax-request-in-chrome
    unlockActionSync() {
        $.ajax( {
            url: acp + '/changerequest/unlockAction',
            method: "POST",
            data: { actionId: this.action.oid },
            success: function( a ) {

            },
            async: false
        } );
    }
    // https://www.tivix.com/blog/making-promises-in-a-synchronous-manner
    // unlockActionSync()
    // {
    //   console.log("UnlockActionSync", "Checkpoint 1");
    //
    //   let makeMeLookSync = fn => {
    //     console.log("UnlockActionSync", "Checkpoint 2");
    //
    //     let iterator = fn();
    //     let loop = result => {
    //       console.log("UnlockActionSync", "Checkpoint OuterSnycLoop", result.done, result.value);
    //
    //       !result.done && result.value.then(res => {
    //         console.log("UnlockActionSync", "Checkpoint InnerSnycLoop");
    //         loop(iterator.next(res));
    //       })
    //     };
    //
    //     console.log("UnlockActionSync", "Checkpoint Loop Start", iterator);
    //     loop(iterator.next());
    //   };
    //
    //   var crs = this.changeRequestService;
    //   var actionOid = this.action.oid;
    //
    //   makeMeLookSync(function* () {
    //     console.log("UnlockActionSync Checkpoint", "Start MakeMeLookSync");
    //
    //     yield crs.unlockAction(actionOid).then( response => {
    //       this.readOnly = true;
    //     } ).catch(( err: HttpErrorResponse ) => {
    //       this.error( err );
    //     } );
    //
    //     console.log("UnlockActionSync Checkpoint", "End MakeMeLookSync");
    //   });
    // }

    public error( err: HttpErrorResponse ): void {
        console.log( err );

        // Handle error
        if ( err !== null ) {
            // TODO: add error modal
            this.bsModalRef = this.modalService.show( ErrorModalComponent, { backdrop: true } );
            this.bsModalRef.content.message = ( err.error.localizedMessage || err.error.message || err.message );
        }

    }

}
