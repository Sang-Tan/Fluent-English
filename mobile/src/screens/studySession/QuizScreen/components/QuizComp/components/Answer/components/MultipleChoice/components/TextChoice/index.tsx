import { ChoiceComponentProps } from "../types";
import { View, Text, Pressable } from "react-native";
import styles from "./styles";

interface Props extends ChoiceComponentProps {
  choice: string;
}

function TextChoice({ choice, selected, onSelect }: Props) {
  return (
    <Pressable onPress={onSelect}>
      <View style={[styles.container, selected && styles.selected]}>
        <Text style={styles.text}>{choice}</Text>
      </View>
    </Pressable>
  );
}

export default TextChoice;
