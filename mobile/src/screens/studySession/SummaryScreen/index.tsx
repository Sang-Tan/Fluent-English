import WordsScoresList from "./components/WordsScoresList";
import { SummaryScreenProps } from "src/routes/types";

import { View, Text, SafeAreaView, Pressable } from "react-native";
import { FontAwesomeIcon } from "@fortawesome/react-native-fontawesome";
import styles from "./styles";
import { faSurprise } from "@fortawesome/free-regular-svg-icons";

function SummaryScreen({ route, navigation }: SummaryScreenProps) {
  const { summary } = route.params;

  const battleResult = summary.battleResult;
  const levelProgress = battleResult.levelProgress;
  const storyProgress = battleResult.storyProgress;

  const handleFinish = () => {
    // pop 2 screens to go back to the screen before the quiz
    navigation.pop(2);
  };
  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.titleContainer}>
        <FontAwesomeIcon
          style={styles.titleIcon}
          icon={faSurprise}
          size={80}
          color="white"
        />
        <Text style={styles.title}>Chúc mừng</Text>
        <Text style={styles.subtitle}>Bạn đã hoàn thành bài học</Text>
      </View>
      <View>
        <Text style={styles.text}>
          Hp: {battleResult.currentState.currentHp}/
          {battleResult.attributes.maxHp}
        </Text>
        <Text style={styles.text}>
          Level before: {levelProgress.before.level}, exp before:{" "}
          {levelProgress.before.experience}
        </Text>
        <Text style={styles.text}>
          Level after: {levelProgress.after.level}, exp after:{" "}
          {levelProgress.after.experience}
        </Text>
        <Text style={styles.text}>
          Chapter before: {storyProgress.before.chapterNumber}, progress before:{" "}
          {storyProgress.before.chapterProgress}
        </Text>
        <Text style={styles.text}>
          Chapter after: {storyProgress.after.chapterNumber}, progress after:{" "}
          {storyProgress.after.chapterProgress}
        </Text>
      </View>
      <WordsScoresList wordsScores={summary.wordsScores} />
      <Pressable style={styles.button} onPress={handleFinish}>
        <Text style={styles.buttonText}>Hoàn thành</Text>
      </Pressable>
    </SafeAreaView>
  );
}

export default SummaryScreen;
