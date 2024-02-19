import { AnswerDto, AnswerChoiceDto } from "src/screens/studySession/types";

export interface ChoiceComponentProps {
  choice: AnswerChoiceDto;
  selected: boolean;
  onSelect: () => void;
}
