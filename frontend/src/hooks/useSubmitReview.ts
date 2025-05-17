interface ReviewData {
  quiz_id: number;
  nickname: string;
  rating: number;
  review: string;
}

export default function useSubmitReview() {
  return async (review: ReviewData) => {
    const baseUrl = import.meta.env.VITE_API_BASE_URL as string;
    const response = await fetch(`${baseUrl}/review`, {
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
