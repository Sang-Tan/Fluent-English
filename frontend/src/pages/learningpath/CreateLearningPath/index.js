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

  const [createError, setCreateError] = useState(null);

  const handleSubmit = async () => {
    setConfirmModalShow(true);
  };

  const handleConfirm = async (event) => {
    setSubmitting(true);
    try {
      const resp = await request(`/learning-paths`, {
        method: "POST",
        body: JSON.stringify(learningPath),
        headers: {
          "Content-Type": "application/json",
        },
      });
      if(!resp.ok) {
        const body = await resp.json();
        setCreateError(body.error);
        console.log(body);
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
      toast.success("Learning path created");
      navigate("/learning-path");
    } catch (err) {
      toast.error(err.message);
    } finally {
      setSubmitting(false);
      setConfirmModalShow(false);
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
                    required
                    type="text"
                    name="name"
                    onChange={(e) => {
                      setLearningPath({
                        ...learningPath,
                        name: e.target.value,
                      });
                    }}
                  />
                  <Form.Control.Feedback type="invalid">
                    {createError?.name}
                  </Form.Control.Feedback>
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
