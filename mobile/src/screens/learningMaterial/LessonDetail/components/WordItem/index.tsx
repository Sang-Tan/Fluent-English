import { WordStudyStatusDto, WordStudyStatusType } from "src/types/study/word";
import { isStudyingWord } from "src/types/study/word/helpers";

import { View, Text } from "react-native";
import styles from "./styles";

interface WordItemProps {
  word: string;
  meaning: string;
  status: WordStudyStatusDto;
}

function WordItem({ word, meaning, status }: WordItemProps) {
  return (
    <View style={styles.container}>
      <View style={styles.wordContainer}>
        <Text style={styles.text}>{word}</Text>
        <Text style={styles.text}>{meaning}</Text>
      </View>
      <View>
        {isStudyingWord(status) ? (
          <Text style={styles.text}>
            Lần học tiếp theo: {new Date(status.nextStudy).toLocaleDateString()}
          </Text>
        ) : status.status === WordStudyStatusType.Ignore ? (
          <Text style={styles.text}>Đã biết</Text>
        ) : (
          <Text style={styles.text}>Chưa học</Text>
        )}
        </View>
    </View>
  );
}

export default WordItem;
