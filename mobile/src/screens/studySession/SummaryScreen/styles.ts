import { MAIN_COLORS } from "src/styles/globalColors";
import { StyleSheet } from "react-native";

export default StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: MAIN_COLORS.BACKGROUND,
  },
  text: {
    color: MAIN_COLORS.TEXT,
  },
  titleContainer: {
    alignItems: "center",
    margin: 20,
  },
  titleIcon: {
    marginBottom: 10,
  },
  title: {
    fontSize: 25,
    fontWeight: "bold",
    color: MAIN_COLORS.TEXT,
  },
  subtitle: {
    fontSize: 20,
    color: MAIN_COLORS.TEXT,
  },
  button: {
    backgroundColor: MAIN_COLORS.PRIMARY,
    padding: 10,
    borderRadius: 10,
    margin: 20,
  },
  buttonText: {
    color: MAIN_COLORS.TEXT,
    textAlign: "center",
  },
});
