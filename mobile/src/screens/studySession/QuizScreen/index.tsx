import {
  SessionStatusDto,
  SessionSubmissionDto,
  SessionInitializationDto,
  AnswerSubmissionType,
  AnsweredSubmissionDto,
} from "../types";
import {
  isUpdateSessionInfo,
  isSessionSummary,
  isIncorrectAnswerSubmissionResult,
} from "../types/helpers";

import useRequest from "src/hooks/useRequest";
import { useState } from "react";
import { SafeAreaView, Pressable, Text, View } from "react-native";
import QuizComp from "./components/QuizComp";
import styles from "./styles";
import BattleInfo from "./components/BattleInfo";

interface Props {
  navigation: any;
  route: any;
}

function QuizScreen({ navigation, route }: Props) {
  const { startInfo }: { startInfo: SessionInitializationDto } = route.params;
  const sessionId = startInfo.sessionId;

  const [userAnswer, setUserAnswer] = useState("");
  const [sessionStatus, setSessionStatus] =
    useState<SessionStatusDto>(startInfo);
  const request = useRequest();

  const quiz = sessionStatus.nextQuiz;
  const battleInfo = sessionStatus.battleInfo;

  const handleAnswerSubmit = async () => {
    const submission: AnsweredSubmissionDto = {
      type: AnswerSubmissionType.Answered,
      wordId: quiz.wordId,
      answer: userAnswer,
      timeAnsweredSec: 0,
    };

    try {
      const resp = await request(`/study-sessions/${sessionId}/answer`, {
        method: "POST",
        body: JSON.stringify(submission),
      });

      if (resp.ok) {
        const submissionInfo: SessionSubmissionDto = await resp.json();
        const submissionResult = submissionInfo.answerSubmissionResult;
        if (isIncorrectAnswerSubmissionResult(submissionResult)) {
          alert(
            "Incorrect answer, correct answer is " +
              submissionResult.correctAnswer
          );
        }

        if (isUpdateSessionInfo(submissionInfo)) {
          setSessionStatus(submissionInfo);
          setUserAnswer("");
        } else if (isSessionSummary(submissionInfo)) {
          console.log("Session Summary", submissionInfo);
        }
      } else {
        console.error("Failed to submit answer", resp);
      }
    } catch (e) {
      console.error(e);
    }
  };

  return (
    <SafeAreaView style={styles.container}>
      <BattleInfo data={battleInfo} />
      <Text>Remaining quizzes: {sessionStatus.remainingQuizzesCount}</Text>
      <QuizComp
        quiz={quiz}
        onAnswerChange={(answer) => {
          setUserAnswer(answer);
        }}
      />
      <Pressable disabled={!userAnswer} onPress={handleAnswerSubmit}>
        <View
          style={[
            styles.submitButton,
            !userAnswer && styles.disabledSubmitButton,
          ]}
        >
          <Text style={styles.submitButtonText}>Trả lời</Text>
        </View>
      </Pressable>
    </SafeAreaView>
  );
}

export default QuizScreen;
