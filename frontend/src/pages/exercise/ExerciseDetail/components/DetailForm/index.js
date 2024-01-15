import { ENDPOINT_EXERCISE } from "src/apis/endpoints";

import { useEffect, useState } from "react";
import parseUrl from "src/helpers/pasteUrl";
import useAsyncRequest from "src/hooks/useAsyncRequest";
import { toast } from "react-toastify";
import { Card, Alert, Spinner } from "react-bootstrap";
import ExerciseForm from "src/pages/exercise/components/ExerciseForm";

function DetailForm({ exerciseId }) {
  const [exercise, setExercise] = useState(null);
  const [isFetching, setFetching] = useState(true);
  const [errorMessage, setErrorMessage] = useState(null);
  const [paramError, setParamError] = useState(null);

  const [request] = useAsyncRequest();

  useEffect(() => {
    const fetchExercise = async () => {
      setFetching(true);
      const resp = await request(
        parseUrl(ENDPOINT_EXERCISE.DETAIL, { exerciseId })
      );
      if (resp.ok) {
        const data = await resp.json();
        setExercise(data);
      } else {
        setErrorMessage("Something went wrong");
      }
      setFetching(false);
    };
    fetchExercise();
  }, [exerciseId, request]);

  const handleSubmit = async (exercise) => {
    try {
      const resp = await request(
        parseUrl(ENDPOINT_EXERCISE.UPDATE, { exerciseId }),
        {
          method: "PUT",
          body: JSON.stringify(exercise),
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      if (resp.ok) {
        setParamError(null);
        setErrorMessage(null);
        toast.success("Exercise updated");
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

  return isFetching ? (
    errorMessage ? (
      <Alert variant="danger">{errorMessage}</Alert>
    ) : (
      <Spinner animation="border" />
    )
  ) : (
    <Card>
      <Card.Body>
        <Card.Title>Update Exercise</Card.Title>
        {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
        <ExerciseForm
          initialData={exercise}
          onSubmit={handleSubmit}
          error={paramError}
        />
      </Card.Body>
    </Card>
  );
}

export default DetailForm;
