import DataTable from "src/components/DataTable";
import { Button, Spinner } from "react-bootstrap";
import  useAsyncRequest from "src/hooks/useAsyncRequest";
import { useState } from "react";
import { Link } from "react-router-dom";

function AssignedLesson({learningPathId, assignedLessons, onAssingedLessonsChange, fetchingAssignedLessons, assignedLessonsFetchErr}) {
  const [removingLesson, setRemovingLesson] = useState(false);
  const [removeLessonErr, setRemoveLessonErr] = useState([]);
  const [request] = useAsyncRequest();

  const removelessonFromLessonList = async (lessonId) => {
    setRemovingLesson(true);
    try{
      const resp = await request(`/learning-paths/${learningPathId}/lessons/${lessonId}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
      });
      if(resp.status !== 204){
        throw new Error("Something went wrong");
      }
      const newLessons = assignedLessons.filter((lesson) => lesson.id !== lessonId);
      onAssingedLessonsChange(newLessons);
    }
    catch(err){
      setRemoveLessonErr((preErr) => [...preErr, {id: lessonId, message: err.message}])
    }
    finally{
      setRemovingLesson(false)
    }
  };

  const renderLessons = assignedLessons.map((lesson) => {
    const details = <Link to={`/lesson/${lesson.id}`}>
      <Button variant="primary">Details</Button>
    </Link>;
    const remove = (
      <>
        {removingLesson && removeLessonErr.some((err) => err.id === lesson.id) ? (
          <>
            <div className="error-message">
              {removeLessonErr.find((err) => err.id === lesson.id).message}
            </div>
            <Button
              variant="primary"
              disabled={removingLesson}
              onClick={() => removelessonFromLessonList(lesson.id)}
            >
              Remove
            </Button>
          </>
        ) : (
          <Button
            variant="primary"
            disabled={removingLesson}
            onClick={() => removelessonFromLessonList(lesson.id)}
          >
            Remove
          </Button>
        )}
      </>
    )
    return {name: lesson.name, details: details, remove: remove}
  });

  return (
  <>
      {fetchingAssignedLessons ? (
        <Spinner animation="border" />
      ) : assignedLessonsFetchErr ? (
        <div>{assignedLessonsFetchErr}</div>
      ) : (
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
            data={renderLessons}
          />
      )}
    </>
    );
}

export default AssignedLesson;
