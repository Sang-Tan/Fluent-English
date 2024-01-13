import { useParams, useNavigate } from "react-router-dom";
import useAsyncRequest from "src/hooks/useAsyncRequest";
import { useState, useEffect } from "react";
import LessonListOfLearningPathManager from "../LessonListOfLearningPathManager";

import { Form, Row, Col, Card, Button, Spinner, Modal } from "react-bootstrap";
import { toast } from "react-toastify";

function LearningPathDetail() {
  const [request] = useAsyncRequest();
  const { learningPathId } = useParams();
  const navigate = useNavigate();

  const [learningPath, setLearningPath] = useState(null);
  const [fetchingLearningPath, setFetchingLearningPath] = useState(true);
  const [learningPathFetchErr, setLearningPathFetchErr] = useState(null);

  const [confirmModalShow, setConfirmModalShow] = useState(false);
  const [isSubmitting, setSubmitting] = useState(false);

  const [deleteModalShow, setDeleteModalShow] = useState(false);
  const [isDeleting, setDeleting] = useState(false);

  useEffect(() => {
    (async () => {
      try {
        const resp = await request(`/learning-paths/${learningPathId}`);
        setLearningPath(await resp.json());
      } catch (err) {
        setLearningPathFetchErr(err.message);
      } finally {
        setFetchingLearningPath(false);
      }
    })();
  }, [learningPathId, request]);

  const handleSubmit = async () => {
    setConfirmModalShow(true);
  };

  const handleConfirm = async () => {
    setSubmitting(true);
    try {
      await request(`/learning-paths/${learningPathId}`, {
        method: "PUT",
        body: JSON.stringify(learningPath),
        headers: {
          "Content-Type": "application/json",
        },
      });
      toast.success("Learning path updated");
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
      await request(`/learning-paths/${learningPathId}`, {
        method: "DELETE",
      });
      toast.success("Learning path deleted");
      navigate(`/learning-path`);
    } catch (err) {
      toast.error(err.message);
    } finally {
      setDeleting(false);
      setDeleteModalShow(false);
    }
  };

  return (
    <>
      {fetchingLearningPath ? (
        <Spinner animation="border" />
      ) : learningPathFetchErr ? (
        <div>{learningPathFetchErr}</div>
      ) : (
          <section>
            <Row>
              <Col>
                <Card>
                  <Card.Body>
                    <div className="d-flex justify-content-between align-items-center">
                      <Card.Title>Learning path</Card.Title>
                      <Button
                        variant="danger"
                        onClick={() => setDeleteModalShow(true)}
                      >
                        Delete
                      </Button>
                    </div>
                    <Form>
                      <Form.Group className="mb-3">
                        <Form.Label>Name</Form.Label>
                        <Form.Control
                          type="text"
                          name="name"
                          value={learningPath?.name}
                          onChange={(e) => {
                            setLearningPath({
                              ...learningPath,
                              name: e.target.value,
                            });
                          }}
                        />
                      </Form.Group>
                      <Form.Group className="mb-3">
                        <Form.Label>Description</Form.Label>
                        <Form.Control
                          type="text"
                          name="description"
                          value={learningPath?.description}
                          onChange={(e) => {
                            setLearningPath({
                              ...learningPath,
                              description: e.target.value,
                            });
                          }}
                        />
                      </Form.Group>

                      <Button
                        onClick={handleSubmit}
                      >
                        {"Submit"}
                      </Button>
                    </Form>
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
                <LessonListOfLearningPathManager learningPathId={learningPathId}/>
              </Col>
            </Row>
          </section>
      )}
    </>
  );
}

export default LearningPathDetail;
