import React from "react";
import {
  NativeStackNavigationOptions,
  NativeStackScreenProps,
} from "@react-navigation/native-stack";
import { LessonDto } from "src/types/learningMaterial";
import {
  SessionInitializationDto,
  SessionSummaryDto,
} from "src/screens/studySession/types";

import ROUTE_NAMES from "./routeNames";

export interface Route {
  name: keyof RootStackParamList;
  component: React.FC<any>;
  options?: NativeStackNavigationOptions;
}

export type RootStackParamList = {
  [ROUTE_NAMES.LOGIN]: undefined;
  [ROUTE_NAMES.REGISTER]: undefined;
  [ROUTE_NAMES.REGISTER_SUCCESS]: undefined;
  [ROUTE_NAMES.LESSON_SEARCH]: undefined;
  [ROUTE_NAMES.LESSON_DETAIL]: { lesson: LessonDto };
  [ROUTE_NAMES.QUIZ_SCREEN]: {
    startInfo: SessionInitializationDto;
  };
  [ROUTE_NAMES.STUDY_SESSION_SUMMARY]: {
    summary: SessionSummaryDto;
  };
};

export type LoginScreenProps = NativeStackScreenProps<
  RootStackParamList,
  "Login"
>;
export type RegisterScreenProps = NativeStackScreenProps<
  RootStackParamList,
  "Register"
>;
export type RegisterSuccessScreenProps = NativeStackScreenProps<
  RootStackParamList,
  "RegisterSuccess"
>;
export type LessonSearchScreenProps = NativeStackScreenProps<
  RootStackParamList,
  "LessonSearch"
>;
export type LessonDetailScreenProps = NativeStackScreenProps<
  RootStackParamList,
  "LessonDetail"
>;
export type QuizScreenProps = NativeStackScreenProps<
  RootStackParamList,
  "QuizScreen"
>;
export type SummaryScreenProps = NativeStackScreenProps<
  RootStackParamList,
  "StudySessionSummary"
>;
