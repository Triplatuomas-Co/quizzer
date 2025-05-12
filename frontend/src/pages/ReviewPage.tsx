import { useNavigate, useParams } from "react-router-dom";
import useSubmitReview from "../hooks/useSubmitReview";
import ReviewForm from "../components/ReviewForm";

export default function ReviewPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const submitReview = useSubmitReview();

  const handleReviewSubmit = async (data: {
    quiz_id: number;
    nickname: string;
    rating: number;
    review: string;
  }) => {
    await submitReview(data);
    navigate(`/quiz/${id}/reviews`);
  };

  return id ? (
    <ReviewForm quizId={Number(id)} onSubmit={handleReviewSubmit} />
  ) : null;
}
