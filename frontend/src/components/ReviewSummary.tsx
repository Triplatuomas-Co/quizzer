import { Typography, Divider, Box } from "@mui/material";
import { Link } from "react-router-dom";

interface ReviewSummaryProps {
  quizId: number;
  reviewCount: number;
  averageRating: string;
}

export default function ReviewSummary({
  quizId,
  reviewCount,
  averageRating,
}: ReviewSummaryProps) {
  return (
    <Box sx={{ color: "text.primary" }}>
      <Typography variant="h5" gutterBottom>
        Reviews of Quiz #{quizId}
      </Typography>
      <Typography variant="subtitle1" gutterBottom>
        Average rating: {averageRating} based on {reviewCount} review(s)
      </Typography>
      <Link to={`/quiz/${quizId}/review`}>Write your review</Link>
      <Divider sx={{ my: 2 }} />
    </Box>
  );
}
