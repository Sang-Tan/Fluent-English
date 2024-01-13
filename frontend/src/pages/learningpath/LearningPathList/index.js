import { Link } from "react-router-dom";
import { Button, Card, CardBody, CardTitle, Spinner } from "react-bootstrap";
import useAsyncRequest from "src/hooks/useAsyncRequest";
import DataTable from "src/components/DataTable";

import { useState, useEffect } from "react";

function LearningPathList() {
  const [request] = useAsyncRequest();
  const [learningPathList, setLearningPathList] = useState([]);
  const [fetchingLearningPathList, setFetchingLearningPathList] = useState(true);
  const [learningPathListFetchErr, setLearningPathListFetchErr] = useState(null);

  useEffect(() => {
    (async () => {
      try {
        const resp = await request(`/learning-paths`);
        setLearningPathList(await resp.json());
      } catch (err) {
        setLearningPathListFetchErr(err.message);
      } finally {
        setFetchingLearningPathList(false);
      }
    })();
  }, [request]);

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

  learningPathList.forEach((learningPath) => {
    learningPath.details = (
      <Link to={`/learning-path/${learningPath.id}`}>
        <Button variant="primary">Details</Button>
      </Link>
    );
  });

  return (
    <>
      {fetchingLearningPathList ? (
        <Spinner animation="border" />
      ) : learningPathListFetchErr ? (
        <div>{learningPathListFetchErr}</div>
      ) : (
        <section>
        <Card>
          <CardBody>
            <div className="d-flex justify-content-between align-items-center">
              <CardTitle>learningPath</CardTitle>
              <Link to="/learning-path/create">
                <Button variant="primary">Create</Button>
              </Link>
            </div>
            {fetchingLearningPathList ? (
              <div>Loading...</div>
            ) : (
              <DataTable columns={columns} data={learningPathList} />
            )}
          </CardBody>
        </Card>
        </section>
      )}
    </>
  );
}

export default LearningPathList;
