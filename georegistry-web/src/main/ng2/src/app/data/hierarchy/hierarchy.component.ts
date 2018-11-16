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
/// License along with Runway SDK(tm).  If not, see <http://www.gnu.org/licenses/>.
///

import { Component, OnInit, ViewChild, ElementRef, TemplateRef, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';

import { TreeNode, TreeComponent, TreeDropDirective } from 'angular-tree-component';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { ContextMenuService, ContextMenuComponent } from 'ngx-contextmenu';

import { CreateModalComponent } from './modals/create-modal.component';
import { CreateChildModalComponent } from './modals/create-child-modal.component';
import { CreateGeoObjTypeModalComponent } from './modals/create-geoobjtype-modal.component';
import { ConfirmModalComponent } from './modals/confirm-modal.component';
import { ErrorModalComponent } from './modals/error-modal.component';

import { Hierarchy, HierarchyType, HierarchyNode, GeoObjectType, TreeEntity } from './hierarchy';

import { HierarchyService } from '../../service/hierarchy.service';

class Instance {
  active: boolean;
  label: string;   
}

@Component({
  
  selector: 'hierarchies',
  templateUrl: './hierarchy.component.html',
  styleUrls: []
})
export class HierarchyComponent implements OnInit {
  instance : Instance = new Instance();  
  private hierarchies: HierarchyType[];
  private geoObjectTypes: GeoObjectType[] = [];
  private nodes = [] as HierarchyNode[];
  private currentHierarchy: HierarchyType = null;
  private hierarchyTypeDeleteExclusions: string[] = ['AllowedIn', 'IsARelationship'];
  private geoObjectTypeDeleteExclusions: string[] = ['ROOT'];

  /*
   * Reference to the modal current showing
  */
  private bsModalRef: BsModalRef;

  /*
   * Tree component
   */
  @ViewChild( TreeComponent )
  private tree: TreeComponent;
  
  /*
   * Template for the delete confirmation
   */
  @ViewChild( 'confirmTemplate' ) public confirmTemplate: TemplateRef<any>;
  
  /*
   * Template for tree node menu
   */
  @ViewChild( 'nodeMenu' ) public nodeMenuComponent: ContextMenuComponent;

  /*
   * Template for leaf menu
   */
  @ViewChild( 'leafMenu' ) public leafMenuComponent: ContextMenuComponent;
  
  /* 
   * Currently clicked on id for delete confirmation modal 
   */
  current: TreeNode;
  
  
  constructor(private router: Router, private hierarchyService: HierarchyService, private modalService: BsModalService, 
		      private contextMenuService: ContextMenuService, private changeDetectorRef: ChangeDetectorRef) { 
	  
  }

  ngOnInit(): void {
	  this.hierarchyService.getGeoObjectTypes([])
	    .then( types => {
		  this.geoObjectTypes = types;
		  
		  this.geoObjectTypes.sort((a, b) => {
		      if (a.localizedLabel.toLowerCase() < b.localizedLabel.toLowerCase()) return -1;
		      else if (a.localizedLabel.toLowerCase() > b.localizedLabel.toLowerCase()) return 1;
		      else return 0;
		  });
		  
		  let pos = this.getGeoObjectTypePosition("ROOT");
		  if(pos){
			  this.geoObjectTypes.splice(pos, 1);
		  }
	  
		  this.hierarchyService.getHierarchyTypes([])
		    .then( types => {
			  this.setHierarchies(types);
			  
			  this.setNodesOnInit();
	      })
	  }).catch(( err: any ) => {
	      this.error( err.json() );
	  });
  }
  
  ngAfterViewInit() {
	  
  }
  
  public excludeHierarchyTypeDeletes(hierarchy: HierarchyType){
	  return (this.hierarchyTypeDeleteExclusions.indexOf(hierarchy.code) !== -1);
  }
  
  public excludeGeoObjectTypeDeletes(geoObjectType: GeoObjectType){
	  return (this.geoObjectTypeDeleteExclusions.indexOf(geoObjectType.code) !== -1);
  }
  
  private setNodesOnInit():void {
	  for(let i=0; i<this.hierarchies.length; i++){
		let hierarchy = this.hierarchies[i];
		if(hierarchy.rootGeoObjectTypes.length > 0){
		  this.nodes = hierarchy.rootGeoObjectTypes;
		  this.currentHierarchy = hierarchy;
		  break;
		}
	  }
	  
	  setTimeout(() => {
		  this.tree.treeModel.expandAll();
	  }, 1)
  }
  
  private setNodesForHierarchy(hierarchyType: HierarchyType):void {
	  for(let i=0; i<this.hierarchies.length; i++){
		let hierarchy = this.hierarchies[i];
		if(hierarchy.code === hierarchyType.code){
		   this.nodes = hierarchyType.rootGeoObjectTypes;
		   this.currentHierarchy = hierarchy;
		   break;
		}
	  }
	  
	  setTimeout(() => {
		  this.tree.treeModel.expandAll();
	  }, 1)
  }
  
  private getHierarchy(hierarchyId: string):HierarchyType {
	  let target: HierarchyType = null;
	  this.hierarchies.forEach(hierarchy => {
		  if(hierarchyId === hierarchy.code){
			  target = hierarchy;
		  }
	  });
	  
	  return target;
  }
  
  private setHierarchies(data: HierarchyType[]):void{
	  let hierarchies:HierarchyType[] = [];
	  data.forEach( (hierarchyType, index) => {
		  
		  if(hierarchyType.rootGeoObjectTypes.length > 0){
			  this.processHierarchyNodes(hierarchyType.rootGeoObjectTypes);
		  }
		  
		  hierarchies.push(hierarchyType);
		  
	  });
	  
	  this.hierarchies = hierarchies
	  
	  this.hierarchies.sort((a, b) => {
	      if (a.localizedLabel.toLowerCase() < b.localizedLabel.toLowerCase()) return -1;
	      else if (a.localizedLabel.toLowerCase() > b.localizedLabel.toLowerCase()) return 1;
	      else return 0;
	  });
  }
  
  private updateHierarchy(code: string, rootGeoObjectTypes: HierarchyNode[]): void{
	  this.hierarchies.forEach(hierarchy => {
		  if(hierarchy.code === code){
			  hierarchy.rootGeoObjectTypes = rootGeoObjectTypes;
		  }
	  })
  }
  
  private processHierarchyNodes(nodes: HierarchyNode[]){
	  nodes.forEach(node => {
		this.processHierarchyNode(node);
	  });
  }
  
  /**
   * Set properties required by angular-tree-component using recursion.
   */
  private processHierarchyNode(node: HierarchyNode){
	node.label = this.getHierarchyLabel(node.geoObjectType);
	
	node.children.forEach(child => {
	  this.processHierarchyNode(child);
	});
  }
  
  private getHierarchyLabel(geoObjectTypeCode: string):string {
	  let label: string = null;
	  this.geoObjectTypes.forEach(function(gOT){
		  if(gOT.code === geoObjectTypeCode){
			  label = gOT.localizedLabel;
		  }
	  });
	  
	  return label;
  }
  
  public handleOnMenu( node: any, $event: any ): void {
	  
      this.contextMenuService.show.next( {
          contextMenu: (node.data.childType !== null ? this.nodeMenuComponent : this.leafMenuComponent),
          event: $event,
          item: node,
      } );
      $event.preventDefault();
      $event.stopPropagation();
  }
  
  public treeNodeOnClick( node: TreeNode, $event: any ): void {
  	
  	node.treeModel.setFocusedNode(node);
  	
  	if(node.treeModel.isExpanded(node)){
      	node.collapse();
  	}
  	else{
      	node.treeModel.expandAll();
  	}
  }
  
  options = {
//		  allowDrag: (node:TreeNode) => node.isLeaf,
//		  allowDrop: (element:Element, { parent, index }: {parent:TreeNode,index:number}) => {
			    // return true / false based on element, to.parent, to.index. e.g.
//			    return parent.hasChildren;
//			  },
	      displayField: "label",
		  actionMapping: {
	            mouse: {
	                click : ( tree: TreeComponent, node: TreeNode, $event: any ) => {
	                    this.treeNodeOnClick( node, $event );
	                },
	                contextMenu: ( tree: any, node: any, $event: any ) => {
	                    this.handleOnMenu( node, $event );
	                }
	            }
	        },
	        mouse: {
//	            drop: (tree: TreeComponent, node: TreeNode, $event: any, {from, to}: {from:TreeNode, to:TreeNode}) => {
//	              console.log('drag', from, to); // from === {name: 'first'}
//	              // Add a node to `to.parent` at `to.index` based on the data in `from`
//	              // Then call tree.update()
//	            }
	          }
  };
  
  public hierarchyOnClick(event:any, item:any) {
	  let hierarchyId = item.code;
	  
	  this.currentHierarchy = item;
	  
	  this.nodes = [];
	  
	  if(this.getHierarchy(hierarchyId).rootGeoObjectTypes.length > 0){
	    this.getHierarchy(hierarchyId).rootGeoObjectTypes.forEach(type => {
		  this.nodes.push(type)
	    });
     
	    setTimeout(() => {
	      if(this && this.tree){
		    this.tree.treeModel.expandAll();
	      }
		}, 1)
	  }
	  
	  if(this.tree){
	    this.tree.treeModel.update();
	  }
  }
  
  public createHierarchy(): void {
	  this.bsModalRef = this.modalService.show( CreateModalComponent, {
          animated: true,
          backdrop: true,
          ignoreBackdropClick: true,
          'class': 'upload-modal'
      } );
      
      ( <CreateModalComponent>this.bsModalRef.content ).onHierarchytTypeCreate.subscribe( data => {
    	  this.hierarchies.push(data);
      } );
  }
  
  public deleteHierarchyType( obj: HierarchyType ): void {
	  this.bsModalRef = this.modalService.show( ConfirmModalComponent, {
		  animated: true,
		  backdrop: true,
		  ignoreBackdropClick: true,
	  } );
	  this.bsModalRef.content.message = 'Are you sure you want to delete [' + obj.localizedLabel + ']';
	  this.bsModalRef.content.data = obj.code;

	  ( <ConfirmModalComponent>this.bsModalRef.content ).onConfirm.subscribe( data => {
		  this.removeHierarchyType( data );
	  } );
  }
  
  public removeHierarchyType( code: string ): void {
      this.hierarchyService.deleteHierarchyType( code ).then( response => {
    	  
    	  let pos = this.getHierarchyTypePosition(code);
    	  this.hierarchies.splice(pos, 1);
    	  
      } ).catch(( err: any ) => {
          this.error( err.json() );
      } );
  }
  
  public createGeoObjectType(): void {
	  this.bsModalRef = this.modalService.show( CreateGeoObjTypeModalComponent, {
          animated: true,
          backdrop: true,
          ignoreBackdropClick: true,
          'class': 'upload-modal'
      } );
	  this.bsModalRef.content.hierarchyType = this.currentHierarchy;
	  
      ( <CreateGeoObjTypeModalComponent>this.bsModalRef.content ).onGeoObjTypeCreate.subscribe( data => {
    	  this.geoObjectTypes.push(data);
      } );
  }
  
  public createRootGeoObjectType(): void {
	  
	  this.bsModalRef = this.modalService.show( CreateGeoObjTypeModalComponent, {
          animated: true,
          backdrop: true,
          ignoreBackdropClick: true,
          'class': 'upload-modal'
      } );
	  this.bsModalRef.content.hierarchyType = this.currentHierarchy;
	  
      ( <CreateGeoObjTypeModalComponent>this.bsModalRef.content ).onGeoObjTypeCreate.subscribe( data => {
    	  this.geoObjectTypes.push(data);
      } );
  }
  
  public deleteGeoObjectType(obj: GeoObjectType): void {
    this.bsModalRef = this.modalService.show( ConfirmModalComponent, {
        animated: true,
        backdrop: true,
        ignoreBackdropClick: true,
    } );
    this.bsModalRef.content.message = 'Are you sure you want to delete [' + obj.localizedLabel + ']';
    this.bsModalRef.content.data = obj.code;

    ( <ConfirmModalComponent>this.bsModalRef.content ).onConfirm.subscribe( data => {
        this.removeGeoObjectType( data );
    } );
  }
  
  public removeGeoObjectType( code: string ): void {
      this.hierarchyService.deleteGeoObjectType( code ).then( response => {
    	  
    	  console.log("deleted")
    	  
    	  let pos = this.getGeoObjectTypePosition(code);
    	  this.geoObjectTypes.splice(pos, 1);
    	  
//          const parent = node.parent;
//          let children = parent.data.children;
//
//          parent.data.children = children.filter(( n: any ) => n.id !== node.data.id );
//
//          if ( parent.data.children.length === 0 ) {
//              parent.data.hasChildren = false;
//          }
//
//          this.tree.treeModel.update();
      } ).catch(( err: any ) => {
          this.error( err.json() );
      } );
  }
  
  private getHierarchyTypePosition(code: string): number{
	  for(let i=0; i<this.hierarchies.length; i++){
		  let obj = this.hierarchies[i];
		  if(obj.code === code){
			  return i;
		  }
	  }
  }
  
  private getGeoObjectTypePosition(code: string): number{
	  for(let i=0; i<this.geoObjectTypes.length; i++){
		  let obj = this.geoObjectTypes[i];
		  if(obj.code === code){
			  return i;
		  }
	  }
  }
  
  public addChildAndRootToHierarchy(): void {
	  const that = this;
      
      this.bsModalRef = this.modalService.show( CreateChildModalComponent, {
          animated: true,
          backdrop: true,
          ignoreBackdropClick: true,
          'class': 'upload-modal'
      } );
      this.bsModalRef.content.allGeoObjectTypes = this.geoObjectTypes;
      this.bsModalRef.content.parent = "ROOT";
      this.bsModalRef.content.toRoot = true;
      this.bsModalRef.content.hierarchyType = this.currentHierarchy;
      this.bsModalRef.content.nodes = this.nodes;

      ( <CreateChildModalComponent>this.bsModalRef.content ).onNodeChange.subscribe( hierarchyType => {
          
          that.processHierarchyNodes(hierarchyType.rootGeoObjectTypes);
          that.updateHierarchy(hierarchyType.code, hierarchyType.rootGeoObjectTypes)
          
          that.setNodesForHierarchy(hierarchyType);

          if(this.tree){
            this.tree.treeModel.update();
          }
      } );
  }
  
  public addChildToHierarchy( parent: TreeNode ): void {
	  const that = this;
      that.current = parent;
      
      this.bsModalRef = this.modalService.show( CreateChildModalComponent, {
          animated: true,
          backdrop: true,
          ignoreBackdropClick: true,
          'class': 'upload-modal'
      } );
      this.bsModalRef.content.allGeoObjectTypes = this.geoObjectTypes;
      this.bsModalRef.content.parent = parent;
      this.bsModalRef.content.toRoot = false;
      this.bsModalRef.content.hierarchyType = this.currentHierarchy;
      this.bsModalRef.content.nodes = this.nodes;

      ( <CreateChildModalComponent>this.bsModalRef.content ).onNodeChange.subscribe( hierarchyType => {
          const d = that.current.data;

          
          that.processHierarchyNodes(hierarchyType.rootGeoObjectTypes);
          that.updateHierarchy(hierarchyType.code, hierarchyType.rootGeoObjectTypes)
          
          that.setNodesForHierarchy(hierarchyType);

          if(this.tree){
            this.tree.treeModel.update();
          }
      } );
  }
  
  public deleteTreeNode( node: TreeNode ): void {
      this.bsModalRef = this.modalService.show( ConfirmModalComponent, {
          animated: true,
          backdrop: true,
          ignoreBackdropClick: true,
      } );
      this.bsModalRef.content.message = 'Are you sure you want to delete [' + node.data.label + ']';
      this.bsModalRef.content.data = node;

      ( <ConfirmModalComponent>this.bsModalRef.content ).onConfirm.subscribe( data => {
          this.removeTreeNode( data );
      } );
  }
  
  public removeTreeNode( node: TreeNode ): void {
      this.hierarchyService.removeFromHierarchy( this.currentHierarchy.code, node.parent.data.geoObjectType, node.data.geoObjectType ).then( data => {
          const parent = node.parent;
          let children = parent.data.children;

          // Update the tree
          parent.data.children = children.filter(( n: any ) => n.id !== node.data.id );
          if ( parent.data.children.length === 0 ) {
              parent.data.hasChildren = false;
          }
          this.tree.treeModel.update();
          
          // Update the available GeoObjectTypes
          this.changeDetectorRef.detectChanges()
          
      } ).catch(( err: any ) => {
          this.error( err.json() );
      } );
  }
  
  public isActive(item:any) {
      return this.currentHierarchy === item;
  };
  
  public onDrop($event:any) {
	    // Dropped $event.element
	  console.log("on drop")
	  
	  this.removeTreeNode($event.element)
  }

  public allowDrop(element:Element) {
	    // Return true/false based on element
	  console.log("allow drop")
	  return true;
  }
  
  public error( err: any ): void {
      // Handle error
      if ( err !== null ) {
          this.bsModalRef = this.modalService.show( ErrorModalComponent, { backdrop: true } );
          this.bsModalRef.content.message = ( err.localizedMessage || err.message );
      }

  }
   
}
