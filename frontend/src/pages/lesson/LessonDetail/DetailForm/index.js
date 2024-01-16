import { ENDPOINT_LESSON } from "src/apis/endpoints";
import { PATH_LESSON } from "src/routes/paths";
import { useCallback, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import useRequest from "src/hooks/useRequest";
import useAsyncRequest from "src/hooks/useAsyncRequest";
import parseUrl from "src/helpers/pasteUrl";
import { Card, Form, Button, Alert, Spinner, Modal } from "react-bootstrap";
import { EyeSlash, EyeFill } from "react-bootstrap-icons";

function DetailForm({ lessonId }) {
  const [lesson, setLesson] = useState(null);
  const [fetchLoading, setFetchLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState(null);
  const [paramError, setParamError] = useState(null);
  const [deleteModalShow, setDeleteModalShow] = useState(false);
  const [publicityModalShow, setPublicityModalShow] = useState(false);
  const [asyncRequest] = useAsyncRequest();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchLesson = async () => {
      setFetchLoading(true);
      try {
        const response = await asyncRequest(
          parseUrl(ENDPOINT_LESSON.DETAIL, { lessonId })
        );
        if (response.ok) {
          const data = await response.json();
          setLesson(data);
        } else {
          setErrorMessage("Something went wrong");
        }
      } catch (exception) {
        setErrorMessage("Something went wrong");
        console.error("Error while updating lesson", exception);
      }
      setFetchLoading(false);
    };
    fetchLesson();
  }, [lessonId, asyncRequest]);

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

  const deleteLesson = async () => {
    const options = {
      method: "delete",
    };

    try {
      const response = await asyncRequest(
        parseUrl(ENDPOINT_LESSON.DELETE, { lessonId }),
        options
      );

      if (response.ok) {
        toast.success("Lesson deleted successfully");
        navigate(PATH_LESSON.LIST);
      } else if (response.status === 400) {
        const data = await response.json();
        toast.error(data?.message || "Something went wrong");
      } else {
        toast.error("Something went wrong");
      }
    } catch (exception) {
      toast.error("Something went wrong");
      console.error("Error while deleting lesson", exception);
    }
    setDeleteModalShow(false);
  };

  const setPublicity = async (isPublic) => {
    const options = {
      method: "put",
      body: JSON.stringify({ isPublic }),
      headers: {
        "Content-Type": "application/json",
      },
    };

    try {
      const response = await asyncRequest(
        parseUrl(ENDPOINT_LESSON.SET_PUBLICITY, { lessonId }),
        options
      );

      if (response.ok) {
        toast.success("Lesson's publicity updated successfully");
        setLesson({ ...lesson, isPublic });
      } else if (response.status === 400) {
        const data = await response.json();
        toast.error(data?.message || "Something went wrong");
      } else {
        toast.error("Something went wrong");
      }
    } catch (exception) {
      toast.error("Something went wrong");
      console.error("Error while updating lesson", exception);
    }
    setPublicityModalShow(false);
  };

  return (
    <>
      <Card>
        <Card.Body>
          <div className="d-flex justify-content-between align-items-center">
            <Card.Title>Update Lesson</Card.Title>
            <div>
              <Button
                variant="outline-secondary"
                onClick={() => setPublicityModalShow(true)}
                title={lesson?.isPublic ? "Make Private" : "Make Public"}
                className="me-2"
              >
                {lesson?.isPublic ? (
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
                {loading ? <Spinner animation="border" size="sm" /> : "Update"}
              </Button>
            </Form>
          )}
        </Card.Body>
      </Card>

      {/* Delete modal */}
      <Modal
        show={deleteModalShow}
        onHide={() => setDeleteModalShow(false)}
        centered
      >
        <Modal.Header closeButton>
          <Modal.Title>Delete Lesson</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <p>Are you sure you want to delete this lesson?</p>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setDeleteModalShow(false)}>
            Cancel
          </Button>
          <Button variant="danger" onClick={deleteLesson}>
            Delete
          </Button>
        </Modal.Footer>
      </Modal>

      {/* Public modal */}
      <Modal
        show={publicityModalShow}
        onHide={() => setPublicityModalShow(false)}
        centered
      >
        <Modal.Header closeButton>
          <Modal.Title>
            {lesson?.isPublic ? "Make Private" : "Make Public"}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <p>
            Are you sure you want to make this lesson{" "}
            {lesson?.isPublic ? "private" : "public"}?
          </p>
        </Modal.Body>
        <Modal.Footer>
          <Button
            variant="secondary"
            onClick={() => setPublicityModalShow(false)}
          >
            Cancel
          </Button>
          <Button
            variant="primary"
            onClick={() => setPublicity(lesson?.isPublic ? false : true)}
          >
            {lesson?.isPublic ? "Make Private" : "Make Public"}
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default DetailForm;
