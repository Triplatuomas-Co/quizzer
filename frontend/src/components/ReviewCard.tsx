import { Box, Typography, Divider, Button, Stack } from "@mui/material";
import { Link } from "react-router-dom";

interface Review {
  review_id: number;
  nickname: string;
  rating: number;
  review: string;
  created_date: string;
  quiz_id: number;
}

interface ReviewCardProps {
  review: Review;
  onDelete?: (id: number) => void;
  currentUserNickname: string | null;
}

export default function ReviewCard({
  review,
  onDelete,
  currentUserNickname,
}: ReviewCardProps) {
  const isOwner = review.nickname === currentUserNickname;

  return (
    <Box sx={{ mb: 3 }}>
      <Typography fontWeight="bold" color="text.primary">
        {review.nickname}
      </Typography>
      <Typography color="text.primary">Rating: {review.rating}/5</Typography>
      <Typography color="text.primary">{review.review}</Typography>
      <Typography variant="caption" color="text.secondary">
        Written on: {review.created_date}
      </Typography>

      {isOwner && (
        <Stack direction="row" spacing={1} sx={{ mt: 1 }}>
          <Button
            variant="outlined"
            color="error"
            size="small"
            onClick={() => onDelete?.(review.review_id)}
          >
            Delete
          </Button>
          <Button
            variant="outlined"
            color="primary"
            size="small"
            component={Link}
            to={`/editreview/${review.review_id}`}
          >
            Edit
          </Button>
        </Stack>
      )}
      <Divider sx={{ mt: 2 }} />
    </Box>
  );
}
