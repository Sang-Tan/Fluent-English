import ROUTE_NAMES from "src/routes/routeNames";

import styles from "./styles";
import { useState, useEffect, useMemo } from "react";
import {
  View,
  Text,
  SafeAreaView,
  TextInput,
  ScrollView,
  ActivityIndicator,
} from "react-native";
import LessonItem from "./components/LessonItem";
import { Lesson } from "./types";
import useRequest from "src/hooks/useRequest";

const TYPING_TIMEOUT = 200;

interface SearchProps {
  text: string;
  page: number;
}

interface ScrollToBottomProps {
  layoutMeasurement: { height: number; width: number };
  contentOffset: { x: number; y: number };
  contentSize: { height: number; width: number };
}

const isScrollToBottom = ({
  layoutMeasurement,
  contentOffset,
  contentSize,
}: ScrollToBottomProps) => {
  const bottomPadding = 20;
  return (
    layoutMeasurement.height + contentOffset.y >=
    contentSize.height - bottomPadding
  );
};

interface LessonSearchProps {
  navigation: any;
}

function LessonSearch({ navigation }: LessonSearchProps) {
  const [searchText, setSearchText] = useState<string>("");
  const [curPage, setCurPage] = useState<number>(0);
  const [lessons, setLessons] = useState<Lesson[]>([]);
  const [pendingSearch, setPendingSearch] = useState<SearchProps | null>({
    text: "",
    page: 1,
  });
  const [isFetching, setIsFetching] = useState<boolean>(false);
  const request = useRequest();

  const handleTextChange = useMemo(() => {
    let typingTimer: NodeJS.Timeout;

    return (text: string) => {
      if (typingTimer) {
        clearTimeout(typingTimer);
      }

      setSearchText(text);

      typingTimer = setTimeout(() => {
        setLessons([]);
        setPendingSearch({ text, page: 1 });
      }, TYPING_TIMEOUT);
    };
  }, []);

  const handleScroll = (event: any) => {
    const { layoutMeasurement, contentOffset, contentSize } = event.nativeEvent;
    if (isScrollToBottom({ layoutMeasurement, contentOffset, contentSize })) {
      setPendingSearch({ text: searchText, page: curPage + 1 });
    }
  };

  useEffect(() => {
    const searchLessons = async (searchProps: SearchProps) => {
      setIsFetching(true);
      setPendingSearch(null);

      try {
        const response = await request(
          `/lessons?q=${searchProps.text}&page=${searchProps.page}`,
          {
            method: "GET",
          }
        );
        const respData = await response.json();
        if (respData && respData.data.length > 0) {
          setLessons([...lessons, ...respData.data]);
          setCurPage(searchProps.page);
        }
      } catch (error) {
        console.error("Error fetching lessons", error);
      }

      setIsFetching(false);
    };
    if (pendingSearch && !isFetching) {
      searchLessons(pendingSearch);
    }
  }, [pendingSearch, isFetching]);

  return (
    <SafeAreaView style={[styles.container]}>
      <View>
        <Text style={styles.headerTitle}>Lessons</Text>
      </View>
      <View style={styles.searchContainer}>
        <View style={styles.searchBox}>
          <TextInput
            placeholder="Search"
            value={searchText}
            onChangeText={handleTextChange}
          />
        </View>
      </View>
      <ScrollView onScroll={handleScroll} style={styles.lessonsContainer}>
        {lessons.map((lesson) => (
          <LessonItem
            key={lesson.id}
            lessonName={lesson.name}
            difficulty={lesson.difficulty}
            onPress={() => {
              navigation.navigate(ROUTE_NAMES.LESSON_DETAIL, {
                lesson: lesson,
              });
            }}
          />
        ))}
        <ActivityIndicator
          style={isFetching ? {} : styles.hidden}
          size="large"
          color="#0000ff"
        />
      </ScrollView>
    </SafeAreaView>
  );
}

export default LessonSearch;
