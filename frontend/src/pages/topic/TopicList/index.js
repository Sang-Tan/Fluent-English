import { Link } from "react-router-dom";
import { Button, Card, CardBody, CardTitle } from "react-bootstrap";
import DataTable from "src/components/DataTable";

import { useState, useEffect } from "react";
import useFetch from "src/hooks/useFetch";

function TopicList() {
  const { data: respTopics, loading } = useFetch("/topics", {
    initialData: [],
  });

  const [topics, setTopics] = useState([]);

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

  topics.forEach((topic) => {
    topic.details = (
      <Link to={`/topic/${topic.id}`}>
        <Button variant="primary">Details</Button>
      </Link>
    );
  });

  useEffect(() => {
    setTopics(respTopics || []);
  }, [respTopics]);

  return (
    <section>
      <Card>
        <CardBody>
          <div className="d-flex justify-content-between align-items-center">
            <CardTitle>Topics</CardTitle>
            <Link to="/topic/create">
              <Button variant="primary">Create</Button>
            </Link>
          </div>
          {loading ? (
            <div>Loading...</div>
          ) : (
            <DataTable columns={columns} data={topics} />
          )}
        </CardBody>
      </Card>
    </section>
  );
}

export default TopicList;
