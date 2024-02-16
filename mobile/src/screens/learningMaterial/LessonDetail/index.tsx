// types
import { Lesson } from "../LessonSearch/types";
import { Quiz, BattleInfo } from "src/types/study/session/types";

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

interface StartStudySessionInfo {
  sessionId: string;
  nextQuiz: Quiz<any>;
  battleInfo: BattleInfo;
}

interface LessonDetailProps {
  route: any;
  navigation: any;
}

function LessonDetail({ route, navigation }: LessonDetailProps) {
  const { lesson }: { lesson: Lesson } = route.params;

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
      const resp = await request("/study-session", {
        method: "POST",
        body: JSON.stringify({ wordIds }),
      });
      if (resp.ok) {
        const data: StartStudySessionInfo = await resp.json();
        console.log(data);
      }
    };

    const arr = [...words];
    arr.sort((a, b) => a.nextStudy - b.nextStudy);
    const wordsToStudy = arr.slice(
      0,
      Math.min(STUDY_SESSION_MAX_WORDS, arr.length)
    );
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
      >
        <Text style={styles.buttonText}>Học</Text>
      </Pressable>
    </SafeAreaView>
  );
}

export default LessonDetail;
