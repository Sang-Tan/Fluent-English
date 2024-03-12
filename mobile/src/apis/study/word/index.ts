import { RequestFunction } from "src/hooks/useRequest/types";
import { WordStudyStatusDto } from "src/types/study/word";

export async function getWordsStudyStatus(
  request: RequestFunction,
  wordIds: number[]
): Promise<WordStudyStatusDto[]> {
  const url = `/words-study-status?wordIds=${wordIds.join(",")}`;
  const response = await request(url, { method: "GET" });
  return await response.json();
}