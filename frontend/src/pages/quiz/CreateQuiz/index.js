import { useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useSearchParams } from "react-router-dom";
import useAsyncRequest from "src/hooks/useAsyncRequest";
import QuizUploadContextProvider from "../components/QuizUploadContext";

import { Row, Col, Card, Button, Spinner, Modal } from "react-bootstrap";
import QuestionForm from "../components/QuestionForm";
import AnswerForm from "../components/AnswerForm";
import { toast } from "react-toastify";

function CreateQuiz() {
  const [request] = useAsyncRequest();
  const [confirmModalShow, setConfirmModalShow] = useState(false);
  const [isValidating, setValidating] = useState(false);
  const [isSubmitting, setSubmitting] = useState(false);
  const [questionData, setQuestionData] = useState(null);
  const [answerData, setAnswerData] = useState(null);
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  const exerciseId = searchParams.get("exerciseId");

  /** @type {React.MutableRefObject<import("../components/QuestionForm").QuestionFormRef>} */
  const questionRef = useRef();

  /** @type {React.MutableRefObject<import("../components/AnswerForm").AnswerFormRef>} */
  const answerRef = useRef();

  const handleSubmit = async (event) => {
    event.preventDefault();

    let validated = true;
    const promises = [
      questionRef.current.validateAndGetData(),
      answerRef.current.validateAndGetData(),
    ];
    setValidating(true);
    const [questionResult, answerResult] = await Promise.allSettled(promises);

    if (questionResult.status === "rejected") {
      validated = false;
    } else {
      setQuestionData(questionResult.value);
    }

    if (answerResult.status === "rejected") {
      validated = false;
    } else {
      setAnswerData(answerResult.value);
    }

    setValidating(false);

    if (!validated) {
      return;
    }

    setConfirmModalShow(true);
  };

  const handleConfirm = async (event) => {
    const data = {
      question: questionData,
      answer: answerData,
      exerciseId,
    };

    setSubmitting(true);
    const resp = await request(`/quizzes`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });
    setSubmitting(false);

    if (resp.ok) {
      toast.success("Quiz created");
      navigate(`/exercise/${exerciseId}`);
    } else {
      alert("Error creating quiz");
    }

    setConfirmModalShow(false);
  };

  const closeModal = () => {
    setConfirmModalShow(false);
  };

  return (
    <>
      {exerciseId ? (
        <QuizUploadContextProvider uploadFolder="quiz">
          <section>
            <Row>
              <Col>
                <Card>
                  <Card.Body>
                    <Card.Title>Create Quiz (Exercise {exerciseId})</Card.Title>
                    <Card.Subtitle>Question</Card.Subtitle>
                    <div className="mb-3">
                      <QuestionForm data={questionData} ref={questionRef} />
                    </div>

                    <Card.Subtitle>Answers</Card.Subtitle>
                    <div className="mb-3">
                      <AnswerForm
                        data={answerData}
                        ref={answerRef}
                      ></AnswerForm>
                    </div>

                    <Button onClick={handleSubmit} disabled={isValidating > 0}>
                      {isValidating > 0 ? (
                        <Spinner animation="border" />
                      ) : (
                        "Submit"
                      )}
                    </Button>
                    <Modal show={confirmModalShow}>
                      <Modal.Header closeButton>
                        <Modal.Title>Confirm</Modal.Title>
                      </Modal.Header>
                      <Modal.Body>Are you sure?</Modal.Body>
                      <Modal.Footer>
                        <Button
                          variant="secondary"
                          onClick={() => closeModal()}
                        >
                          Cancel
                        </Button>
                        <Button variant="primary" onClick={handleConfirm}>
                          {isSubmitting ? (
                            <Spinner animation="border" />
                          ) : (
                            "Confirm"
                          )}
                        </Button>
                      </Modal.Footer>
                    </Modal>
                  </Card.Body>
                </Card>
              </Col>
            </Row>
          </section>
        </QuizUploadContextProvider>
      ) : (
        <div>Exercise not found</div>
      )}
    </>
  );
}

export default CreateQuiz;
