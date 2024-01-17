import { Row, Col, Card, Form, Button, Alert, Spinner } from "react-bootstrap";
import { useCallback, useState } from "react";
import { useNavigate } from "react-router-dom";
import useRequest from "src/hooks/useRequest";

function CreateLesson() {
  const navigate = useNavigate();
  const [errorMessage, setErrorMessage] = useState(null);
  const [paramError, setParamError] = useState(null);

  const handleResponse = useCallback(
    async (response) => {
      if (response.ok) {
        navigate("/lesson");
      } else if (response.status === 400) {
        const data = await response.json();
        setParamError(data?.paramError);
      } else {
        setErrorMessage("Something went wrong");
      }
    },
    [navigate]
  );

  const handleException = useCallback((exception) => {
    setErrorMessage("Something went wrong");
  }, []);

  const [request, loading] = useRequest({
    onResponse: handleResponse,
    onException: handleException,
  });

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    const requestData = {};
    for (let [key, value] of data.entries()) {
      requestData[key] = value;
    }

    const options = {
      method: "post",
      body: JSON.stringify(requestData),
      headers: {
        "Content-Type": "application/json",
      },
    };

    request("/lessons", options);
  };

  return (
    <section>
      <Row>
        <Col>
          <Card>
            <Card.Body>
              <Card.Title>Create Lesson</Card.Title>
              {errorMessage && (
                <Alert
                  variant="danger"
                  onClose={() => setErrorMessage("")}
                  dismissible
                >
                  {errorMessage}
                </Alert>
              )}
              <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3">
                  <Form.Label>Name</Form.Label>
                  <Form.Control
                    type="text"
                    name="name"
                    isInvalid={paramError?.name}
                  />
                  <Form.Control.Feedback type="invalid">
                    {paramError?.name}
                  </Form.Control.Feedback>
                </Form.Group>
                <Button variant="primary" type="submit" disabled={loading}>
                  {loading ? (
                    <Spinner animation="border" size="sm" />
                  ) : (
                    "Submit"
                  )}
                </Button>
              </Form>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </section>
  );
}

export default CreateLesson;
