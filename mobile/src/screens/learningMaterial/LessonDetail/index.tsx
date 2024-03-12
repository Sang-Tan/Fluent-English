// types
import { SessionInitializationDto } from "src/types/study/session";
import { LessonDetailScreenProps } from "src/routes/types";
import { WordDto } from "src/types/learningMaterial";
import { WordStudyStatusDto, WordStudyStatusType } from "src/types/study/word";
import { isStudyingWord } from "src/types/study/word/helpers";

// constants
import ROUTE_NAMES from "src/routes/routeNames";

// api
import { getWordsByLesson } from "src/apis/learningMaterial/words";
import { getWordsStudyStatus } from "src/apis/study/word";
import { startStudySession } from "src/apis/study/session";

import getEnv from "src/helpers/getEnv";
import { useState, useEffect } from "react";
import useRequest from "src/hooks/useRequest";
import { View, Text, SafeAreaView, ScrollView, Pressable } from "react-native";
import WordItem from "./components/WordItem";
import { FontAwesomeIcon } from "@fortawesome/react-native-fontawesome";
import { faChevronLeft } from "@fortawesome/free-solid-svg-icons";
import styles from "./styles";

interface WordDtoWithStatus extends WordDto {
  status: WordStudyStatusDto;
}

const STUDY_SESSION_MAX_WORDS: number = Number.parseInt(
  getEnv("EXPO_PUBLIC_STUDY_SESSION_MAX_WORDS")
);

function LessonDetail({ route, navigation }: LessonDetailScreenProps) {
  const { lesson } = route.params;

  const [words, setWords] = useState<WordDtoWithStatus[]>([]);
  const request = useRequest();

  const handleBack = () => {
    navigation.goBack();
  };

  useEffect(() => {
    const prepareWords = async () => {
      const words = await getWordsByLesson(request, lesson.id);
      const wordIds = words.map((word) => word.id);
      const wordsStatus = await getWordsStudyStatus(request, wordIds);
      const wordsWithStatus = words.map((word) => {
        const status = wordsStatus.find((status) => status.wordId === word.id);

        if (!status) {
          throw new Error(`Status not found for word ${word.id}`);
        }

        return {
          ...word,
          status,
        };
      });

      setWords(wordsWithStatus);
    };

    prepareWords();
  }, []);

  const handleStudyPressed = () => {
    const wordsToStudyIds = words
      .filter((word) => {
        return (
          (isStudyingWord(word.status) &&
            word.status.nextStudy <= Date.now()) ||
          word.status.type === WordStudyStatusType.Uncategorized
        );
      })
      .sort((a, b) => {
        const nextStudyA = isStudyingWord(a.status) ? a.status.nextStudy : 0;
        const nextStudyB = isStudyingWord(b.status) ? b.status.nextStudy : 0;
        return nextStudyA - nextStudyB;
      })
      .slice(0, STUDY_SESSION_MAX_WORDS)
      .map((word) => word.id);

    startStudySession(request, wordsToStudyIds)
      .then((session: SessionInitializationDto) => {
        navigation.navigate(ROUTE_NAMES.QUIZ_SCREEN, { startInfo: session });
      })
      .catch((error) => {
        alert("Failed to start study session");
      });
  };

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <Pressable onPress={handleBack}>
          <FontAwesomeIcon style={styles.text} icon={faChevronLeft} />
        </Pressable>
      </View>

      <Text style={styles.title}>{lesson.name}</Text>

      <ScrollView>
        {words.map((word) => (
          <WordItem
            key={word.id}
            word={word.text}
            meaning={word.vietnameseMeaning}
            status={word.status}
          />
        ))}
      </ScrollView>
      <Pressable
        style={({ pressed }) => [
          styles.button,
          pressed && styles.buttonPressed,
        ]}
        onPress={handleStudyPressed}
      >
        <Text style={styles.buttonText}>Học</Text>
      </Pressable>
    </SafeAreaView>
  );
}

export default LessonDetail;
