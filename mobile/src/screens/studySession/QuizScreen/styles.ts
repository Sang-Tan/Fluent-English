import { LIGHT_THEME_COLORS, COMMON_COLORS } from "src/styles/globalColors";
import { StyleSheet } from "react-native";

export default StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: LIGHT_THEME_COLORS.BACKGROUND,
    padding: 20,
  },
  text: {
    color: LIGHT_THEME_COLORS.TEXT,
  },
  submitButton: {
    backgroundColor: COMMON_COLORS.DARK_GREEN,
    padding: 15,
    borderRadius: 10,
    alignItems: "center",
    justifyContent: "center",
  },
  disabledSubmitButton: {
    backgroundColor: COMMON_COLORS.GRAY,
  },
  submitButtonText: {
    color: COMMON_COLORS.WHITE,
  },
});
