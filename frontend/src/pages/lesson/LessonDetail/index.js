import { useParams } from "react-router-dom";
import { Row, Col } from "react-bootstrap";
import ExerciseList from "./ExerciseList";
import DetailForm from "./DetailForm";

function LessonDetail() {
  const { lessonId } = useParams();

  return (
    <>
      <section>
        <Row>
          <Col>
            <DetailForm lessonId={lessonId} />
            <ExerciseList lessonId={lessonId} />
          </Col>
        </Row>
      </section>
    </>
  );
}

export default LessonDetail;
