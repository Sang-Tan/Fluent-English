import { View, Text, Pressable } from "react-native";
import styles from "./styles";

interface LessonProps {
  lessonName: string;
  difficulty: number;
  onPress?: () => void;
}

function LessonItem({ lessonName, difficulty, onPress }: LessonProps) {
  return (
    <Pressable onPress={onPress}>
      <View style={styles.container}>
        <View style={styles.leftContainer}>
          <View style={styles.difficultyBox}>
            <Text style={styles.difficultyText}>{difficulty}</Text>
          </View>
        </View>
        <View style={styles.rightContainer}>
          <Text style={styles.lessonName}>{lessonName}</Text>
        </View>
      </View>
    </Pressable>
  );
}

export default LessonItem;
