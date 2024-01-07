import { useState, useRef, useEffect } from "react";
import classNames from "classnames/bind";
import styles from "./AudioPlayer.module.scss";
import {
  PlayFill,
  PauseFill,
  VolumeUpFill,
  VolumeMute,
} from "react-bootstrap-icons";

const cx = classNames.bind(styles);

function AudioPlayer({ audioUrl }) {
  const [audioLoaded, setAudioLoaded] = useState(false);
  const [isPlaying, setPlaying] = useState(false);
  const [volume, setVolume] = useState(0.5);
  const [muted, setMuted] = useState(false);
  const [duration, setDuration] = useState(0);
  const [currentTimePos, setCurrentTimePos] = useState(0);
  const audioRef = useRef();

  useEffect(() => {
    audioRef.current.src = audioUrl;
    setPlaying(false);
    setAudioLoaded(false);
    setDuration(0);
    setCurrentTimePos(0);
    console.log("audio url changed", audioUrl);
  }, [audioUrl]);

  const handleVolumeChange = (e) => {
    setVolume(e.target.value);
    audioRef.current.volume = e.target.value / 100;
    if (muted) {
      setMuted(false);
    }
  };

  const handleSeekChange = (e) => {
    const pos = e.target.value;
    setCurrentTimePos(pos);
    audioRef.current.currentTime = (pos / 100) * duration;
  };

  const handleMute = () => {
    setMuted(!muted);
  };

  const handleUnmute = () => {
    if (volume === "0") {
      setVolume(1);
    }
    setMuted(false);
  };

  const handleLoadedMetadata = () => {
    setDuration(audioRef.current.duration);
    setAudioLoaded(true);
  };

  const handleTimeUpdate = (e) => {
    console.log("time update", audioRef.current.currentTime);
    const pos = (audioRef.current.currentTime / (duration || 1)) * 100;
    setCurrentTimePos(Math.round(pos));
  };

  const handlePlayPause = () => {
    if (!audioLoaded) {
      return;
    }

    if (isPlaying) {
      pauseAudio();
    } else {
      playAudio();
    }
  };

  const playAudio = () => {
    audioRef.current.play();
    setPlaying(true);
  };

  const pauseAudio = () => {
    audioRef.current.pause();
    setPlaying(false);
  };

  console.log("duration", duration);
  console.log("current time pos", currentTimePos);

  return (
    <div className={cx("controls")}>
      <div className={cx("play-pause")}>
        <div
          className={cx("play-pause-btn", { disabled: !audioLoaded })}
          onClick={handlePlayPause}
        >
          {isPlaying ? <PauseFill /> : <PlayFill />}
        </div>
      </div>
      <div className={cx("seekbar")}>
        <audio
          ref={audioRef}
          muted={muted}
          onLoadedMetadata={handleLoadedMetadata}
          onTimeUpdate={handleTimeUpdate}
          onEnded={() => pauseAudio()}
          hidden
        ></audio>
        <input
          type="range"
          className={cx("slidebar")}
          min="0"
          max="100"
          value={currentTimePos}
          onChange={handleSeekChange}
          disabled={!audioLoaded}
        ></input>
      </div>
      <div className={cx("volume")}>
        <div className={cx("volume-btn")}>
          {muted || volume === "0" ? (
            <VolumeMute onClick={handleUnmute} />
          ) : (
            <VolumeUpFill onClick={handleMute} />
          )}
        </div>
        <div className={cx("volume-bar", { muted: muted })}>
          <input
            type="range"
            className={cx("slidebar")}
            value={volume}
            onChange={handleVolumeChange}
            min="0"
            max="100"
          ></input>
        </div>
      </div>
    </div>
  );
}

export default AudioPlayer;
