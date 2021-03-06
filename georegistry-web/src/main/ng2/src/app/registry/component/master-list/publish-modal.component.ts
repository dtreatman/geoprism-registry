import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { Subject } from 'rxjs/Subject';
import { HttpErrorResponse } from '@angular/common/http';

import { GeoObjectType, MasterList } from '../../model/registry';

import { RegistryService } from '../../service/registry.service';

import { IOService } from '../../service/io.service';
import { LocalizationService } from '../../../shared/service/localization.service';

@Component( {
    selector: 'publish-modal',
    templateUrl: './publish-modal.component.html',
    styleUrls: []
} )
export class PublishModalComponent implements OnInit {
    message: string = null;
    master: MasterList;

    /*
     * Observable subject for MasterList changes.  Called when an update is successful 
     */
    onMasterListChange: Subject<MasterList>;


    /*
     * List of geo object types from the system
     */
    types: { label: string, code: string }[]

    /*
     * List of geo object types from the system
     */
    readonly: boolean = false;

    /*
     * List of geo object types from the system
     */
    edit: boolean = false;


    constructor( private service: RegistryService, private iService: IOService, private lService: LocalizationService, public bsModalRef: BsModalRef ) { }

    ngOnInit(): void {

        this.onMasterListChange = new Subject();

        if ( this.master == null || !this.readonly ) {
            this.iService.listGeoObjectTypes( true ).then( types => {
                this.types = types;
            } ).catch(( err: HttpErrorResponse) => {
                this.error( err );
            } );

            this.master = {
                oid: null,
                typeCode: '',
                displayLabel: this.lService.create(),
                code: '',
                representativityDate: null,
                publishDate: null,
                listAbstract: '',
                process: '',
                progress: '',
                accessConstraints: '',
                useConstraints: '',
                acknowledgements: '',
                disclaimer: '',
                contactName: '',
                organization: '',
                telephoneNumber: '',
                email: '',
                hierarchies: [],
				leaf: false
            };
        }


    }

    getIsDisabled(event):boolean {
        let elClasses = event.target.classList;
        for(let i=0; i<elClasses.length; i++){
            let c = elClasses[i];
            if(c === 'disabled'){
                return true;
            }
        }

        return false;
    }

    onChange(): void {

        if ( this.master.typeCode != null && this.master.typeCode.length > 0 ) {
            this.iService.getHierarchiesForType( this.master.typeCode, true ).then( hierarchies => {
                this.master.hierarchies = hierarchies;
            } ).catch(( err: HttpErrorResponse) => {
                this.error( err );
            } );
        }
        else {
            this.master.hierarchies = [];
        }
    }

    onSubmit(): void {
        this.service.createMasterList( this.master ).then( response => {

            this.onMasterListChange.next( response );
            this.bsModalRef.hide();
        } ).catch(( err: HttpErrorResponse) => {
            this.error( err );
        } );
    }

    onCancel(): void {
        this.bsModalRef.hide()
    }

    error( err: HttpErrorResponse ): void {
        // Handle error
        if ( err !== null ) {
            this.message = ( err.error.localizedMessage || err.error.message || err.message );
        }
    }

}
