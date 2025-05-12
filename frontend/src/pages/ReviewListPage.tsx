import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { Box, Typography, Divider } from "@mui/material";
import ReviewList from "../components/ReviewList";

interface Review {
  review_id: number;
  nickname: string;
  rating: number;
  review: string;
  created_date: string;
  quiz_id: number;
}

export default function ReviewListPage() {
  const { id } = useParams<{ id: string }>();
  const [reviews, setReviews] = useState<Review[]>([]);
  const [loading, setLoading] = useState(true);
  const localNickname = localStorage.getItem("nickname");

  useEffect(() => {
    const fetchReviews = async () => {
      const res = await fetch(`/api/quiz/${id}/reviews`);
      if (res.ok) {
        const data = await res.json();
        setReviews(data);
      }
      setLoading(false);
    };
    fetchReviews();
  }, [id]);

  const handleDelete = async (reviewId: number) => {
    const res = await fetch(`/api/quiz/review/${reviewId}`, {
      method: "DELETE",
    });
    if (res.ok) {
      setReviews(reviews.filter((r) => r.review_id !== reviewId));
    }
  };

  const average =
    reviews.length > 0
      ? (
          reviews.reduce((sum, r) => sum + r.rating, 0) / reviews.length
        ).toFixed(1)
      : "N/A";

  if (loading) return <Typography>Loading reviews...</Typography>;

  return (
    <Box sx={{ maxWidth: 800, mx: "auto", mt: 4, color: "text.primary" }}>
      <Typography variant="h5" gutterBottom>
        Reviews of Quiz #{id}
      </Typography>
      <Typography variant="subtitle1" gutterBottom>
        Average rating: {average} based on {reviews.length} review(s)
      </Typography>
      <Link to={`/quiz/${id}/review`}>Write your review</Link>
      <Divider sx={{ my: 2 }} />
      <ReviewList
        reviews={reviews}
        onDelete={handleDelete}
        currentUserNickname={localNickname}
      />
    </Box>
  );
}
