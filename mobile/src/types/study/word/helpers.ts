import { WordStudyStatusType, StudyingWordDto, WordStudyStatusDto } from ".";

export function isStudyingWord(
  word: WordStudyStatusDto
): word is StudyingWordDto {
  return word.type === WordStudyStatusType.Studying;
}
