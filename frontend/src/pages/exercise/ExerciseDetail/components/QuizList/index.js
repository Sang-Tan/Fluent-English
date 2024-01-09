import useFetch from "src/hooks/useFetch";
import DataTable from "src/components/DataTable";
import { Button, Card, CardBody, CardTitle } from "react-bootstrap";
import { Link } from "react-router-dom";

function QuizList({ exerciseId }) {
  const { data: quizzes, isLoading } = useFetch(
    `/exercises/${exerciseId}/quizzes`,
    {
      initialData: [],
    }
  );

  const quizzesData = quizzes
    .sort((quiz1, quiz2) => {
      return quiz1.position - quiz2.position;
    })
    .map((quiz) => {
      return {
        id: quiz.id,
        question: quiz.question.content,
        details: (
          <Link to={`/quiz/${quiz.id}`}>
            <Button variant="primary">Details</Button>
          </Link>
        ),
      };
    });

  return (
    <section>
      <Card>
        <CardBody>
          <div className="d-flex justify-content-between align-items-center">
            <CardTitle>Quizzes</CardTitle>
            <Link to={`/quiz/create?exerciseId=${exerciseId}`}>
              <Button variant="primary">Create</Button>
            </Link>
          </div>
          {isLoading ? (
            <div>Loading...</div>
          ) : (
            <DataTable
              columns={[
                {
                  id: "question",
                  label: "Question",
                },
                {
                  id: "details",
                  label: "Details",
                  width: "100px",
                },
              ]}
              data={quizzesData}
            />
          )}
        </CardBody>
      </Card>
    </section>
  );
}

export default QuizList;
