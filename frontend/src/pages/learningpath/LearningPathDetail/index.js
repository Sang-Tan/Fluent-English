import { useParams, useNavigate } from "react-router-dom";
import useAsyncRequest from "src/hooks/useAsyncRequest";
import { useState, useEffect } from "react";
import LessonListOfLearningPathManager from "./components/LessonListOfLearningPathManager";

import { Form, Row, Col, Card, Button, Spinner, Modal } from "react-bootstrap";
import { toast } from "react-toastify";

function LearningPathDetail() {
  const [request] = useAsyncRequest();
  const { learningPathId } = useParams();
  const navigate = useNavigate();

  const [published, setPublished] = useState(false);
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

  const changePublicity = async (isPublished) => {
    try {
      const resp = await request(`/learning-paths/${learningPathId}/publicity`, {
        method: "PUT",
        body: JSON.stringify({
          isPublic: isPublished,
        }),
        headers: {
          "Content-Type": "application/json",
        },
      });
      if (resp.status !== 204) {
        throw new Error("Something went wrong");
      }
      setPublished(isPublished);
      toast.success("Learning path publicity updated");
    } catch (err) {
      toast.error(err.message);
    }
  };


  const handleSubmit = async () => {
    setConfirmModalShow(true);
  };

  const handleConfirm = async () => {
    setSubmitting(true);
    try {
      const resp = await request(`/learning-paths/${learningPathId}`, {
        method: "PUT",
        body: JSON.stringify({
          name: learningPath.name,
          description: learningPath.description,
        }),
        headers: {
          "Content-Type": "application/json",
        },
      });
      if(!resp.ok) {
        const body = await resp.json();
        var errMessage = body.message;
        if(JSON.parse(body.error).name) {
          errMessage += ", " + JSON.parse(body.error).name;
        }
        if(JSON.parse(body.error).description) {
          errMessage += ", " + JSON.parse(body.error).description;
        }
        toast.error(errMessage);

        return;
      }
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
                    <Form.Check type="switch" id="published-switch">
                      <Form.Check.Input
                        type="checkbox"
                        checked={published}
                        onChange={(e) => changePublicity(e.target.checked)}
                      />
                      <Form.Check.Label>Published</Form.Check.Label>
                    </Form.Check>
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
