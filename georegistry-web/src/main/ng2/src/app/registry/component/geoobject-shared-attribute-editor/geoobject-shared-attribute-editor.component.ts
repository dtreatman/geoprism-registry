import { Component, OnInit, ViewChild, ElementRef, TemplateRef, ChangeDetectorRef, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { DatePipe } from '@angular/common';

import { LocalizedValue } from '../../../shared/model/core';
import { LocalizationService } from '../../../shared/service/localization.service';

import { ManageVersionsModalComponent } from './manage-versions-modal.component';

import { RegistryService } from '../../service/registry.service';
import { ChangeRequestService } from '../../service/change-request.service';


import { IOService } from '../../service/io.service';
import { GeoObjectType, GeoObjectOverTime, Attribute, AttributeTerm, AttributeDecimal, Term, PRESENT } from '../../model/registry';

import { ToEpochDateTimePipe } from '../../pipe/to-epoch-date-time.pipe';

import Utils from '../../utility/Utils';

declare var acp: string;


@Component( {
    selector: 'geoobject-shared-attribute-editor',
    templateUrl: './geoobject-shared-attribute-editor.component.html',
    styleUrls: ['./geoobject-shared-attribute-editor.css'],
    providers: [DatePipe]
} )

/**
 * This component is shared between:
 * - crtable (create-update-geo-object action detail)
 * - change-request (for submitting change requests)
 * - master list geoobject editing widget
 * 
 * Be wary of changing this component for one usecase and breaking other usecases!
 */
export class GeoObjectSharedAttributeEditorComponent implements OnInit {

    private bsModalRef: BsModalRef;

    /*
	 * The current state of the GeoObject in the GeoRegistry
	 */
    @Input() preGeoObject: GeoObjectOverTime = null;

    calculatedPreObject: any = {};

    /*
	 * The state of the GeoObject being modified
	 */
    @Input() postGeoObject: GeoObjectOverTime = null;

    calculatedPostObject: any = {};

    @Input() geoObjectType: GeoObjectType;

    @Input() attributeExcludes: string[] = [];

    @Input() forDate: Date = new Date();

    @Input() readOnly: boolean = false;

    @Input() isNew: boolean = false;

    @Input() isEditingGeometries = false;

    @Input() isGeometryInlined = false;

    @ViewChild( "geometryEditor" ) geometryEditor;

    @Output() valid = new EventEmitter<boolean>();

    modifiedTermOption: Term = null;
    currentTermOption: Term = null;
    isValid: boolean = true;

    geoObjectAttributeExcludes: string[] = ["uid", "sequence", "type", "lastUpdateDate", "createDate"];

    @ViewChild( "attributeForm" ) attributeForm;

    constructor( private service: IOService, private modalService: BsModalService, private changeDetectorRef: ChangeDetectorRef,
        private registryService: RegistryService, private elRef: ElementRef, private changeRequestService: ChangeRequestService,
        private datePipe: DatePipe, private toEpochDateTimePipe: ToEpochDateTimePipe, private lService: LocalizationService ) {

    }

    ngOnInit(): void {
        this.preGeoObject = new GeoObjectOverTime( this.geoObjectType, JSON.parse( JSON.stringify( this.preGeoObject ) ).attributes ); // We're about to heavily modify this object. We don't want to muck with the original copy they sent us.

        if ( this.postGeoObject == null ) {
            this.postGeoObject = new GeoObjectOverTime( this.geoObjectType, JSON.parse( JSON.stringify( this.preGeoObject ) ).attributes ); // Object.assign is a shallow copy. We want a deep copy.
        }
        else {
            this.postGeoObject = new GeoObjectOverTime( this.geoObjectType, JSON.parse( JSON.stringify( this.postGeoObject ) ).attributes ); // We're about to heavily modify this object. We don't want to muck with the original copy they sent us.
        }

        this.attributeForm.statusChanges.subscribe( result => {
            this.isValid = ( result === "VALID" || result === "DISABLED" );

            this.valid.emit( this.isValid );
        } );

        if ( this.attributeExcludes != null ) {
            this.geoObjectAttributeExcludes.push.apply( this.geoObjectAttributeExcludes, this.attributeExcludes );

            if ( !this.isGeometryInlined ) {
                this.geoObjectAttributeExcludes.push( "geometry" );
            }
        }

        let geomAttr = null;
        for ( var i = 0; i < this.geoObjectType.attributes.length; ++i ) {
            if ( this.geoObjectType.attributes[i].code === 'geometry' ) {
                geomAttr = this.geoObjectType.attributes[i];
            }
        }
        if ( geomAttr == null ) {
            let geometry: Attribute = new Attribute( "geometry", "geometry", new LocalizedValue( "Geometry", null ), new LocalizedValue( "Geometry", null ), true, false, false, true );
            this.geoObjectType.attributes.push( geometry );
        }

        this.calculate();
    }

    ngOnChanges( changes: SimpleChanges ) {
        if ( changes['forDate'] ) {
            this.calculate();
        }
    }

    calculate(): void {
        this.calculatedPreObject = this.calculateCurrent( this.preGeoObject );
        this.calculatedPostObject = this.calculateCurrent( this.postGeoObject );

        if ( this.geometryEditor != null ) {
            this.geometryEditor.reload();
        }
    }

    calculateCurrent( goot: GeoObjectOverTime ): any {
        const object = {};

        const time = this.forDate.getTime();

        for ( let i = 0; i < this.geoObjectType.attributes.length; ++i ) {
            let attr = this.geoObjectType.attributes[i];
            object[attr.code] = null;

            if ( attr.type === 'local' ) {
                object[attr.code] = this.lService.create();
            }

            if ( attr.isChangeOverTime ) {
                let values = goot.attributes[attr.code].values;

                values.forEach( vot => {

                    const startDate = Date.parse( vot.startDate );
                    const endDate = Date.parse( vot.endDate );

                    if ( time >= startDate && time <= endDate ) {

                        if ( attr.type === 'local' ) {
                            object[attr.code] = {
                                startDate: this.formatDate( vot.startDate ),
                                endDate: this.formatDate( vot.endDate ),
                                value: JSON.parse( JSON.stringify( vot.value ) )
                            };
                        }
                        else if ( attr.type === 'term' && vot.value != null && Array.isArray( vot.value ) && vot.value.length > 0 ) {
                            object[attr.code] = {
                                startDate: this.formatDate( vot.startDate ),
                                endDate: this.formatDate( vot.endDate ),
                                value: vot.value[0]
                            };
                        }
                        else {
                            object[attr.code] = {
                                startDate: this.formatDate( vot.startDate ),
                                endDate: this.formatDate( vot.endDate ),
                                value: vot.value
                            };
                        }
                    }
                } );
            }
            else {
                object[attr.code] = goot.attributes[attr.code];
            }
        }

        return object;
    }

    formatDate( date: string ): string {
        if ( date === PRESENT ) {
            return this.lService.localize("changeovertime", "present");
        }

        return date;
    }

    handleChangeCode( e: any ): void {
        this.postGeoObject.attributes.code = this.calculatedPostObject['code'];
        //        
        //        console.log(this.calculatedPostObject['code'])
        //        console.log(e)
    }

    onManageAttributeVersions( attribute: Attribute ): void {
        this.bsModalRef = this.modalService.show( ManageVersionsModalComponent, {
            animated: true,
            backdrop: true,
            ignoreBackdropClick: true,
        } );

        // TODO: sending the properties like this is wrong
        this.bsModalRef.content.geoObjectOverTime = this.postGeoObject;
        this.bsModalRef.content.geoObjectType = this.geoObjectType;
        this.bsModalRef.content.isNewGeoObject = this.isNew;
        this.bsModalRef.content.attribute = attribute;
        this.bsModalRef.content.onAttributeVersionChange.subscribe( versionObj => {
            this.calculate();
        } );
        this.bsModalRef.content.tfInit();
    }

    onManageGeometryVersions(): void {
        let geometry = null;
        for ( var i = 0; i < this.geoObjectType.attributes.length; ++i ) {
            if ( this.geoObjectType.attributes[i].code === 'geometry' ) {
                geometry = this.geoObjectType.attributes[i];
            }
        }

        this.onManageAttributeVersions( geometry );
    }

    isDifferentText( attribute: Attribute ): boolean {
        if ( this.calculatedPostObject[attribute.code] == null && this.calculatedPreObject[attribute.code] != null ) {
            return true;
        }

        return ( this.calculatedPostObject[attribute.code].value && this.calculatedPostObject[attribute.code].value.trim() !== this.calculatedPreObject[attribute.code].value );
    }

    isDifferentValue( attribute: Attribute ): boolean {
        if ( this.calculatedPostObject[attribute.code] == null && this.calculatedPreObject[attribute.code] != null ) {
            return true;
        }

        return ( this.calculatedPostObject[attribute.code].value && this.calculatedPostObject[attribute.code].value !== this.calculatedPreObject[attribute.code].value );
    }

    onSelectPropertyOption( event: any, option: any ): void {
        this.currentTermOption = JSON.parse( JSON.stringify( this.modifiedTermOption ) );
    }

    getGeoObjectTypeTermAttributeOptions( termAttributeCode: string ) {
        for ( let i = 0; i < this.geoObjectType.attributes.length; i++ ) {
            let attr: any = this.geoObjectType.attributes[i];

            if ( attr.type === "term" && attr.code === termAttributeCode ) {

                attr = <AttributeTerm>attr;
                let attrOpts = attr.rootTerm.children;

                if ( attr.code === "status" ) {
                    return Utils.removeStatuses( JSON.parse( JSON.stringify( attrOpts ) ) );
                }
                else {
                    return attrOpts;
                }
            }
        }

        return null;
    }

    isStatusChanged( post, pre ) {

        if ( pre != null && post == null ) {
            return true;
        }

        if ( pre == null || post == null || pre.length == 0 || post.length == 0 ) {
            return false;
        }

        var preCompare = pre;
        if ( Array.isArray( pre ) ) {
            preCompare = pre[0];
        }

        var postCompare = post;
        if ( Array.isArray( post ) ) {
            postCompare = post[0];
        }

        return preCompare !== postCompare;
    }

    getTypeDefinition( key: string ): string {
        // let attrs = this.geoObjectType.attributes;


        // attrs.attributes.forEach(attr => {
        for ( let i = 0; i < this.geoObjectType.attributes.length; i++ ) {
            let attr = this.geoObjectType.attributes[i];

            if ( attr.code === key ) {
                return attr.type;
            }
        }

        return null;
    }

    public getIsValid(): boolean {
        return this.isValid;
    }

    public getGeoObject(): any {
        return this.postGeoObject;

        //        // The front-end uses the 'yyyy-mm-dd' date format. Our backend expects dates in epoch format.
        //        var submitGO = JSON.parse( JSON.stringify( this.postGeoObject ) );
        //        for ( var i = 0; i < this.geoObjectType.attributes.length; ++i ) {
        //            var attr = this.geoObjectType.attributes[i];
        //
        //            if ( attr.type === "date" && this.postGeoObject.properties[attr.code] != null ) {
        //                var parts = this.postGeoObject.properties[attr.code].split( '-' );
        //                var date = new Date( parts[0], parts[1] - 1, parts[2] );
        //
        //                submitGO.properties[attr.code] = date.getTime();
        //            }
        //        }
        //
        //        return submitGO;
    }
}
