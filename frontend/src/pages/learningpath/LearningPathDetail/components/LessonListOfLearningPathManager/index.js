import {Modal, Card, CardBody, CardTitle, Button } from "react-bootstrap";
import AddedLessonList from "./components/AssignedLesson";
import PopupLessonList from "./components/UnassignedLessons";
import { useEffect, useState } from "react";
import useAsyncRequest from "src/hooks/useAsyncRequest";

function LessonListOfLearningPathManager({ learningPathId }) {
  const [assignedLessons, setAssignedLessons] = useState([]);
  const [fetchingAssignedLessons, setFetchingAddedLessons] = useState(true);
  const [assignedLessonsFetchErr, setAddedLessonsFetchErr] = useState(null);

  const [popopLessonListShowing, setPopopLessonListShowing] = useState(false);
  const [request] = useAsyncRequest();

  const reFetchLessonList = async () => {
    setFetchingAddedLessons(true);
    try {
      const resp = await request(`/learning-paths/${learningPathId}/lessons`);
      setAssignedLessons(await resp.json());
    } catch (err) {
      setAddedLessonsFetchErr(err.message);
    } finally {
      setFetchingAddedLessons(false);
    }
  };

  useEffect(() => {
    (async () => {
      try {
        const resp = await request(`/learning-paths/${learningPathId}/lessons`);
        setAssignedLessons(await resp.json());
      } catch (err) {
        setAddedLessonsFetchErr(err.message);
      } finally {
        setFetchingAddedLessons(false);
      }
    })();
  }, [learningPathId, request]);

  const changeAssignedLessons = (newLessons => setAssignedLessons(newLessons)); 

  return (
    <>
      <Card>
        <CardBody >
          <div className="d-flex justify-content-between align-items-center">
            <CardTitle>Added lesson list</CardTitle>
            <Button
              variant="primary"
              onClick={() => setPopopLessonListShowing(true)}
            >
              Add more lesson
            </Button>
          </div>
          
          <AddedLessonList
            learningPathId={learningPathId}
            assignedLessons={assignedLessons}
            onAssingedLessonsChange={changeAssignedLessons}
            fetchingAssignedLessons={fetchingAssignedLessons}
            assignedLessonsFetchErr={assignedLessonsFetchErr}
          />
          
          <Modal
            show={popopLessonListShowing}
            onHide={() => setPopopLessonListShowing(false)}
          >
            <Modal.Header>
              <Modal.Title>Lesson list</Modal.Title>
            </Modal.Header>
            <Modal.Body>
              <PopupLessonList
                learningPathId={learningPathId}
                fetchingAssignedLessons={fetchingAssignedLessons}
                assignedLessonsFetchErr={assignedLessonsFetchErr}
              />
              
            </Modal.Body>
            <Modal.Footer>
              <Button
                variant="secondary"
                onClick={() => {
                  setPopopLessonListShowing(false);
                  reFetchLessonList();
                }}
              >
                Close
              </Button>
            </Modal.Footer>
          </Modal>
        </CardBody>
      </Card>
    </>
  );
}

export default LessonListOfLearningPathManager;
