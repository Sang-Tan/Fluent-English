import { QuizDto } from "src/screens/studySession/types";

import { useState } from "react";
import { View, SafeAreaView } from "react-native";
import Question from "./components/Question";
import Answer from "./components/Answer";
import styles from "./styles";

interface Props {
  quiz: QuizDto;
  onAnswerChange?: (answer: string) => void;
}

function QuizComp({ quiz, onAnswerChange }: Props) {
  return (
    <View style={styles.container}>
      <Question data={quiz.question} />
      <Answer answerData={quiz.answer} onAnswerChange={onAnswerChange} />
    </View>
  );
}

export default QuizComp;
