import { FileResource } from "../fileResource/types";

export interface LessonDto {
  id: number;
  name: string;
  difficulty: number;
}

export interface WordDto {
  id: number;
  text: string;
  sound: FileResource;
  image: FileResource;
  vietnameseMeaning: string;
  difficulty: number;
}
