import { ENDPOINT_EXERCISE as EP_EXERCISES } from "src/apis/endpoints";
import { PATH_EXERCISE } from "src/routes/paths";
import parseUrl from "src/helpers/pasteUrl";

import { useState, useEffect } from "react";
import useFetch from "src/hooks/useFetch";

import { Link } from "react-router-dom";
import { Button, Card, CardBody, CardTitle, Spinner } from "react-bootstrap";
import DataTable from "src/components/DataTable";

function ExerciseList({ lessonId }) {
  const { data: respExercises, loading } = useFetch(
    parseUrl(EP_EXERCISES.LIST, { lessonId }),
    {
      initialData: [],
    }
  );

  const [exercises, setExercises] = useState([]);

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

  exercises.forEach((exercise) => {
    exercise.details = (
      <Link to={parseUrl(PATH_EXERCISE.DETAIL, { exerciseId: exercise.id })}>
        <Button variant="primary">Details</Button>
      </Link>
    );
  });

  useEffect(() => {
    setExercises(respExercises || []);
  }, [respExercises]);

  return (
    <Card>
      <CardBody>
        <div className="d-flex justify-content-between align-items-center">
          <CardTitle>Exercises</CardTitle>
          <Link to={parseUrl(PATH_EXERCISE.CREATE, { lessonId })}>
            <Button variant="primary">Create</Button>
          </Link>
        </div>
        {loading ? (
          <div>
            <Spinner />
          </div>
        ) : (
          <DataTable columns={columns} data={exercises} />
        )}
      </CardBody>
    </Card>
  );
}

export default ExerciseList;
