import { useParams } from "react-router-dom";

import QuizList from "./components/QuizList";
import DetailForm from "./components/DetailForm";
import { Container, Row, Col } from "react-bootstrap";

function ExerciseDetail() {
  const { exerciseId } = useParams();

  return (
    <Container fluid>
      <Row>
        <Col>
          <DetailForm exerciseId={exerciseId} />
          <QuizList exerciseId={exerciseId} />
        </Col>
      </Row>
    </Container>
  );
}

export default ExerciseDetail;
