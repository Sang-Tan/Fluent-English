import ROUTE_NAMES from "src/routes/routeNames";
import globalStyles from "src/styles/globalStyles";
import styles from "./styles";
import { Text, Pressable, View } from "react-native";

function RegisterSuccess({ navigation }: { navigation: any }) {
  const handleGoToLogin = () => {
    navigation.navigate(ROUTE_NAMES.LOGIN);
  };

  return (
    <View style={[globalStyles.statusBarPadding, styles.container]}>
      <Text style={styles.title}>Register Success</Text>
      <Pressable onPress={handleGoToLogin} style={styles.button}>
        <Text style={styles.buttonText}>Go to Login</Text>
      </Pressable>
    </View>
  );
}

export default RegisterSuccess;
