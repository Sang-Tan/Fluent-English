import { FileResource } from "src/types/fileResource/types";

//#region ================RESPONSE====================
//#region Question
export interface QuestionDto {
  content: string;
  attachment?: FileResource;
}
//#endregion

//#region Answer
export enum ChoiceType {
  Text = "text",
  Audio = "audio",
}

export type AnswerChoiceDto = string | FileResource;

export interface AnswerChoicesDto {
  type: ChoiceType;
  data: AnswerChoiceDto[];
}

export interface TextChoicesDto extends AnswerChoicesDto {
  type: ChoiceType.Text;
  data: string[];
}

export interface AudioChoicesDto extends AnswerChoicesDto {
  type: ChoiceType.Audio;
  data: FileResource[];
}

export enum AnswerType {
  MultipleChoice = "multiple-choice",
  Input = "input",
}

export interface AnswerDto {
  explanation?: string;
  type: AnswerType;
}

export interface MultipleChoiceAnswerDto extends AnswerDto {
  type: AnswerType.MultipleChoice;
  choices: AnswerChoicesDto;
  correctChoice: number;
}

export interface InputAnswerDto extends AnswerDto {
  type: AnswerType.Input;
  answer: string;
}
//#endregion

//#region Quiz
export interface QuizDto {
  wordId: number;
  question: QuestionDto;
  answer: AnswerDto;
}

export interface AnswerSubmissionResultDto {
  correctAnswer: string;
  isCorrect: boolean;
}

export interface IncorrectAnswerSubmissionResultDto
  extends AnswerSubmissionResultDto {
  isCorrect: false;
  correctAnswer: string;
}
//#endregion

//#region User
//#region User story
export interface StoryContentDto {
  vietnameseContent: string;
  englishContent: string;
}

export interface StoryProgressDto {
  chapterNumber: number;

  /**
   * Number in [0.0, 1.0], equivalent to [0%, 100%]
   */
  chapterProgress: number;
}

export interface BeforeAfterStoryProgressDto {
  before: StoryProgressDto;
  after: StoryProgressDto;
  storyContentReceived: StoryContentDto[];
}
//#endregion

//#region User level
export interface LevelProgressDto {
  level: number;
  experience: number;
  expToNextLevel: number;
}

export interface LevelBeforeAfterDto {
  before: LevelProgressDto;
  after: LevelProgressDto;
}
//#endregion

//#region User state and attributes
export interface UserCurrentStateDto {
  currentHp: number;
}

export interface UserAttributesDto {
  maxHp: number;
  baseShield: number;
}

export interface BeforeAfterUserAttributesDto {
  before: UserAttributesDto;
  after: UserAttributesDto;
}
//#endregion

//#endregion

//#region Battle Info
export interface BattleInfoDto {
  userCurrentHp: number;
  userShield: number;
  userStreak: number;
  enemyName: string;
}

export interface BattleResultDto {
  storyProgress: BeforeAfterStoryProgressDto;
  levelProgress: LevelBeforeAfterDto;
  currentState: UserCurrentStateDto;
  attributesChange: BeforeAfterUserAttributesDto;
}
//#endregion

//#region Session Info
export enum SessionInfoSubmissionType {
  Update = "update",
  Summary = "summary",
}

export interface SessionSubmissionResultDto {
  type: SessionInfoSubmissionType;
  answerSubmissionResult: AnswerSubmissionResultDto;
}

export interface SessionStatusDto {
  nextQuiz: QuizDto;
  battleInfo: BattleInfoDto;
  remainingQuizzesCount: number;
}

export interface SessionUpdateDto
  extends SessionSubmissionResultDto,
    SessionStatusDto {
  type: SessionInfoSubmissionType.Update;
  sessionId?: string;
}

export interface SessionInitializationDto extends SessionStatusDto {
  sessionId: string;
}

export interface WordsScoresDto {
  [wordId: number]: number;
}

export interface SessionSummaryDto extends SessionSubmissionResultDto {
  type: SessionInfoSubmissionType.Summary;
  battleResult: BattleResultDto;
  wordsScores: WordsScoresDto;
}
//#endregion
//#endregion

//#region ================REQUEST====================
export interface AnswerSubmissionDto {
  answer: string;
  timeAnsweredSec: number;
}
//#endregion
