import { useParams, useNavigate } from "react-router-dom";
import useAsyncRequest from "src/hooks/useAsyncRequest";
import { useState, useRef, useEffect } from "react";

import { Row, Col, Card, Button, Spinner, Modal } from "react-bootstrap";
import QuizUploadContextProvider from "../components/QuizUploadContext";
import AnswerForm from "../components/AnswerForm";
import QuestionForm from "../components/QuestionForm";
import { toast } from "react-toastify";

function QuizDetail() {
  const [request] = useAsyncRequest();
  const { quizId } = useParams();
  const navigate = useNavigate();

  const [quiz, setQuiz] = useState(null);
  const [fetchingQuiz, setFetchingQuiz] = useState(true);
  const [quizFetchErr, setQuizFetchErr] = useState(null);

  const [confirmModalShow, setConfirmModalShow] = useState(false);
  const [isValidating, setValidating] = useState(false);
  const [isSubmitting, setSubmitting] = useState(false);

  const [deleteModalShow, setDeleteModalShow] = useState(false);
  const [isDeleting, setDeleting] = useState(false);

  /** @type {React.MutableRefObject<import("../components/QuestionForm").QuestionFormRef>} */
  const questionRef = useRef();

  /** @type {React.MutableRefObject<import("../components/AnswerForm").AnswerFormRef>} */
  const answerRef = useRef();

  useEffect(() => {
    (async () => {
      try {
        const resp = await request(`/quizzes/${quizId}`);
        setQuiz(await resp.json());
      } catch (err) {
        setQuizFetchErr(err.message);
      } finally {
        setFetchingQuiz(false);
      }
    })();
  }, [quizId, request]);

  const handleSubmit = async () => {
    setValidating(true);
    const [questionResult, answerResult] = await Promise.allSettled([
      questionRef.current.validateAndGetData(),
      answerRef.current.validateAndGetData(),
    ]);
    setValidating(false);

    if (questionResult.status === "rejected") {
      return;
    }

    if (answerResult.status === "rejected") {
      return;
    }

    setQuiz({
      question: questionResult.value,
      answer: answerResult.value,
    });

    setConfirmModalShow(true);
  };

  const handleConfirm = async () => {
    setSubmitting(true);
    try {
      await request(`/quizzes/${quizId}`, {
        method: "PUT",
        body: JSON.stringify(quiz),
        headers: {
          "Content-Type": "application/json",
        },
      });
      toast.success("Quiz updated");
    } catch (err) {
      toast.error(err.message);
    } finally {
      setSubmitting(false);
      setConfirmModalShow(false);
    }
  };

  const handleConfirmDelete = async () => {
    setDeleting(true);
    try {
      await request(`/quizzes/${quizId}`, {
        method: "DELETE",
      });
      toast.success("Quiz deleted");
      navigate(`/exercise/${quiz.exerciseId}`);
    } catch (err) {
      toast.error(err.message);
    } finally {
      setDeleting(false);
      setDeleteModalShow(false);
    }
  };

  return (
    <>
      {fetchingQuiz ? (
        <Spinner animation="border" />
      ) : quizFetchErr ? (
        <div>{quizFetchErr}</div>
      ) : (
        <QuizUploadContextProvider uploadFolder="quiz">
          <section>
            <Row>
              <Col>
                <Card>
                  <Card.Body>
                    <div className="d-flex justify-content-between align-items-center">
                      <Card.Title>Quiz</Card.Title>
                      <Button
                        variant="danger"
                        onClick={() => setDeleteModalShow(true)}
                      >
                        Delete
                      </Button>
                    </div>
                    <Card.Subtitle>Question</Card.Subtitle>
                    <div className="mb-3">
                      <QuestionForm data={quiz.question} ref={questionRef} />
                    </div>

                    <Card.Subtitle>Answers</Card.Subtitle>
                    <div className="mb-3">
                      <AnswerForm
                        data={quiz.answer}
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
                          onClick={() => setConfirmModalShow(false)}
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
                    <Modal show={deleteModalShow}>
                      <Modal.Header closeButton>
                        <Modal.Title>Confirm</Modal.Title>
                      </Modal.Header>
                      <Modal.Body>Are you sure?</Modal.Body>
                      <Modal.Footer>
                        <Button
                          variant="secondary"
                          onClick={() => setDeleteModalShow(false)}
                        >
                          Cancel
                        </Button>
                        <Button variant="primary" onClick={handleConfirmDelete}>
                          {isDeleting ? (
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
      )}
    </>
  );
}

export default QuizDetail;
