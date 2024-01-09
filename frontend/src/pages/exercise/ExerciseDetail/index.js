import { useParams } from "react-router-dom";
import QuizList from "./components/QuizList";

function ExerciseDetail() {
  const { exerciseId } = useParams();
  return <QuizList exerciseId={exerciseId} />;
}

export default ExerciseDetail;
