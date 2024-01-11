import { useImperativeHandle, forwardRef, useState, useEffect } from "react";
import { Form } from "react-bootstrap";

/**
 * @typedef {import("../typeDefs").InputAnswerDetail} InputAnswerDetail
 */
/**
 * @typedef {Object} InputAnswerProps
 * @property {InputAnswerDetail} data
 */
/**
 * @typedef {Object} InputAnswerRef
 * @property {() => Promise<InputAnswerDetail>} validateAndGetData
 */
/**
 * @type {React.ForwardRefExoticComponent<React.PropsWithoutRef<InputAnswerProps> & React.RefAttributes<InputAnswerRef>>}
 */
const InputAnswer = forwardRef(({ data }, ref) => {
  const [pendingAnswer, setPendingAnswer] = useState(data?.answer || "");
  const [error, setError] = useState(null);

  useEffect(() => {
    setPendingAnswer(data?.answer || "");
  }, [data?.answer]);

  useImperativeHandle(ref, () => ({
    async validateAndGetData() {
      setError(null);

      if (!pendingAnswer) {
        setError("Answer is required");
        throw new Error("Validation error");
      }

      return {
        answer: pendingAnswer,
      };
    },
  }));

  return (
    <div>
      <Form.Group>
        <Form.Label>Answer</Form.Label>
        <Form.Control
          type="text"
          value={pendingAnswer}
          onChange={(e) => setPendingAnswer(e.target.value)}
          placeholder="Enter answer"
        />
      </Form.Group>
      {error && <p className="text-danger">{error}</p>}
    </div>
  );
});

export default InputAnswer;
