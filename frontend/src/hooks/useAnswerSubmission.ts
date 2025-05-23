import { useState } from "react";
import { useNotification } from "../components/NotificationSystem";

interface UseAnswerSubmissionProps {
  quizId: number;
}

export function useAnswerSubmission({}: UseAnswerSubmissionProps) {
  const [selectedAnswers, setSelectedAnswers] = useState<Record<number, number>>({});
  const [answeredQuestions, setAnsweredQuestions] = useState<Record<number, boolean>>({});
  const { showNotification } = useNotification();
  const baseUrl = import.meta.env.VITE_API_BASE_URL as string;

  const handleAnswerSubmit = async (questionId: number) => {
    const selectedOptionId = selectedAnswers[questionId];
    if (!selectedOptionId) {
      showNotification("Please select an answer", "error");
      return;
    }

    try {
      const response = await fetch(`${baseUrl}/update-answered-times`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          "Accept": "application/json",
        },
        body: JSON.stringify(selectedOptionId), 
      });

      if (!response.ok) {
        const errorText = await response.text();
        console.error("Error response:", errorText);
        throw new Error("Failed to submit answer");
      }

      const result = await response.json(); 
      setAnsweredQuestions((prev) => ({ ...prev, [questionId]: true }));

      if (result.correct) {
        showNotification("Correct answer!", "success");
      } else {
        showNotification("Incorrect answer. Try again!", "error");
      }
    } catch (error) {
      console.error("Error submitting answer:", error);
      showNotification("Error submitting answer", "error");
    }
  };

  const handleAnswerSelect = (questionId: number, optionId: number) => {
    setSelectedAnswers((prev) => ({
      ...prev,
      [questionId]: optionId,
    }));
  };

  return {
    selectedAnswers,
    answeredQuestions,
    handleAnswerSubmit,
    handleAnswerSelect,
  };
}
