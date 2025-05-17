import { Typography, Divider, Box, Button } from "@mui/material";
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
      <Button
        variant="contained"
        color="primary"
        size="small"
        component={Link}
        to={`/quiz/${quizId}/review`}
        sx={{ mt: 1 }}
      >
        Write your review
      </Button>
      <Divider sx={{ my: 2 }} />
    </Box>
  );
}
