import DataTable from "src/components/DataTable";
import { Button, Card, CardBody, CardTitle, Spinner } from "react-bootstrap";
import { useCallback } from "react";
import { Link } from "react-router-dom";

function AddedLessonList({addedLessonList, setAddedLessonList, fetchingAddedLessons, addedLessonsFetchErr}) {
  const removelessonFromLessonList = useCallback( async (lessonId, LessonList ) => {
    const newLessonList = LessonList.filter((lesson) => lesson.id !== lessonId);
    setAddedLessonList(newLessonList);
  }, [setAddedLessonList]);

  addedLessonList.forEach((lesson) => {
    lesson.details = (
      <Link to={`/lesson/${lesson.id}`}>
        <Button variant="primary">Details</Button>
      </Link>
    );
    lesson.remove = (
      <Button variant="danger" onClick={() => removelessonFromLessonList(lesson.id, addedLessonList)}>
        Remove
      </Button>
    );
  });

  return (
  <>
      {fetchingAddedLessons ? (
        <Spinner animation="border" />
      ) : addedLessonsFetchErr ? (
        <div>{addedLessonsFetchErr}</div>
      ) : (
    <section>
      <Card>
        <CardBody>
          <div className="d-flex justify-content-between align-items-center">
            <CardTitle>Added lesson list</CardTitle>
          </div>
          <DataTable
            columns={[
              {
                id: "name",
                label: "Name",
              },
              {
                id: "details",
                label: "Details",
                width: "100px",
              },
              {
                id: "remove",
                label: "Remove",
                width: "100px",
              }
            ]}
            data={addedLessonList}
          />
        </CardBody>
      </Card>
    </section>
      )}
    </>
    );
}

export default AddedLessonList;
