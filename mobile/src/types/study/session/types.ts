import { FileResource } from "src/types/fileResource/types";

export interface Question {
  content: string;
  attachment?: FileResource;
}

export interface AnswerChoices {
  type: "text" | "audio";
  data: string[] | FileResource[];
}

export interface Answer {
  explanation?: string;
}

export interface MultipleChoiceAnswer extends Answer {
  type: "multiple-choice";
  choices: AnswerChoices;
  correctChoice: number;
}

export interface InputAnswer extends Answer {
  type: "input";
  answer: string;
}
