import { MAIN_COLORS } from "src/styles/globalColors";
import { StyleSheet } from "react-native";

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: MAIN_COLORS.BACKGROUND,
    paddingLeft: 20,
    paddingRight: 20,
    paddingTop: 10,
  },
  searchContainer: {
    marginTop: 10,
  },
  searchBox: {
    backgroundColor: "white",
    borderRadius: 10,
    padding: 10,
    marginBottom: 10,
  },
  lessonsContainer: {
    // flex: 1,
  },
  hidden: {
    display: "none",
  },
  headerTitle: {
    fontSize: 20,
    fontWeight: "bold",
    color: MAIN_COLORS.TEXT,
  },
});

export default styles;
