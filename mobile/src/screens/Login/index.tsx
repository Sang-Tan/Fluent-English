import { COLORS } from "./styles";
import globalStyles from "src/styles/globalStyles";
import styles from "./styles";
import { useState } from "react";
import { Pressable, StatusBar, Text, TextInput, View } from "react-native";
import { FontAwesomeIcon } from "@fortawesome/react-native-fontawesome";
import {
  faEnvelope,
  faEye,
  faEyeSlash,
} from "@fortawesome/free-regular-svg-icons";
import { faLock } from "@fortawesome/free-solid-svg-icons";

function Login() {
  const [showPassword, setShowPassword] = useState<boolean>(false);
  StatusBar.setBarStyle("light-content", true);

  const toggleShowPassword = () => {
    setShowPassword(!showPassword);
  };

  return (
    <View style={[globalStyles.statusBarPadding, styles.container]}>
      <View style={styles.topPanel}>
        <View style={styles.logoWrapper}>
          <FontAwesomeIcon
            icon={faLock}
            size={40}
            color={COLORS.OUTLINE}
            style={styles.logo}
          />
        </View>
        <Text style={styles.title}>Welcome back!</Text>
        <View style={styles.inputWrapper}>
          <View>
            <FontAwesomeIcon
              icon={faEnvelope}
              size={20}
              color={COLORS.OUTLINE}
              style={styles.inputIcon}
            />
          </View>
          <TextInput
            style={styles.textInput}
            placeholder="Email"
            editable
            placeholderTextColor="#aaa"
          />
        </View>
        <View style={styles.inputWrapper}>
          <View>
            <Pressable onPress={toggleShowPassword}>
              <FontAwesomeIcon
                icon={showPassword ? faEye : faEyeSlash}
                size={20}
                color={COLORS.OUTLINE}
                style={styles.inputIcon}
              />
            </Pressable>
          </View>
          <TextInput
            style={styles.textInput}
            placeholder="Password"
            editable
            placeholderTextColor="#aaa"
            secureTextEntry={!showPassword}
          />
        </View>
        <Pressable style={styles.loginButton}>
          <Text style={styles.loginButtonText}>Login</Text>
        </Pressable>
      </View>
      <View style={styles.bottomPanel}>
        <Text style={styles.text}>
          Don't have an account?{" "}
          <Text style={[styles.text, { fontWeight: "bold" }]}>Sign up</Text>
        </Text>
      </View>
    </View>
  );
}

export default Login;
