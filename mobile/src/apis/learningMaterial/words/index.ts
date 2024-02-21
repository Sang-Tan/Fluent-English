import { RequestFunction } from "src/hooks/useRequest/types";
import { WordDto } from "src/types/learningMaterial";

export async function getWordsByIds(
  request: RequestFunction,
  ids: number[]
): Promise<WordDto[]> {
  const url = `/words?ids=${ids.join(",")}`;
  const response = await request(url, { method: "GET" });
  return await response.json();
}
