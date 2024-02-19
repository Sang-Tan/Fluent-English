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

  /**
   * Number in [0.0, 1.0], equivalent to [0%, 100%]
   */
  experience: number;
}

export interface LevelBeforeAfterDto {
  before: LevelProgressDto;
  after: LevelProgressDto;
}
//#endregion

//#region User state
export interface UserCurrentStateDto {
  currentHp: number;
}
//#endregion

//#endregion

//#region Battle Info
export interface BattleInfoDto {
  userHp: number;
  userShield: number;
  userStreak: number;
  enemyName: string;
}

export interface BattleResultDto {
  storyProgress: StoryProgressDto;
  levelProgress: LevelProgressDto;
  currentState: UserCurrentStateDto;
}
//#endregion

//#region Session Info
export enum SessionInfoSubmissionType {
  Update = "update",
  Summary = "summary",
}

export interface SessionSubmissionDto {
  type: SessionInfoSubmissionType;
  answerSubmissionResult: AnswerSubmissionResultDto;
}

export interface SessionStatusDto {
  nextQuiz: QuizDto;
  battleInfo: BattleInfoDto;
  remainingQuizzesCount: number;
}

export interface SessionUpdateDto
  extends SessionSubmissionDto,
    SessionStatusDto {
  type: SessionInfoSubmissionType.Update;
  sessionId?: string;
}

export interface SessionInitializationDto extends SessionStatusDto {
  sessionId: string;
}

export interface SessionSummaryDto extends SessionSubmissionDto {
  type: SessionInfoSubmissionType.Summary;
  battleResult: BattleResultDto;
  wordsScores: { [wordId: number]: number };
}
//#endregion
//#endregion

//#region ================REQUEST====================
export enum AnswerSubmissionType {
  Answered = "answered",
  NotAnswered = "not-answered",
}

export interface AnswerSubmissionDto {
  type: AnswerSubmissionType;
  wordId: number;
}

export interface AnsweredSubmissionDto extends AnswerSubmissionDto {
  type: AnswerSubmissionType.Answered;
  answer: string;
  timeAnsweredSec: number;
}

export interface NotAnsweredSubmissionDto extends AnswerSubmissionDto {
  type: AnswerSubmissionType.NotAnswered;
}
//#endregion
