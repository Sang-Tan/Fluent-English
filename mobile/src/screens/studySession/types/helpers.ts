import {
  AnswerDto,
  AnswerType,
  InputAnswerDto,
  MultipleChoiceAnswerDto,
  AnswerChoicesDto,
  TextChoicesDto,
  AudioChoicesDto,
  ChoiceType,
} from ".";

import {
  SessionSubmissionDto,
  SessionInfoSubmissionType,
  SessionUpdateDto,
  SessionSummaryDto,
} from ".";

import {
  AnswerSubmissionResultDto,
  IncorrectAnswerSubmissionResultDto,
} from ".";

export function isInputAnswer(answer: AnswerDto): answer is InputAnswerDto {
  return answer.type === AnswerType.Input;
}

export function isMultipleChoiceAnswer(
  answer: AnswerDto
): answer is MultipleChoiceAnswerDto {
  return answer.type === AnswerType.MultipleChoice;
}

export function isTextChoices(
  choices: AnswerChoicesDto
): choices is TextChoicesDto {
  return choices.type === ChoiceType.Text;
}

export function isAudioChoices(
  choices: AnswerChoicesDto
): choices is AudioChoicesDto {
  return choices.type === ChoiceType.Audio;
}

//#region SessionInfo
export function isUpdateSessionInfo(
  info: SessionSubmissionDto
): info is SessionUpdateDto {
  return info.type === SessionInfoSubmissionType.Update;
}

export function isSessionSummary(
  info: SessionSubmissionDto
): info is SessionSummaryDto {
  return info.type === SessionInfoSubmissionType.Summary;
}
//#endregion

export function isIncorrectAnswerSubmissionResult(
  result: AnswerSubmissionResultDto
): result is IncorrectAnswerSubmissionResultDto {
  return result.isCorrect === false;
}
