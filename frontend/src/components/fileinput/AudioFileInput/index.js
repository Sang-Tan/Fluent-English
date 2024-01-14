import { useState, useRef, useEffect } from "react";
import { toast } from "react-toastify";
import { PencilSquare, ArrowClockwise } from "react-bootstrap-icons";
import AudioPlayer from "src/components/AudioPlayer";
import classNames from "classnames/bind";
import styles from "./AudioFileInput.module.scss";

const cx = classNames.bind(styles);

function AudioFileInput({
  initialAudioUrl = null,
  onUploadUrlChange = (url) => {},
}) {
  const [audioUrl, setAudioUrl] = useState(initialAudioUrl);
  const fileInputRef = useRef(null);

  useEffect(() => {
    setAudioUrl(initialAudioUrl);
  }, [initialAudioUrl]);

  const changeAudioUrl = (url) => {
    if (audioUrl && audioUrl.startsWith("blob:")) {
      URL.revokeObjectURL(audioUrl);
    }
    setAudioUrl(url === null ? initialAudioUrl : url);
    onUploadUrlChange(url);
  };

  const handleFileChange = (event) => {
    if (event.target.files.length === 0) {
      return;
    }
    const file = event.target.files[0];
    if (file && file.type.startsWith("audio/")) {
      changeAudioUrl(URL.createObjectURL(file));
    } else {
      toast.error("Please select an audio file.");
    }
  };

  const handleReset = () => {
    changeAudioUrl(null);
    fileInputRef.current.value = null;
  };

  return (
    <div className={cx("container")}>
      <div className={cx("options")}>
        <input
          type="file"
          accept="audio/*"
          onChange={handleFileChange}
          ref={fileInputRef}
          hidden
        />
        <button
          className={cx("button", "button-upload")}
          onClick={() => fileInputRef.current.click()}
        >
          <PencilSquare />
        </button>
        <button className={cx("button", "button-reset")}>
          <ArrowClockwise onClick={handleReset} />
        </button>
      </div>
      <AudioPlayer audioUrl={audioUrl} />
    </div>
  );
}

export default AudioFileInput;
