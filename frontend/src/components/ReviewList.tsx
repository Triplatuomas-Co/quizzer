import ReviewCard from "./ReviewCard";

interface Review {
  review_id: number;
  nickname: string;
  rating: number;
  review: string;
  created_date: string;
  quiz_id: number;
}

interface ReviewListProps {
  reviews: Review[];
  onDelete?: (id: number) => void;
  currentUserNickname: string | null;
}

export default function ReviewList({
  reviews,
  onDelete,
  currentUserNickname,
}: ReviewListProps) {
  return (
    <>
      {reviews.map((r) => (
        <ReviewCard
          key={r.review_id}
          review={r}
          onDelete={onDelete}
          currentUserNickname={currentUserNickname}
        />
      ))}
    </>
  );
}
