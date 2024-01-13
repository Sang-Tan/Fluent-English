import { Modal, Card, CardBody, CardTitle, Button } from "react-bootstrap";
import AddedLessonList from "./components/AddedLessonList";
import PopupLessonList from "./components/PopupLessonList";
import { useEffect, useState } from "react";
import  useAsyncRequest from "src/hooks/useAsyncRequest";

function LessonListOfLearningPathManager({learningPathId}) {
   const [addedLessonList, setAddedLessonList] = useState([])
   const [fetchingAddedLessons, setFetchingAddedLessons] = useState(true);
   const [addedLessonsFetchErr, setAddedLessonsFetchErr] = useState(null);

   const [popopLessonListShowing, setPopopLessonListShowing] = useState(false);
   const [request] = useAsyncRequest();
   useEffect(() => {
     (async () => {
       try {
         const resp = await request(`/learning-paths/${learningPathId}/lessons`);
         setAddedLessonList(await resp.json());
       } catch (err) {
         setAddedLessonsFetchErr(err.message);
       } finally {
         setFetchingAddedLessons(false);
       }
     })();
   }, [learningPathId, request]);
  return (
  <>
    <Card>
      <CardBody>
        <div className="d-flex justify-content-between align-items-center">
          <CardTitle>Added lesson list</CardTitle>
        </div>
          <Button
            variant="primary"
            onClick={() => setPopopLessonListShowing(true)}
          >
          Add more lesson
          </Button>
    <AddedLessonList
        addedLessonList={addedLessonList}
        setAddedLessonList={setAddedLessonList}
        fetchingAddedLessons={fetchingAddedLessons}
        addedLessonsFetchErr={addedLessonsFetchErr}
     />
     <Modal show={popopLessonListShowing} onHide={() => setPopopLessonListShowing(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Lesson list</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <PopupLessonList 
              learningPathId={learningPathId}
              addedLessonList={addedLessonList}
              setAddedLessonList={setAddedLessonList}
              fetchingAddedLessons={fetchingAddedLessons}
              addedLessonsFetchErr={addedLessonsFetchErr}
          />
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setPopopLessonListShowing(false)}>
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
