import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import {
  Box,
  Button,
  TextField,
  Typography,
  RadioGroup,
  FormControlLabel,
  Radio,
} from "@mui/material";

interface Review {
  review_id: number;
  nickname: string;
  rating: number;
  review: string;
  created_date: string;
  quiz_id: number;
}

export default function EditReviewPage() {
  const { id } = useParams<{ id: string }>(); // review_id
  const navigate = useNavigate();
  const [reviewData, setReviewData] = useState<Review | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchReview = async () => {
      const res = await fetch(`/api/quiz/review/${id}`);
      if (res.ok) {
        const data = await res.json();
        setReviewData(data);
      }
      setLoading(false);
    };
    fetchReview();
  }, [id]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!reviewData) return;

    const res = await fetch(`/api/quiz/review/${reviewData.review_id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(reviewData),
    });

    if (res.ok) {
      navigate(`/quiz/${reviewData.quiz_id}/reviews`);
    }
  };

  if (loading) return <Typography>Loading...</Typography>;
  if (!reviewData) return <Typography>Review not found</Typography>;

  return (
    <Box sx={{ maxWidth: 600, mx: "auto", mt: 4 }}>
      <Typography variant="h5" gutterBottom>
        Edit your review
      </Typography>
      <form onSubmit={handleSubmit}>
        <TextField
          label="Nickname"
          fullWidth
          required
          value={reviewData.nickname}
          onChange={(e) =>
            setReviewData({ ...reviewData, nickname: e.target.value })
          }
          sx={{ mb: 2 }}
        />
        <Typography>Rating:</Typography>
        <RadioGroup
          row
          value={reviewData.rating.toString()}
          onChange={(e) =>
            setReviewData({ ...reviewData, rating: Number(e.target.value) })
          }
          sx={{ mb: 2 }}
        >
          {[1, 2, 3, 4, 5].map((num) => (
            <FormControlLabel
              key={num}
              value={num.toString()}
              control={<Radio />}
              label={num}
              sx={{
                '& .MuiFormControlLabel-label': {
                  color: 'text.primary',
                  fontSize: '1rem',
                  display: 'inline',
                }
              }}
            />
          ))}
        </RadioGroup>
        <TextField
          label="Review"
          fullWidth
          multiline
          minRows={3}
          value={reviewData.review}
          onChange={e =>
            setReviewData({ ...reviewData, review: e.target.value })
          }
          slotProps={{
            htmlInput: {
              maxLength: 10000
            }
          }}
          helperText={`${reviewData.review.length} / 10000`}
          sx={{
            mb: 2,
            '& textarea': {
              resize: 'vertical',
              overflow: 'auto'
            }
          }}
        />
        <Button variant="contained" type="submit">
          Save changes
        </Button>
      </form>
    </Box>
  );
}
