import { useState, useEffect, useCallback, useMemo } from 'react'; // Added useMemo
import { Review } from '../types'; 

export function useQuizReviews(quizId: string | undefined) {
  const [reviews, setReviews] = useState<Review[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);

  useEffect(() => {
    if (!quizId) {
      setLoading(false);
      setReviews([]);
      return;
    }

    const fetchReviews = async () => {
      setLoading(true);
      setError(null);
      try {
        const baseUrl = import.meta.env.VITE_API_BASE_URL as string;
        const res = await fetch(`${baseUrl}/${quizId}/reviews`);
        if (!res.ok) {
          throw new Error(`Failed to fetch reviews: ${res.status}`);
        }
        const data = await res.json();
        setReviews(data);
      } catch (err) {
        setError(err instanceof Error ? err : new Error('An unknown error occurred'));
        setReviews([]);
      } finally {
        setLoading(false);
      }
    };

    fetchReviews();
  }, [quizId]);

  const deleteReview = useCallback(async (reviewId: number) => {
    const originalReviews = [...reviews];
    setReviews((prevReviews) => prevReviews.filter((r) => r.review_id !== reviewId));

    try {
      const res = await fetch(`https://quizzer-git-quizzer-postgres.2.rahtiapp.fi/api/quiz/review/${reviewId}`, {
        method: "DELETE",
      });
      if (!res.ok) {
        setReviews(originalReviews);
        console.error("Failed to delete review from API:", res.status);
        throw new Error(`Failed to delete review: ${res.status}`);
      }
    } catch (err) {
      setReviews(originalReviews);
      console.error("Error deleting review:", err);
      throw err;
    }
  }, [reviews]); 

  const reviewCount = reviews.length;

  // Calculate average rating using useMemo to avoid recalculation on every render
  // unless 'reviews' changes.
  const averageRating = useMemo(() => {
    if (reviews.length === 0) {
      return "N/A";
    }
    const totalRating = reviews.reduce((sum, r) => sum + r.rating, 0);
    return (totalRating / reviews.length).toFixed(1);
  }, [reviews]);

  return { reviews, loading, error, setReviews, deleteReview, reviewCount, averageRating }; // Add averageRating
}
