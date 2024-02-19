import { AnswerDto } from "src/screens/studySession/types";
import {
  isInputAnswer,
  isMultipleChoiceAnswer,
} from "src/screens/studySession/types/helpers";
import { AnswerProps } from "./types";

import { Text } from "react-native";
import Input from "./components/Input";
import MultipleChoice from "./components/MultipleChoice";

interface Props extends AnswerProps {
  answerData: AnswerDto;
}

function Answer({ answerData, onAnswerChange }: Props) {
  return (
    <>
      {isInputAnswer(answerData) ? (
        <Input onAnswerChange={onAnswerChange} />
      ) : isMultipleChoiceAnswer(answerData) ? (
        <MultipleChoice
          choices={answerData.choices}
          onAnswerChange={onAnswerChange}
        />
      ) : (
        <Text>Answer not supported</Text>
      )}
    </>
  );
}

export default Answer;
