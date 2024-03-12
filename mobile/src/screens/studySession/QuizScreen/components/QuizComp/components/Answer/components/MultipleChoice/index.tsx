import { AnswerChoicesDto } from "src/types/study/session";
import { isTextChoices } from "src/types/study/session/helpers";
import { AnswerProps } from "../../types";

import { useState } from "react";
import TextChoice from "./components/TextChoice";
import { View, Text } from "react-native";
import styles from "./styles";

interface Props extends AnswerProps {
  choices: AnswerChoicesDto;
}

function MultipleChoice({ choices, onAnswerChange }: Props) {
  const [selectedIdx, setSelectedIdx] = useState<number | null>(null);

  const handleSelect = (idx: number) => {
    setSelectedIdx(idx);
    if (onAnswerChange) {
      onAnswerChange(idx.toString());
    }
  };

  return (
    <View style={styles.container}>
      {isTextChoices(choices) ? (
        choices.data.map((choice, index) => (
          <View key={index} style={styles.choiceContainer}>
            <TextChoice
              choice={choice}
              selected={selectedIdx === index}
              onSelect={() => handleSelect(index)}
            />
          </View>
        ))
      ) : (
        <View style={styles.container}>
          <Text>This choice type is not supported</Text>
        </View>
      )}
    </View>
  );
}

export default MultipleChoice;
