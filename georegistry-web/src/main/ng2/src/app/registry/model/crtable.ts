import { GeoObjectOverTime, HierarchyOverTime } from './registry';

export class ChangeRequest {
  oid: string;
  createdBy: string;
  createDate: Date;
  approvalStatus: string;
  statusCode: string;
  total: number;
  pending: number;
}

export class AbstractAction {
  approvalStatus: string;
  createActionDate: Date;
  label: string;
  oid: string;
  actionType: string;
  actionLabel: string;
}

export class UpdateGeoObjectAction extends AbstractAction {
  geoObjectJson: GeoObjectOverTime;
}

export class CreateGeoObjectAction extends AbstractAction {
  geoObjectJson: GeoObjectOverTime;
}

export class AddChildAction extends AbstractAction {
  childId: string;
  childTypeCode: string;
  parentId: string;
  parentTypeCode: string;
  hierarchyTypeCode: string;
  contributorNotes: string;
  maintainerNotes: string;
}

export class RemoveChildAction extends AbstractAction {
  childId: string;
  childTypeCode: string;
  parentId: string;
  parentTypeCode: string;
  hierarchyCode: string;
}

export class GovernanceStatus {
  key: string;
  label: string;
}

export class PageEvent {
  type:string;
  data:any;
}

export class SetParentAction extends AbstractAction {
  childCode: string;
  childTypeCode: string;
  json: HierarchyOverTime[];
}

