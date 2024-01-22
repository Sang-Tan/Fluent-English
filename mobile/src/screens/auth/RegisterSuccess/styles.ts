import { MAIN_COLORS } from "src/styles/globalColors";
import { StyleSheet } from "react-native";

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: MAIN_COLORS.BACKGROUND,
    justifyContent: "center",
    alignItems: "center",
  },
  title: {
    fontSize: 30,
    color: MAIN_COLORS.TEXT,
    marginBottom: 50,
  },
  button: {
    width: "80%",
    height: 50,
    borderRadius: 15,
    backgroundColor: MAIN_COLORS.PRIMARY,
    justifyContent: "center",
    alignItems: "center",
    marginBottom: 20,
  },
  buttonText: {
    fontSize: 20,
    color: MAIN_COLORS.TEXT,
  },
});

export default styles;
