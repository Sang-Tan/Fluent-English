import {
  INIT_STATE,
  ADD_CHOICE,
  REMOVE_CHOICE,
  SET_CORRECT_CHOICE,
  SET_CHOICES_TYPE,
  SET_CHOICES,
} from "./constants";
import { CHOICE_TEXT } from "../constants";

const DEFAULT_CHOICE_TYPE = CHOICE_TEXT;

/** @type {import("../../typeDefs").MultipleChoiceAnswerDetail} */
const initialAnswer = {
  choices: {
    type: DEFAULT_CHOICE_TYPE,
    data: [],
  },
  correctChoice: -1,
};

/**
 * @param {import("../typeDefs").MultipleChoiceAnswerState} state
 * @returns {import("../typeDefs").MultipleChoiceAnswerState}
 */
const answerReducer = (state, action) => {
  switch (action.type) {
    case INIT_STATE:
      return initializeAnswer(action.payload.data);
    case ADD_CHOICE:
      return addChoice(state);
    case REMOVE_CHOICE:
      return removeChoice(state, action.payload.key);
    case SET_CORRECT_CHOICE:
      return setCorrectChoice(state, action.payload.correctChoice);
    case SET_CHOICES_TYPE:
      return setType(state, action.payload.type);
    case SET_CHOICES:
      return setChoices(state, action.payload.choices);
    default:
      throw Error("Invalid action type");
  }
};

/**
 * @param {import("../../typeDefs").MultipleChoiceAnswerDetail} data
 * @returns {import("../typeDefs").MultipleChoiceAnswerState}
 */
const initializeAnswer = (data) => {
  if (!data) {
    data = initialAnswer;
  }

  return {
    ...data,
    choiceType: data.choices.type,
    choices: data.choices.data.map((choice, index) => {
      return {
        data: choice,
        key: index,
      };
    }),
  };
};

/**
 * @param {import("../typeDefs").MultipleChoiceAnswerState} state
 * @returns {import("../typeDefs").MultipleChoiceAnswerState}
 */
const addChoice = (state) => {
  const choices = state.choices;
  const maxKey = choices.reduce((max, choice) => Math.max(max, choice.key), -1);
  return {
    ...state,
    choices: [...choices, { key: maxKey + 1 }],
  };
};

/**
 * @param {import("../typeDefs").MultipleChoiceAnswerState} state
 * @param {number} key
 * @returns {import("../typeDefs").MultipleChoiceAnswerState}
 */
const removeChoice = (state, key) => {
  const choices = state.choices;
  const removedIndex = choices.findIndex((choice) => choice.key === key);
  let correctIndex = state.correctChoice;
  if (correctIndex === removedIndex) {
    correctIndex = -1;
  }

  return {
    ...state,
    choices: choices.filter((choice) => choice.key !== key),
    correctChoice: correctIndex,
  };
};

/**
 * @param {import("../typeDefs").MultipleChoiceAnswerState} state
 * @param {number} correctIndex
 * @returns {import("../typeDefs").MultipleChoiceAnswerState}
 */
const setCorrectChoice = (state, correctIndex) => {
  return {
    ...state,
    correctChoice: correctIndex,
  };
};

/**
 * @param {import("../typeDefs").MultipleChoiceAnswerState} state
 * @param {string} type
 * @returns {import("../typeDefs").MultipleChoiceAnswerState}
 */
const setType = (state, type) => {
  const choices = state.choices;
  return {
    ...state,
    choiceType: type,
    choices: choices.map((choice, index) => {
      return {
        key: index,
      };
    }),
    correctChoice: -1,
  };
};

/**
 * @param {AnswerState} state
 * @param {ChoiceState[]} choices
 * @returns {AnswerState}
 */
const setChoices = (state, choices) => {
  return {
    ...state,
    choices: choices.map((choice, index) => {
      return {
        ...choice,
        key: index,
      };
    }),
  };
};

export { initialAnswer, initializeAnswer };
export default answerReducer;
