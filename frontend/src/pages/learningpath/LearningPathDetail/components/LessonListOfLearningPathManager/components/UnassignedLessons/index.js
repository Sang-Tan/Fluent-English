import DataTable from "src/components/DataTable";
import {FormGroup, Button, Spinner } from "react-bootstrap";
import { useCallback, useState, useEffect } from "react";
import useAsyncRequest from "src/hooks/useAsyncRequest";
import { PaginationControl } from "react-bootstrap-pagination-control";
import { Link } from "react-router-dom";
import "./ModalStyles.scss";

function UnassignedLesson({
  learningPathId,
  fetchingAssignedLessons,
  assignedLessonsFetchErr,
}) {
  const [searchKey, setSearchKey] = useState("");
  const [page, setPage] = useState(1);
  const [lessonPage, setLessonPage] = useState([]);
  const [fetchingLessonPage, setFetchingLessonPage] = useState(true);
  const [lessonPageFetchErr, setLessonPageFetchErr] = useState(null);

  const [addingLesson, setAddingLesson] = useState(false);
  const [addingLessonErr, setAddingLessonErr] = useState([]);
  const [request] = useAsyncRequest();
  useEffect(() => {
    (async () => {
      try {
        const resp = await request(`/learning-paths/${learningPathId}/unassigned-lessons?page=${page}&q=${searchKey}`);
        setLessonPage(await resp.json());
      } catch (err) {
        setLessonPageFetchErr(err.message);
      } finally {
        setFetchingLessonPage(false);
      }
    })();
  }, [request, page, searchKey, learningPathId]);

  const refreshLessonPage = async () => {
    setFetchingLessonPage(true);
    try {
      const resp = await request(`/learning-paths/${learningPathId}/unassigned-lessons?page=${page}&q=${searchKey}`);
      setLessonPage(await resp.json());
    } catch (err) {
      setLessonPageFetchErr(err.message);
    } finally {
      setFetchingLessonPage(false);
    }
  }

  const addLesson = useCallback(
    async (addLesson) => {
      setAddingLesson(true);
      try {
        const resp = await request(
          `/learning-paths/${learningPathId}/lessons`,
          {
            method: "POST",
            body: JSON.stringify({
              lessonId: addLesson.id,
            }),
            headers: {
              "Content-Type": "application/json",
            },
          }
        );
        if (resp.status !== 201) {
          throw new Error(await resp.message);
        }
        setAddingLessonErr((err) =>
          err.filter((err) => err.id !== addLesson.id)
        );
        refreshLessonPage();
      } catch (err) {
        setAddingLessonErr((preErr) => [
          ...preErr,
          { id: addLesson.id, message: err.message },
        ]);
      } finally {
        setAddingLesson(false);
      }
    },
    [learningPathId, request, refreshLessonPage]
  );
  if (lessonPage.data){

    lessonPage.data.forEach((lesson) => {
      lesson.details = (
        <Link to={`/lesson/${lesson.id}`}>
          <Button variant="primary">Details</Button>
        </Link>
      );
      if (addingLesson) {
        lesson.add = (<Spinner animation="border" />);
      } else {
        const hasError =
          addingLessonErr &&
          addingLessonErr.some((err) => err.id === lesson.id);

        if (hasError) {
          lesson.add = (
            <>
              <div className="error-message">
                {addingLessonErr.find((err) => err.id === lesson.id).message}
              </div>
              <Button
                variant="primary"
                onClick={() =>
                  addLesson(lesson)
                }
              >
                Add
              </Button>
            </>
          );
        } else {
          lesson.add = (
            <Button
              variant="primary"
              onClick={() =>
                addLesson(lesson)
              }
            >
              Add
            </Button>
          );
        }
      }
    });
  }

  return (
    <>
      {fetchingAssignedLessons || fetchingLessonPage ? (
        <Spinner animation="border" />
      ) : assignedLessonsFetchErr || lessonPageFetchErr ? (
        <div>{assignedLessonsFetchErr || lessonPageFetchErr}</div>
      ) : (
        <>
          <FormGroup>
            <input
              type="text"
              className="form-control mb-3 w-50"
              placeholder="Search"
              onChange={(e) => setSearchKey(e.target.value)}
            />
          </FormGroup>
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
              },
            ]}
            data={lessonPage.data}
          />
          <PaginationControl
            page={page}
            total={lessonPage.pagination?.totalItems}
            limit={lessonPage.pagination?.itemsPerPage}
            changePage={(page) => {
              setPage(page);
            }}
            ellipsis={1}
          />
        </>
      )}
    </>
  );
}

export default UnassignedLesson;
