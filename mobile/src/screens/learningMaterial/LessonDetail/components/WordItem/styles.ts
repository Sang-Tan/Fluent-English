import { MAIN_COLORS } from "src/styles/globalColors";
import { StyleSheet } from "react-native";

const styles = StyleSheet.create({
  container: {
    flexDirection: "row",
    justifyContent: "space-between",
    borderRadius: 10,
    borderColor: "#261789",
    borderWidth: 1,
    padding: 10,
    marginBottom: 10,
  },
  wordContainer: {
    flexDirection: "column",
  },
  text: {
    color: MAIN_COLORS.TEXT,
  },
});

export default styles;
