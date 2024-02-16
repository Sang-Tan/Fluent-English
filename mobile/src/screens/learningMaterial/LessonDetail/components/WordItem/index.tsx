import { View, Text } from "react-native";
import styles from "./styles";

interface WordItemProps {
  word: string;
  meaning: string;
}

function WordItem({ word, meaning }: WordItemProps) {
  return (
    <View style={styles.container}>
      <View style={styles.wordContainer}>
        <Text style={styles.text}>{word}</Text>
        <Text style={styles.text}>{meaning}</Text>
      </View>
    </View>
  );
}

export default WordItem;
