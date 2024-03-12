export enum WordStudyStatusType {
  Uncategorized = "uncategorized",
  Ignore = "ignore",
  Studying = "studying",
}

export interface WordStudyStatusDto {
  wordId: number;
  type: WordStudyStatusType;
}

export interface StudyingWordDto extends WordStudyStatusDto {
  type: WordStudyStatusType.Studying;

  /** Last time the word was studied in milliseconds */
  lastStudy: number;

  /** Next time the word should be studied in milliseconds */
  nextStudy: number;

  /** Proficiency of the word [0, 1] */
  proficiency: number;
}
