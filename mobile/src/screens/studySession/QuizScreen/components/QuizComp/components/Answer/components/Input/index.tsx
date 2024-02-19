import { AnswerProps } from "../../types";

import { TextInput } from "react-native";
import { useState } from "react";
import styles from "./styles";

function Input({ onAnswerChange }: AnswerProps) {
  const [userAnswer, setUserAnswer] = useState<string | null>(null);

  const handleAnswerChange = (text: string) => {
    setUserAnswer(text);
    if (onAnswerChange) {
      onAnswerChange(text);
    }
  };

  return (
    <TextInput
      style={styles.input}
      onChangeText={handleAnswerChange}
      value={userAnswer || ""}
    />
  );
}

export default Input;
