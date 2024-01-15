import { Row, Col, Card, Form, Button, Alert, Spinner } from "react-bootstrap";
import ExerciseList from "./ExerciseList";
import { useCallback, useState } from "react";
import { useParams } from "react-router-dom";
import { toast } from "react-toastify";
import useRequest from "src/hooks/useRequest";
import useFetch from "src/hooks/useFetch";
import "react-toastify/dist/ReactToastify.css";

function LessonDetail() {
  const { lessonId } = useParams();
  const { data: lesson, loading: fetchLoading } = useFetch(
    `/lessons/${lessonId}`
  );
  const [errorMessage, setErrorMessage] = useState(null);
  const [paramError, setParamError] = useState(null);

  const handleUpdateResponse = useCallback(async (response) => {
    if (response.ok) {
      toast.success("Lesson updated successfully");
    } else if (response.status === 400) {
      const data = await response.json();
      setParamError(data?.paramError);
    } else {
      setErrorMessage("Something went wrong");
    }
  }, []);

  const handleUpdateException = useCallback((exception) => {
    setErrorMessage("Something went wrong");
    console.error("Error while updating lesson", exception);
  }, []);

  const [request, loading] = useRequest({
    onResponse: handleUpdateResponse,
    onException: handleUpdateException,
  });

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    const requestData = {};
    for (let [key, value] of data.entries()) {
      requestData[key] = value;
    }

    const options = {
      method: "put",
      body: JSON.stringify(requestData),
      headers: {
        "Content-Type": "application/json",
      },
    };

    request("/lessons/" + lessonId, options);
  };

  return (
    <>
      <section>
        <Row>
          <Col>
            <Card>
              <Card.Body>
                <Card.Title>Update Lesson</Card.Title>
                {fetchLoading ? (
                  <Spinner animation="border" />
                ) : (
                  <Form onSubmit={handleSubmit}>
                    {errorMessage && (
                      <Alert
                        variant="danger"
                        onClose={() => setErrorMessage(null)}
                        dismissible
                      >
                        {errorMessage}
                      </Alert>
                    )}
                    <Form.Group className="mb-3">
                      <Form.Label>Name</Form.Label>
                      <Form.Control
                        type="text"
                        name="name"
                        defaultValue={lesson?.name}
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
                        "Update"
                      )}
                    </Button>
                  </Form>
                )}
              </Card.Body>
            </Card>
            <ExerciseList lessonId={lessonId} />
          </Col>
        </Row>
      </section>
    </>
  );
}

export default LessonDetail;
