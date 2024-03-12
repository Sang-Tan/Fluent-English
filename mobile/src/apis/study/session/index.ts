import { RequestFunction } from "src/hooks/useRequest/types";
import { SessionInitializationDto } from "src/types/study/session";
import {
  AnswerSubmissionDto,
  SessionSubmissionResultDto,
} from "src/types/study/session";

export async function startStudySession(
  request: RequestFunction,
  wordIds: number[]
): Promise<SessionInitializationDto> {
  if (wordIds.length === 0) {
    throw new Error("No words to study");
  }

  const url = "/study-sessions";
  const response = await request(url, {
    method: "POST",
    body: JSON.stringify({ wordIds }),
  });

  if (!response.ok) {
    throw new Error("Failed to start study session");
  }

  return await response.json();
}

export async function submitAnswer(
  request: RequestFunction,
  sessionId: string,
  submission: AnswerSubmissionDto | null
): Promise<SessionSubmissionResultDto> {
  const url = `/study-sessions/${sessionId}/answer`;
  const response = await request(url, {
    method: "POST",
    body: JSON.stringify(submission),
  });

  if (!response.ok) {
    throw new Error("Failed to submit answer");
  }

  return await response.json();
}
