import { Button } from "@mui/material";
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

export default function ReviewCard({ review, onDelete }: ReviewCardProps) {
  return (
    <div
      className="quiz-card"
      style={{
        width: "90%",
        maxWidth: "600px",
        margin: "1rem auto",
        display: "flex",
        justifyContent: "space-between",
        alignItems: "flex-start",
        marginBottom: "1rem",
        padding: "1rem",
        border: "1px solid #ccc",
        borderRadius: "8px",
      }}
    >
      <div style={{ flexGrow: 1 }}>
        <h2 style={{ marginBottom: "0.5rem" }}>{review.nickname}</h2>
        <p style={{ margin: 0, fontWeight: "bold" }}>
          Rating: {review.rating}/5
        </p>
        <p style={{ whiteSpace: "pre-wrap", lineHeight: 1.6 }}>
          {review.review}
        </p>
        <p style={{ fontSize: "0.8rem", color: "#666" }}>
          Written on: {review.created_date}
        </p>
      </div>

      <div style={{ display: "flex", flexDirection: "column", gap: "0.5rem" }}>
        <Button
          variant="outlined"
          color="primary"
          component={Link}
          to={`/editreview/${review.review_id}`}
        >
          Edit
        </Button>
        <Button
          variant="outlined"
          color="error"
          onClick={() => onDelete?.(review.review_id)}
        >
          Delete
        </Button>
      </div>
    </div>
  );
}
