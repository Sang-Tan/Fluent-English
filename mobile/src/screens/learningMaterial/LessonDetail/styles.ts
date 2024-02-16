import { MAIN_COLORS } from "src/styles/globalColors";

import { StyleSheet } from "react-native";

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: MAIN_COLORS.BACKGROUND,
    padding: 10,
  },
  header: {
    paddingBottom: 10,
  },
  title: {
    fontSize: 20,
    fontWeight: "bold",
    color: MAIN_COLORS.TEXT,
    paddingBottom: 10,
  },
  text: {
    color: MAIN_COLORS.TEXT,
  },
  button: {
    backgroundColor: MAIN_COLORS.PRIMARY,
    padding: 10,
    borderRadius: 10,
    alignItems: "center",
    justifyContent: "center",
  },
  buttonPressed: {
    backgroundColor: MAIN_COLORS.PRIMARY_DARK,
  },
  buttonText: {
    color: MAIN_COLORS.TEXT,
    fontWeight: "bold",
    fontSize: 20,
  },
});

export default styles;
