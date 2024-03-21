export interface RoadMapResponse {
  message: string;
  roadMaps: RoadMap[];
}

export interface RoadMap {
  id: number;
  email: string;
  createdOn: number;
  roadMapItems: RoadMapItem[];
}

export interface RoadMapItem {
  dayNumber: number;
  title: string;
  tasks: string[];
  resources: string[];
}
