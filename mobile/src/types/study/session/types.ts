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

export interface Quiz<T extends Answer> {
  wordId: number;
  question: Question;
  answer: T;
}

export interface BattleInfo {
  userHp: number;
  userShield: number;
  userStreak: number;
  enemyName: string;
  enemyDmg: number;
  expGain: number;
}
