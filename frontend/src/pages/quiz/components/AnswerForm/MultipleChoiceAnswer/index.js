import {
  useState,
  createRef,
  forwardRef,
  useImperativeHandle,
  useReducer,
  useMemo,
  useEffect,
} from "react";
import answerActions from "./answerReducer/constants";
import answerReducer, {
  initialAnswer,
  initializeAnswer,
} from "./answerReducer";
import { CHOICE_TEXT, CHOICE_AUDIO } from "./constants";
import { Form, Button, Container, Row, Col } from "react-bootstrap";
import { Square, CheckSquare, TrashFill } from "react-bootstrap-icons";
import AudioInputChoice from "./AudioInputChoice";
import TextInputChoice from "./TextInputChoice";
import styles from "./MultipleChoiceAnswer.module.scss";
import classNames from "classnames/bind";

const cx = classNames.bind(styles);

const MAX_CHOICES = 4;
const MIN_CHOICES = 2;

/**
 * @typedef {import("../typeDefs").MultipleChoiceAnswerDetail} MultipleChoiceAnswerDetail
 */
/**
 * @typedef {Object} ChoiceRef
 * @property {() => Promise<any>} validateAndGetData
 */
/**
 * @typedef {Object} MultipleChoiceAnswerProps
 * @property {MultipleChoiceAnswerDetail} data
 */
/**
 * @typedef {Object} MultipleChoiceAnswerRef
 * @property {() => Promise<MultipleChoiceAnswerDetail>} validateAndGetData
 */
/**
 * @type {React.ForwardRefExoticComponent<React.PropsWithoutRef<MultipleChoiceAnswerProps> & React.RefAttributes<MultipleChoiceAnswerRef>>}
 */
const MultipleChoiceAnswer = forwardRef(({ data = initialAnswer }, ref) => {
  const [answerState, dispatch] = useReducer(
    answerReducer,
    data || initialAnswer,
    initializeAnswer
  );
  const [validationErrors, setValidationErrors] = useState(null);

  useEffect(() => {
    dispatch({
      type: answerActions.INIT_STATE,
      payload: { data },
    });
  }, [data]);

  const refsByChoiceKey = useMemo(() => {
    /** @type {Map<number, React.RefObject<ChoiceRef>>} */
    const refs = new Map();
    answerState.choices.forEach((choice) => {
      refs.set(choice.key, createRef());
    });
    return refs;
  }, [answerState.choices]);

  useImperativeHandle(ref, () => ({
    /**
     * @returns {Promise<MultipleChoiceAnswerDetail>}
     */
    async validateAndGetData() {
      setValidationErrors(null);

      const choices = answerState.choices;
      const results = await Promise.allSettled(
        choices.map((choice) => {
          const ref = refsByChoiceKey.get(choice.key);
          return ref.current.validateAndGetData();
        })
      );

      const errors = {};

      // validate choices
      results.forEach((result, index) => {
        if (result.status === "rejected") {
          errors[choices[index].key] = result.reason.message;
        }
      });

      // validate correct choice
      if (answerState.correctChoice === -1) {
        errors.reason = "Select at least one correct choice";
      }

      // validate number of choices
      if (choices.length < MIN_CHOICES) {
        errors.reason = `At least ${MIN_CHOICES} choices are required`;
      }

      if (Object.keys(errors).length > 0) {
        setValidationErrors(errors);
        throw Error("Validation errors");
      }

      const validatedChoices = results.map((result, index) => {
        return result.value;
      });

      const correctChoiceIndex = choices.findIndex(
        (choice) => choice.key === answerState.correctChoice
      );

      return {
        choiceType: answerState.choiceType,
        correctChoice: correctChoiceIndex,
        choices: validatedChoices,
      };
    },
  }));
  return (
    <div>
      <Form.Group className="mb-3">
        <Form.Label>Choices Type</Form.Label>
        <Form.Control
          as="select"
          value={answerState.choiceType}
          onChange={(e) =>
            dispatch({
              type: answerActions.SET_CHOICES_TYPE,
              payload: { type: e.target.value },
            })
          }
        >
          <option value={CHOICE_TEXT}>Text</option>
          <option value={CHOICE_AUDIO}>Audio</option>
        </Form.Control>
      </Form.Group>
      <Form.Group>
        <Container fluid>
          <Row>
            {answerState.choices.map((choice) => {
              const ref = refsByChoiceKey.get(choice.key);
              return (
                <Col key={choice.key} lg={6} sm={12}>
                  <div className={cx("choice-wrapper")}>
                    <div className={cx("choice")}>
                      <button
                        className={cx("correct-btn", {
                          selected: answerState.correctChoice === choice.key,
                        })}
                        onClick={() => {
                          dispatch({
                            type: answerActions.SET_CORRECT_CHOICE,
                            payload: { correctChoice: choice.key },
                          });
                        }}
                      >
                        {answerState.correctChoice === choice.key ? (
                          <CheckSquare />
                        ) : (
                          <Square />
                        )}
                      </button>
                      <div className={cx("content")}>
                        {answerState.choiceType === CHOICE_TEXT ? (
                          <TextInputChoice ref={ref} data={choice.data} />
                        ) : (
                          <AudioInputChoice ref={ref} data={choice.data} />
                        )}
                      </div>
                    </div>
                    <div>
                      <button
                        className={cx("remove-btn")}
                        onClick={() =>
                          dispatch({
                            type: answerActions.REMOVE_CHOICE,
                            payload: { key: choice.key },
                          })
                        }
                      >
                        <TrashFill />
                      </button>
                    </div>
                  </div>
                  {validationErrors?.[choice.key] && (
                    <div className="text-danger">
                      {validationErrors[choice.key]}
                    </div>
                  )}
                </Col>
              );
            })}
            {validationErrors?.reason && (
              <Col>
                <div className="text-danger">{validationErrors.reason}</div>
              </Col>
            )}
          </Row>
        </Container>
      </Form.Group>
      <Button
        variant="primary"
        onClick={() => {
          if (answerState.choices.length >= MAX_CHOICES) {
            return;
          }
          dispatch({ type: answerActions.ADD_CHOICE });
        }}
        hidden={answerState.choices.length >= MAX_CHOICES}
      >
        Add Choice
      </Button>
      {validationErrors && (
        <div className="text-danger">{validationErrors.correctChoice}</div>
      )}
    </div>
  );
});

export default MultipleChoiceAnswer;
