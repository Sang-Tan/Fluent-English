import { Link } from "react-router-dom";
import { Button, Card, CardBody, CardTitle } from "react-bootstrap";
import DataTable from "src/components/DataTable";

import { useState, useEffect } from "react";
import useFetch from "src/hooks/useFetch";

function LessonList() {
  const { data: respLessons, loading } = useFetch("/lessons", {
    initialData: [],
  });

  const [lessons, setLessons] = useState([]);

  const columns = [
    {
      id: "name",
      label: "Name",
    },
    {
      id: "details",
      label: "Details",
      width: "100px",
    },
  ];

  lessons.forEach((lesson) => {
    lesson.details = (
      <Link to={`/lesson/${lesson.id}`}>
        <Button variant="primary">Details</Button>
      </Link>
    );
  });

  useEffect(() => {
    setLessons(respLessons || []);
  }, [respLessons]);

  return (
    <section>
      <Card>
        <CardBody>
          <div className="d-flex justify-content-between align-items-center">
            <CardTitle>Lessons</CardTitle>
            <Link to="/lesson/create">
              <Button variant="primary">Create</Button>
            </Link>
          </div>
          {loading ? (
            <div>Loading...</div>
          ) : (
            <DataTable columns={columns} data={lessons} />
          )}
        </CardBody>
      </Card>
    </section>
  );
}

export default LessonList;
