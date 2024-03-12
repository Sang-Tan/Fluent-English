import { WordsScoresDto } from "src/types/study/session";
import { WordScoreItem } from "./types";

import { useEffect, useState } from "react";
import useRequest from "src/hooks/useRequest";

import { getWordsByIds } from "src/apis/learningMaterial/words";

import { View, Text, ScrollView, ActivityIndicator } from "react-native";
import styles from "./styles";

interface Props {
  wordsScores: WordsScoresDto;
}

function WordsScoresList({ wordsScores }: Props) {
  const request = useRequest();
  const [wordScoreItems, setWordScoreItems] = useState<WordScoreItem[] | null>(
    null
  );

  useEffect(() => {
    const doFetch = async () => {
      const wordsIds = Object.keys(wordsScores).map((id) => parseInt(id));
      try {
        const words = await getWordsByIds(request, wordsIds);
        const items = words.map((word) => {
          return {
            word,
            score: wordsScores[word.id],
          };
        });
        setWordScoreItems(items);
      } catch (error) {
        console.error("Failed to fetch words", error);
      }
    };

    doFetch();
  }, [wordsScores]);

  return (
    <ScrollView>
      {!wordScoreItems ? (
        <ActivityIndicator />
      ) : (
        <View>
          {wordScoreItems.map((item) => (
            <View key={item.word.id} style={styles.itemContainer}>
              <Text style={styles.wordName}>{item.word.text}</Text>
              <Text style={styles.wordScore}>{item.score}</Text>
            </View>
          ))}
        </View>
      )}
    </ScrollView>
  );
}

export default WordsScoresList;
