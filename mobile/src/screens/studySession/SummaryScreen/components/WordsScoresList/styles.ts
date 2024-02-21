import { MAIN_COLORS } from "src/styles/globalColors";
import { StyleSheet } from "react-native";

export default StyleSheet.create({
  itemContainer: {
    flex: 1,
    backgroundColor: MAIN_COLORS.BACKGROUND,
    justifyContent: "space-between",
    padding: 20,
    flexDirection: "row",
  },
  text: {
    color: MAIN_COLORS.TEXT,
  },
  wordName: {
    fontSize: 20,
    color: MAIN_COLORS.TEXT,
  },
  wordScore: {
    fontSize: 20,
    color: MAIN_COLORS.TEXT,
  },
});
