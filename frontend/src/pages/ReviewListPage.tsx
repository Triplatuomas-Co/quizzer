import { useParams, Link } from "react-router-dom";
import { Box, Typography, Divider, Alert } from "@mui/material";
import ReviewList from "../components/ReviewList";
import { useQuizReviews } from "../hooks/useQuizReviews";

export default function ReviewListPage() {
  const { id: quizId } = useParams<{ id: string }>();
  const { reviews, loading, error, deleteReview, reviewCount, averageRating } = useQuizReviews(quizId); 
  const localNickname = localStorage.getItem("nickname");

  const handleDeleteReview = async (reviewId: number) => {
    try {
      await deleteReview(reviewId);
    } catch (err) {
      console.error("Caught error during delete operation in component:", err);
    }
  };

  if (loading) return <Typography>Loading reviews...</Typography>;
  if (error)
    return <Alert severity="error">Error loading reviews: {error.message}</Alert>;

  return (
    <Box sx={{ maxWidth: 800, mx: "auto", mt: 4, color: "text.primary" }}>
      <Typography variant="h5" gutterBottom>
        Reviews of Quiz #{quizId}
      </Typography>
      <Typography variant="subtitle1" gutterBottom>
        Average rating: {averageRating} based on {reviewCount} review(s)
      </Typography>
      <Link to={`/quiz/${quizId}/review`}>Write your review</Link>
      <Divider sx={{ my: 2 }} />
      <ReviewList
        reviews={reviews}
        onDelete={handleDeleteReview}
        currentUserNickname={localNickname}
      />
    </Box>
  );
}
