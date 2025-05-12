interface ReviewData {
  quiz_id: number;
  nickname: string;
  rating: number;
  review: string;
}

export default function useSubmitReview() {
  return async (review: ReviewData) => {
    const response = await fetch("/api/quiz/review", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(review),
    });

    if (!response.ok) {
      throw new Error("Failed to submit review");
    }
  };
}
