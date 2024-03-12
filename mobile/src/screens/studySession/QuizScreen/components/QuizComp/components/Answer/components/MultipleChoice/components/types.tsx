import { AnswerDto, AnswerChoiceDto } from "src/types/study/session";

export interface ChoiceComponentProps {
  choice: AnswerChoiceDto;
  selected: boolean;
  onSelect: () => void;
}
