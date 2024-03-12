import { RequestFunction } from "src/hooks/useRequest/types";
import { WordDto } from "src/types/learningMaterial";

export async function getWordsByIds(
  request: RequestFunction,
  ids: number[]
): Promise<WordDto[]> {
  const url = `/words?wordIds=${ids.join(",")}`;
  const response = await request(url, { method: "GET" });
  return await response.json();
}

export async function getWordsByLesson(
  request: RequestFunction,
  lessonId: number
): Promise<WordDto[]> {
  const response = await request(`/lessons/${lessonId}/words`, {
    method: "GET",
  });
  if (response.ok) {
    return await response.json();
  } else {
    console.error(
      `Failed to fetch words for lesson ${lessonId}, status: ${response.status}`
    );
    throw new Error("Failed to get words by lesson");
  }
}
