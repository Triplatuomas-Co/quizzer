import { useState } from "react";
import {
  Box,
  Button,
  RadioGroup,
  TextField,
  Typography,
  FormControlLabel,
  Radio,
} from "@mui/material";

interface ReviewFormProps {
  quizId: number;
  onSubmit: (data: {
    quiz_id: number;
    nickname: string;
    rating: number;
    review: string;
  }) => Promise<void>;
}

export default function ReviewForm({ quizId, onSubmit }: ReviewFormProps) {
  const [nickname, setNickname] = useState("");
  const [rating, setRating] = useState("5");
  const [comment, setComment] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await onSubmit({
      quiz_id: quizId,
      nickname,
      rating: Number(rating),
      review: comment,
    });
    localStorage.setItem("nickname", nickname);
  };

  return (
    <Box sx={{ maxWidth: 600, mx: "auto", mt: 4, color: "text.primary" }}>
      <Typography variant="h5" gutterBottom>
        Add a review
      </Typography>
      <form onSubmit={handleSubmit}>
        <TextField
          label="Nickname"
          fullWidth
          required
          value={nickname}
          onChange={(e) => setNickname(e.target.value)}
          sx={{ mb: 2 }}
        />
        <Typography>Rating:</Typography>
        <RadioGroup
          row
          value={rating}
          onChange={(e) => setRating(e.target.value)}
          sx={{ mb: 2 }}
        >
          {[1, 2, 3, 4, 5].map((num) => (
            <FormControlLabel
              key={num}
              value={num.toString()}
              control={<Radio />}
              label={num}
            />
          ))}
        </RadioGroup>
        <TextField
          label="Review"
          fullWidth
          multiline
          minRows={3}
          value={comment}
          onChange={(e) => setComment(e.target.value)}
          inputProps={{ 
            maxLength: 10000,
          }}
          helperText={`${comment.length} / 10000`}
          sx={{ 
            mb: 2,
            '& textarea': {
              resize: 'vertical',
              overflow: 'auto',
            }
          }}
        />
        <Button variant="contained" type="submit">
          Submit
        </Button>
      </form>
    </Box>
  );
}
