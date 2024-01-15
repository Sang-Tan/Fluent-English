import EXERCISE_ACTION from "./constants";

/**
 * @typedef {import("../typeDefs").ExerciseFormData} ExerciseFormState
 */

/** @type {ExerciseFormState} */
const initialExercise = {
  name: "",
  skill: "",
  difficulty: 1,
  introduction: null,
};

/**
 * @param {ExerciseFormState} state
 * @returns {ExerciseFormState}
 */
const exerciseReducer = (state, action) => {
  switch (action.type) {
    case EXERCISE_ACTION.INITIALIZE:
      return initializeExercise(action.payload);
    case EXERCISE_ACTION.SET_NAME:
      return setExerciseName(state, action.payload);
    case EXERCISE_ACTION.SET_SKILL:
      return setExerciseSkill(state, action.payload);
    case EXERCISE_ACTION.SET_DIFFICULTY:
      return setExerciseDifficulty(state, action.payload);
    case EXERCISE_ACTION.SET_INTRODUCTION:
      return setExerciseIntroduction(state, action.payload);
    default:
      throw new Error(`Invalid action type: ${action.type}`);
  }
};

const initializeExercise = (initialData) => {
  return initialData || initialExercise;
};

const setExerciseName = (state, name) => {
  return {
    ...state,
    name,
  };
};

const setExerciseSkill = (state, skill) => {
  return {
    ...state,
    skill,
  };
};

const setExerciseDifficulty = (state, difficulty) => {
  return {
    ...state,
    difficulty: parseInt(difficulty),
  };
};

const setExerciseIntroduction = (state, introduction) => {
  return {
    ...state,
    introduction,
  };
};

export { initialExercise, initializeExercise };
export default exerciseReducer;
