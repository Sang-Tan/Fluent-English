import { FileResource } from "src/types/fileResource/types";

import AudioAttachment from "./AudioAttachment";
import { View, Text } from "react-native";
import styles from "./styles";

interface AttachmentProps {
  attachment: FileResource;
}

function Attachment({ attachment }: AttachmentProps) {
  return (
    <View style={styles.container}>
      {attachment.mediaType === "audio" ? (
        <AudioAttachment url={attachment.url} />
      ) : (
        <Text>Attachment not supported</Text>
      )}
    </View>
  );
}

export default Attachment;
