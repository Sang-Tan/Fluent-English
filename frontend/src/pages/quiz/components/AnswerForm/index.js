import { forwardRef, useRef, useState, useImperativeHandle } from "react";
import { INPUT_ANSWER, MULTIPLE_CHOICE_ANSWER } from "./constants";

import { Form } from "react-bootstrap";
import InputAnswer from "./InputAnswer";
import MultipleChoiceAnswer from "./MultipleChoiceAnswer";
import styles from "./AnswerForm.module.scss";
import classNames from "classnames/bind";

const cx = classNames.bind(styles);

/**
 * @typedef {import("./typeDefs").InputAnswerData} InputAnswerData
 * @typedef {import("./typeDefs").MultipleChoiceAnswerData} MultipleChoiceAnswerData
 */
/**
 * @typedef {Object} AnswerFormProps
 * @property {InputAnswerData | MultipleChoiceAnswerData} data
 */
/**
 * @typedef {Object} AnswerFormRef
 * @property {() => Promise<InputAnswerData | MultipleChoiceAnswerData>} validateAndGetData
 */
/**
 * @type {React.ForwardRefExoticComponent<React.PropsWithoutRef<AnswerFormProps> & React.RefAttributes<AnswerFormRef>>}
 */
const AnswerForm = forwardRef(({ data }, ref) => {
  const [type, setType] = useState(data?.type || INPUT_ANSWER);
  const [explanation, setExplanation] = useState(data?.explanation || "");
  const [validationError, setValidationError] = useState(null);
  const childRef = useRef();

  useImperativeHandle(ref, () => ({
    /**
     * @returns {Promise<InputAnswerData | MultipleChoiceAnswerData>}
     */
    async validateAndGetData() {
      setValidationError(null);

      const childData = await childRef.current.validateAndGetData();

      return {
        ...childData,
        type,
        explanation: explanation ? explanation : undefined,
      };
    },
  }));

  return (
    <div>
      <div className={cx("type-header")}>
        <h5 className={cx("title")}>Answer type</h5>
        <Form.Group controlId="answerType" className="mb-3">
          <Form.Control
            as="select"
            value={type}
            onChange={(e) => {
              setType(e.target.value);
              setExplanation("");
            }}
          >
            <option value={INPUT_ANSWER}>Input</option>
            <option value={MULTIPLE_CHOICE_ANSWER}>Multiple choice</option>
          </Form.Control>
        </Form.Group>
      </div>
      <div className={cx("answer-wrapper")}>
        <div className="mb-3">
          {type === INPUT_ANSWER && (
            <InputAnswer
              ref={childRef}
              data={data?.type === INPUT_ANSWER ? data : null}
            />
          )}
          {type === MULTIPLE_CHOICE_ANSWER && (
            <MultipleChoiceAnswer
              ref={childRef}
              data={data?.type === MULTIPLE_CHOICE_ANSWER ? data : null}
            />
          )}
        </div>
        <Form.Group controlId="explanation" className="mb-3">
          <Form.Label>Explanation</Form.Label>
          <Form.Control
            as="textarea"
            name="explanation"
            value={explanation}
            onChange={(e) => setExplanation(e.target.value)}
          />
          {validationError?.explanation && (
            <Form.Text className="text-danger">
              {validationError.explanation}
            </Form.Text>
          )}
        </Form.Group>
      </div>
    </div>
  );
});

export default AnswerForm;
