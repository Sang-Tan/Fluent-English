import { LIGHT_THEME_COLORS } from "src/styles/globalColors";
import { StyleSheet } from "react-native";

const COLORS = {
  AUDIO_ICON: LIGHT_THEME_COLORS.PRIMARY,
};

const styles = StyleSheet.create({
  container: {
    // flex: 1,
    paddingTop: 20,
    paddingBottom: 20,
    flexDirection: "column",
    justifyContent: "space-between",
    alignItems: "center",
  },
  content: {
    color: LIGHT_THEME_COLORS.TEXT,
    fontSize: 30,
    fontWeight: "bold",
    marginTop: 30,
  },
});

export { COLORS };
export default styles;
