import { ENDPOINT_EXERCISE } from "src/apis/endpoints";
import { PATH_LESSON } from "src/routes/paths";

import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import parseUrl from "src/helpers/pasteUrl";
import useAsyncRequest from "src/hooks/useAsyncRequest";
import { toast } from "react-toastify";
import { Card, Alert, Spinner, Modal, Button } from "react-bootstrap";
import { EyeSlash, EyeFill } from "react-bootstrap-icons";
import ExerciseForm from "src/pages/exercise/components/ExerciseForm";

function DetailForm({ exerciseId }) {
  const [exercise, setExercise] = useState(null);
  const [isFetching, setFetching] = useState(true);
  const [errorMessage, setErrorMessage] = useState(null);
  const [paramError, setParamError] = useState(null);
  const [deleteModalShow, setDeleteModalShow] = useState(false);
  const [publicityModalShow, setPublicityModalShow] = useState(false);
  const [request] = useAsyncRequest();
  const navigate = useNavigate();

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
        setParamError(data?.paramError);
        setErrorMessage(data?.message);
      } else {
        setErrorMessage("Something went wrong");
      }
    } catch (err) {
      setErrorMessage("Something went wrong");
      console.error(err);
    }
  };

  const deleteExercise = async () => {
    try {
      const resp = await request(
        parseUrl(ENDPOINT_EXERCISE.DELETE, { exerciseId }),
        {
          method: "DELETE",
        }
      );
      if (resp.ok) {
        toast.success("Exercise deleted");
        navigate(parseUrl(PATH_LESSON.DETAIL, { lessonId: exercise.lessonId }));
      } else {
        toast.error("Something went wrong");
      }
    } catch (err) {
      toast.error("Something went wrong");
      console.error(err);
    }
    setDeleteModalShow(false);
  };

  const setPublicity = async (isPublic) => {
    try {
      const resp = await request(
        parseUrl(ENDPOINT_EXERCISE.SET_PUBLICITY, { exerciseId }),
        {
          method: "PUT",
          body: JSON.stringify({ isPublic }),
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      if (resp.ok) {
        toast.success("Exercise's publicity updated successfully");
        setExercise({ ...exercise, isPublic });
      }
    } catch (err) {
      toast.error("Something went wrong");
      console.error(err);
    }
    setPublicityModalShow(false);
  };

  return isFetching ? (
    errorMessage ? (
      <Alert variant="danger">{errorMessage}</Alert>
    ) : (
      <Spinner animation="border" />
    )
  ) : (
    <>
      <Card>
        <Card.Body>
          <div className="d-flex justify-content-between align-items-center">
            <Card.Title>Update Exercise</Card.Title>
            <div>
              <Button
                variant="outline-secondary"
                onClick={() => setPublicityModalShow(true)}
                title={exercise?.isPublic ? "Make Private" : "Make Public"}
                className="me-2"
              >
                {exercise?.isPublic ? (
                  <>
                    <EyeSlash />
                  </>
                ) : (
                  <>
                    <EyeFill />
                  </>
                )}
              </Button>
              <Button variant="danger" onClick={() => setDeleteModalShow(true)}>
                Delete
              </Button>
            </div>
          </div>
          {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
          <ExerciseForm
            initialData={exercise}
            onSubmit={handleSubmit}
            error={paramError}
          />
        </Card.Body>
      </Card>

      {/* Delete Modal */}
      <Modal show={deleteModalShow} onHide={() => setDeleteModalShow(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Delete Exercise</Modal.Title>
        </Modal.Header>
        <Modal.Body>Are you sure you want to delete this exercise?</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setDeleteModalShow(false)}>
            Close
          </Button>
          <Button variant="danger" onClick={deleteExercise}>
            Delete
          </Button>
        </Modal.Footer>
      </Modal>

      {/* Publicity Modal */}
      <Modal
        show={publicityModalShow}
        onHide={() => setPublicityModalShow(false)}
      >
        <Modal.Header closeButton>
          <Modal.Title>Update Exercise's Publicity</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          Are you sure you want to{" "}
          {exercise?.isPublic
            ? "make this exercise private"
            : "make this exercise public"}
          ?
        </Modal.Body>
        <Modal.Footer>
          <Button
            variant="secondary"
            onClick={() => setPublicityModalShow(false)}
          >
            Close
          </Button>
          <Button
            variant="primary"
            onClick={() => setPublicity(!exercise?.isPublic)}
          >
            {exercise?.isPublic ? "Make Private" : "Make Public"}
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default DetailForm;
