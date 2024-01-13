import { useNavigate } from "react-router-dom";
import useAsyncRequest from "src/hooks/useAsyncRequest";
import { useState } from "react";

import { Form, Row, Col, Card, Button, Spinner, Modal } from "react-bootstrap";
import { toast } from "react-toastify";

function CreateLearningPath() {
  const [request] = useAsyncRequest();
  const navigate = useNavigate();

  const [learningPath, setLearningPath] = useState(null);

  const [confirmModalShow, setConfirmModalShow] = useState(false);
  const [isSubmitting, setSubmitting] = useState(false);

  const handleSubmit = async () => {
    setConfirmModalShow(true);
  };

  const handleConfirm = async () => {
    setSubmitting(true);
    try {
      await request(`/learning-paths`, {
        method: "POST",
        body: JSON.stringify(learningPath),
        headers: {
          "Content-Type": "application/json",
        },
      });
      toast.success("Learning path created");
    } catch (err) {
      toast.error(err.message);
    } finally {
      setSubmitting(false);
      setConfirmModalShow(false);
      navigate("/learning-path");
    }
  };

  return (
    <section>
      <Row>
        <Col>
          <Card>
            <Card.Body>
              <div className="d-flex justify-content-between align-items-center">
                <Card.Title>Learning path</Card.Title>
              </div>
              <Form>
                <Form.Group className="mb-3">
                  <Form.Label>Name</Form.Label>
                  <Form.Control
                    type="text"
                    name="name"
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
                    onChange={(e) => {
                      setLearningPath({
                        ...learningPath,
                        description: e.target.value,
                      });
                    }}
                  />
                </Form.Group>

                <Button onClick={handleSubmit}>{"Submit"}</Button>
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
                    {isSubmitting ? <Spinner animation="border" /> : "Confirm"}
                  </Button>
                </Modal.Footer>
              </Modal>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </section>
  );
}

export default CreateLearningPath;
