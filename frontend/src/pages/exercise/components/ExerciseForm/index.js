import {
  SKILL_LISTENING,
  SKILL_GRAMMAR,
  SKILL_READING,
} from "src/pages/exercise/constants";
import {
  LEVEL_EASY,
  LEVEL_HARD,
  LEVEL_MEDIUM,
} from "src/pages/exercise/constants";
import EXERCISE_ACTION from "./exerciseReducer/constants";
import exerciseReducer, { initializeExercise } from "./exerciseReducer";

import { Form, Button, Spinner } from "react-bootstrap";
import { useEffect, useState, useReducer } from "react";

/**
 * @typedef {import("./typeDefs").ExerciseFormData} ExerciseFormData
 * @typedef {import("./typeDefs").ExerciseFormError} ExerciseFormError
 */
/**
 * @typedef {Object} ExerciseFormProps
 * @property {ExerciseFormData} initialData
 * @property {(data: ExerciseFormData) => Promise<void>} onSubmit
 * @property {ExerciseFormError} error
 */
function ExerciseForm({ initialData, error, onSubmit }) {
  const [exercise, dispatch] = useReducer(
    exerciseReducer,
    initialData,
    initializeExercise
  );
  const [paramError, setParamError] = useState(error);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setParamError(error);
  }, [error]);

  useEffect(() => {
    dispatch({
      type: EXERCISE_ACTION.INITIALIZE,
      payload: initialData,
    });
  }, [initialData]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    const requestData = {};
    for (let [key, value] of data.entries()) {
      requestData[key] = value;
    }
    requestData.difficulty = parseInt(requestData.difficulty);

    setLoading(true);
    await onSubmit(requestData);
    setLoading(false);
  };

  return (
    <Form onSubmit={handleSubmit}>
      {/* name */}
      <Form.Group className="mb-3">
        <Form.Label>Name</Form.Label>
        <Form.Control
          type="text"
          name="name"
          isInvalid={paramError?.name}
          value={exercise.name}
          onChange={(event) =>
            dispatch({
              type: EXERCISE_ACTION.SET_NAME,
              payload: event.target.value,
            })
          }
        />
        <Form.Control.Feedback type="invalid">
          {paramError?.name}
        </Form.Control.Feedback>
      </Form.Group>

      {/* skill */}
      <Form.Group className="mb-3">
        <Form.Label>Skill</Form.Label>
        <Form.Select
          name="skill"
          isInvalid={paramError?.skill}
          value={exercise.skill}
          onChange={(event) =>
            dispatch({
              type: EXERCISE_ACTION.SET_SKILL,
              payload: event.target.value,
            })
          }
        >
          <option value={SKILL_LISTENING}>Listening</option>
          <option value={SKILL_READING}>Reading</option>
          <option value={SKILL_GRAMMAR}>Grammar</option>
        </Form.Select>
        <Form.Control.Feedback type="invalid">
          {paramError?.skill}
        </Form.Control.Feedback>
      </Form.Group>

      {/* difficulty */}
      <Form.Group className="mb-3">
        <Form.Label>Difficulty</Form.Label>
        <Form.Select
          name="difficulty"
          isInvalid={paramError?.difficulty}
          value={exercise.difficulty}
          onChange={(event) =>
            dispatch({
              type: EXERCISE_ACTION.SET_DIFFICULTY,
              payload: event.target.value,
            })
          }
        >
          <option value={LEVEL_EASY}>Easy</option>
          <option value={LEVEL_MEDIUM}>Medium</option>
          <option value={LEVEL_HARD}>Hard</option>
        </Form.Select>
        <Form.Control.Feedback type="invalid">
          {paramError?.difficulty}
        </Form.Control.Feedback>
      </Form.Group>

      {/* Introduction */}
      <Form.Group className="mb-3">
        <Form.Label>
          <span>
            Introduction <small className="text-muted">(optional)</small>
          </span>
        </Form.Label>

        <Form.Control
          as="textarea"
          name="introduction"
          value={exercise.introduction || ""}
          isInvalid={paramError?.introduction}
          onChange={(event) =>
            dispatch({
              type: EXERCISE_ACTION.SET_INTRODUCTION,
              payload: event.target.value,
            })
          }
        />
        <Form.Control.Feedback type="invalid">
          {paramError?.introduction}
        </Form.Control.Feedback>
      </Form.Group>
      {/* Submit */}
      <Button variant="primary" type="submit" disabled={loading}>
        {loading ? <Spinner animation="border" size="sm" /> : "Submit"}
      </Button>
    </Form>
  );
}

export default ExerciseForm;
