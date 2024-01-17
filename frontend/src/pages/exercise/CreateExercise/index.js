import { ENDPOINT_EXERCISE } from "src/apis/endpoints";
import { PATH_LESSON } from "src/routes/paths";

import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import parseUrl from "src/helpers/pasteUrl";
import useAsyncRequest from "src/hooks/useAsyncRequest";
import { toast } from "react-toastify";

import ExerciseForm from "../components/ExerciseForm";
import { Row, Col, Card, Alert } from "react-bootstrap";

function CreateExercise() {
  const navigate = useNavigate();
  const { lessonId } = useParams();
  const [request] = useAsyncRequest();
  const [errorMessage, setErrorMessage] = useState(null);
  const [paramError, setParamError] = useState(null);

  const handleSubmit = async (exercise) => {
    try {
      const resp = await request(parseUrl(ENDPOINT_EXERCISE.ADD), {
        method: "POST",
        body: JSON.stringify({
          ...exercise,
          lessonId,
        }),
        headers: {
          "Content-Type": "application/json",
        },
      });
      if (resp.ok) {
        setParamError(null);
        setErrorMessage(null);
        toast.success("Exercise created");
        navigate(parseUrl(PATH_LESSON.DETAIL, { lessonId }));
      } else if (resp.status === 400) {
        const data = await resp.json();
        setParamError(data.error);
        setErrorMessage(data.message);
      } else {
        setErrorMessage("Something went wrong");
      }
    } catch (err) {
      setErrorMessage("Something went wrong");
      console.error(err);
    }
  };

  return (
    <section>
      <Row>
        <Col>
          <Card>
            <Card.Body>
              <Card.Title>Create Exercise (Lesson {lessonId})</Card.Title>
              {errorMessage && (
                <Alert
                  variant="danger"
                  onClose={() => setErrorMessage("")}
                  dismissible
                >
                  {errorMessage}
                </Alert>
              )}
              <ExerciseForm error={paramError} onSubmit={handleSubmit} />
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </section>
  );
}

export default CreateExercise;
