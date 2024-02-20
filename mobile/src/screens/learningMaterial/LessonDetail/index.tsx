// types
import { LessonDto } from "../../../types/learningMaterial";
import { SessionInitializationDto } from "src/screens/studySession/types";
import { LessonDetailScreenProps } from "src/routes/types";
// constants
import ROUTE_NAMES from "src/routes/routeNames";

import getEnv from "src/helpers/getEnv";
import { useState, useEffect } from "react";
import useRequest from "src/hooks/useRequest";
import { View, Text, SafeAreaView, ScrollView, Pressable } from "react-native";
import WordItem from "./components/WordItem";
import { FontAwesomeIcon } from "@fortawesome/react-native-fontawesome";
import { faChevronLeft } from "@fortawesome/free-solid-svg-icons";
import styles from "./styles";

const STUDY_SESSION_MAX_WORDS: number = Number.parseInt(
  getEnv("EXPO_PUBLIC_STUDY_SESSION_MAX_WORDS")
);

interface Word {
  id: number;
  text: string;
  vietnameseMeaning: string;
  nextStudy: number;
}

function LessonDetail({ route, navigation }: LessonDetailScreenProps) {
  const { lesson } = route.params;

  const [words, setWords] = useState<Word[]>([]);
  const request = useRequest();

  const handleBack = () => {
    navigation.goBack();
  };

  useEffect(() => {
    const fetchWords = async () => {
      const resp = await request(`/lessons/${lesson.id}/words`, {
        method: "GET",
      });
      if (resp.ok) {
        const data: Word[] = await resp.json();
        setWords(data);
      }
    };

    fetchWords();
  }, []);

  const handleStudyPressed = () => {
    const startStudySession = async (wordsToStudy: Word[]) => {
      const wordIds = wordsToStudy.map((word) => word.id);
      const resp = await request("/study-sessions", {
        method: "POST",
        body: JSON.stringify({ wordIds }),
      });
      if (resp.ok) {
        const data: SessionInitializationDto = await resp.json();
        navigation.navigate(ROUTE_NAMES.QUIZ_SCREEN, {
          startInfo: data,
        });
      }
    };

    const arr = [...words];
    arr.sort((a, b) => a.nextStudy - b.nextStudy);
    const wordsToStudy = arr.slice(
      0,
      Math.min(STUDY_SESSION_MAX_WORDS, arr.length)
    );
    startStudySession(wordsToStudy);
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
