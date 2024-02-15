import { StyleSheet } from "react-native";
import { MAIN_COLORS } from "src/styles/globalColors";

const styles = StyleSheet.create({
  container: {
    flexDirection: "row",
    backgroundColor: MAIN_COLORS.BACKGROUND,
    padding: 10,
    marginBottom: 15,
    borderRadius: 10,
    shadowColor: "#B8B8D2",
    shadowOffset: {
      width: 0,
      height: 4,
    },
    shadowOpacity: 0.3,
    shadowRadius: 1,
    elevation: 4,
    borderColor: "#170E3F",
    borderWidth: 1,
  },
  leftContainer: {
    flex: 1,
  },
  difficultyBox: {
    backgroundColor: MAIN_COLORS.PRIMARY,
    borderRadius: 10,
    alignItems: "center",
    justifyContent: "center",
    width: 50,
    height: 50,
  },
  difficultyText: {
    color: MAIN_COLORS.TEXT,
    fontWeight: "bold",
    fontSize: 25,
  },
  rightContainer: {
    flex: 4,
  },
  lessonName: {
    fontSize: 20,
    fontWeight: "bold",
    color: MAIN_COLORS.TEXT,
  },
});

export default styles;
