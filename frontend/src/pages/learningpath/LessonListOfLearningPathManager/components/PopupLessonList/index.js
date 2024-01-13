import DataTable from "src/components/DataTable";
import { Button , Spinner} from "react-bootstrap";
import { useCallback, useState, useEffect } from "react";
import  useAsyncRequest from "src/hooks/useAsyncRequest";
import { Link } from "react-router-dom";

function PopupLessonList({learningPathId, addedLessonList, setAddedLessonList, fetchingAddedLessons, addedLessonsFetchErr}) {
  const [allLessonList, setAllLessonList] = useState([])
  const [fetchingAllLessons, setFetchingAllLessons] = useState(true);
  const [allLessonsFetchErr, setAllLessonsFetchErr] = useState(null);
  const [request] = useAsyncRequest();
  useEffect(() => {
    (async () => {
      try {
        const resp = await request(`/lessons`);
        setAllLessonList(await resp.json());
      } catch (err) {
        setAllLessonsFetchErr(err.message);
      } finally {
        setFetchingAllLessons(false);
      }
    })();
  }, [request]);

  const addLesson = useCallback( async (addedLesson, addedLessonList, setAddedLessonList ) => {
    const newLessonList = [...addedLessonList, addedLesson];
    setAddedLessonList(newLessonList);
  },[]);

  allLessonList.forEach((lesson) => {
    lesson.details = (
      <Link to={`/lesson/${lesson.id}`}>
        <Button variant="primary">Details</Button>
      </Link>
    );
  
    const isLessonIdIncludedInAllLessonListYet = addedLessonList.some((addedLesson) => addedLesson.id === lesson.id);
  
    if (!isLessonIdIncludedInAllLessonListYet) {
      lesson.add = (
        <Button variant="primary" onClick={() => addLesson(lesson, addedLessonList, setAddedLessonList)}>
          Add
        </Button>
      );
    } else {
      lesson.add = (
        <Button variant="primary" disabled>
          Added
        </Button>
      );
    }
  });
  


  return (
  <>
      {fetchingAddedLessons || fetchingAllLessons ? (
        <Spinner animation="border" />
      ) : addedLessonsFetchErr || allLessonsFetchErr ? (
        <div>{addedLessonsFetchErr || allLessonsFetchErr}</div>
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
              id: "add",
              label: "Add",
              width: "100px",
            }
          ]}
          data={allLessonList}
        />
      )}
    </>
    );
}

export default PopupLessonList;
