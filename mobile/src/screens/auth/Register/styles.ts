import { StyleSheet } from "react-native";

import { MAIN_COLORS } from "src/styles/globalColors";

const COLORS = Object.freeze({
  OUTLINE: "#E4F1F8",
});

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: MAIN_COLORS.BACKGROUND,
  },
  topPanel: {
    height: "70%",
    flexDirection: "column",
    justifyContent: "flex-end",
    alignItems: "center",
  },
  bottomPanel: {
    height: "30%",
    flexDirection: "column",
    justifyContent: "flex-start",
    alignItems: "center",
  },
  logoWrapper: {
    width: 70,
    height: 70,
    borderRadius: 20,
    marginBottom: 20,
    borderColor: COLORS.OUTLINE,
    borderWidth: 1,
    justifyContent: "center",
    alignItems: "center",
  },
  logo: {
    width: 50,
    height: 50,
  },
  title: {
    fontSize: 30,
    color: MAIN_COLORS.TEXT,
    marginBottom: 50,
  },
  text: {
    color: MAIN_COLORS.TEXT,
  },
  inputWrapper: {
    width: "80%",
    borderColor: COLORS.OUTLINE,
    borderWidth: 1,
    borderRadius: 15,
    paddingVertical: 10,
    paddingHorizontal: 20,
    flexDirection: "row",
    alignItems: "center",
    marginBottom: 20,
  },
  inputIcon: {
    marginRight: 10,
  },
  textInput: {
    color: MAIN_COLORS.TEXT,
    flex: 1,
  },
  registerButton: {
    width: "80%",
    height: 50,
    borderRadius: 15,
    backgroundColor: MAIN_COLORS.PRIMARY,
    justifyContent: "center",
    alignItems: "center",
    marginBottom: 20,
  },
  registerButtonText: {
    color: MAIN_COLORS.TEXT,
    fontSize: 15,
  },
});

export { COLORS };
export default styles;
