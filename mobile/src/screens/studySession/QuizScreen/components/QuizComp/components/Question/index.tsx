import { QuestionDto } from "src/types/study/session";
import { View, Text } from "react-native";
import Attachment from "./components/Attachment";
import styles from "./styles";

function Question({ data }: { data: QuestionDto }) {
  return (
    <View style={styles.container}>
      {data.attachment && <Attachment attachment={data.attachment} />}
      <Text style={styles.content}>{data.content}</Text>
    </View>
  );
}

export default Question;
