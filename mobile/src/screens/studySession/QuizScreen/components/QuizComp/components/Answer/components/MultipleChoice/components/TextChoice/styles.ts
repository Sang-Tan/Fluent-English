import { LIGHT_THEME_COLORS } from "src/styles/globalColors";
import { StyleSheet } from "react-native";

export default StyleSheet.create({
  container: {
    backgroundColor: LIGHT_THEME_COLORS.PRIMARY,
    borderRadius: 10,
    padding: 10,
    alignItems: "center",
    justifyContent: "center",
    height: 50,
  },
  selected: {
    backgroundColor: LIGHT_THEME_COLORS.PRIMARY_LIGHT,
  },
  text: {
    fontSize: 18,
    color: "white",
    fontWeight: "bold",
  },
});
