import { useEffect, useState, useMemo } from "react";

import { Pressable } from "react-native";
import { FontAwesomeIcon } from "@fortawesome/react-native-fontawesome";
import { faPlayCircle } from "@fortawesome/free-regular-svg-icons";
import { COLORS } from "../styles";
import { Audio } from "expo-av";

interface Props {
  url: string;
}

function AudioAttachment({ url }: Props) {
  const [sound, setSound] = useState<Audio.Sound | null>(null);

  useEffect(() => {
    return () => {
      if (sound) {
        sound.unloadAsync();
      }
    };
  }, []);

  const playAudio = async () => {
    if (sound) {
      await sound.unloadAsync();
    }
    const { sound: loadedSound } = await Audio.Sound.createAsync({ uri: url });
    setSound(loadedSound);

    await loadedSound.playAsync();
  };

  return (
    <Pressable onPress={playAudio}>
      <FontAwesomeIcon
        icon={faPlayCircle}
        size={50}
        color={COLORS.AUDIO_ICON}
      />
    </Pressable>
  );
}

export default AudioAttachment;
