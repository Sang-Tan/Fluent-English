import {
  forwardRef,
  useState,
  useEffect,
  useImperativeHandle,
  useContext,
} from "react";
import { QuizUploadContext } from "../../../QuizUploadContext";
import useAsyncRequest from "src/hooks/useAsyncRequest";
import AudioFileInput from "src/components/fileinput/AudioFileInput";
/**
 * @typedef {import("../../typeDefs").AudioAnswerData} AudioAnswerData
 */
/**
 * @typedef {Object} AudioInputChoiceProps
 * @property {AudioAnswerData} data
 */
/**
 * @typedef {Object} AudioInputChoiceRef
 * @property {() => Promise<AudioAnswerData>} validateAndGetData
 */
/**
 * @type {React.ForwardRefExoticComponent<React.PropsWithoutRef<AudioInputChoiceProps> & React.RefAttributes<AudioInputChoiceRef>>}
 */
const AudioInputChoice = forwardRef(({ data }, ref) => {
  const [pendingUrl, setPendingUrl] = useState(null);
  const [request] = useAsyncRequest();
  const { uploadFolder } = useContext(QuizUploadContext);

  useEffect(() => {
    setPendingUrl(null);
  }, [data]);

  useImperativeHandle(ref, () => ({
    /**
     * @returns {Promise<AudioAnswerData>}
     */
    async validateAndGetData() {
      if (!pendingUrl) {
        if (data?.url) {
          return data;
        }
        throw Error("Audio is required");
      }

      const formData = new FormData();
      formData.append(
        "file",
        await fetch(pendingUrl).then((resp) => resp.blob())
      );
      formData.append("folder", uploadFolder);

      const resp = await request(`/upload`, {
        method: "POST",
        body: formData,
      });

      const respData = await resp.json();

      return {
        url: respData.url,
        id: respData.id,
      };
    },
  }));

  return (
    <AudioFileInput
      initialAudioUrl={data?.url}
      onUploadUrlChange={(url) => setPendingUrl(url)}
    />
  );
});

export default AudioInputChoice;
