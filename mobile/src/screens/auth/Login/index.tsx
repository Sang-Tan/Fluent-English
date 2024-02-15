import ROUTE_NAMES from "src/routes/routeNames";
import { COLORS } from "./styles";
import { MAIN_COLORS } from "src/styles/globalColors";
import globalStyles from "src/styles/globalStyles";
import styles from "./styles";

import { isValidEmail } from "src/helpers/validation";
import { useState, useContext } from "react";
import useRequest from "src/hooks/useRequest";
import AuthContext from "src/contexts/AuthContext";

import {
  Pressable,
  StatusBar,
  Text,
  TextInput,
  View,
  TouchableWithoutFeedback,
} from "react-native";
import { FontAwesomeIcon } from "@fortawesome/react-native-fontawesome";
import {
  faEnvelope,
  faEye,
  faEyeSlash,
} from "@fortawesome/free-regular-svg-icons";
import { faLock } from "@fortawesome/free-solid-svg-icons";

function Login({ navigation }: { navigation: any }) {
  const [showPassword, setShowPassword] = useState<boolean>(false);
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const doRequest = useRequest();
  const authContext = useContext(AuthContext);

  const toggleShowPassword = () => {
    setShowPassword(!showPassword);
  };

  const handleLoginSubmit = async () => {
    const emailError = validateEmail(email);
    if (emailError) {
      alert(emailError);
      return;
    }

    const passwordError = validatePassword(password);
    if (passwordError) {
      alert(passwordError);
      return;
    }

    try {
      const response = await doRequest("/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email,
          password,
        }),
      });

      if (response.status === 200) {
        const respData: { accessToken: string } = await response.json();
        authContext.setAuth({
          ...authContext.auth,
          accessToken: respData.accessToken,
        });

        navigation.navigate(ROUTE_NAMES.HOME);
      } else if (response.status === 401) {
        alert("Invalid credentials");
      } else {
        alert("Something went wrong");
      }
    } catch (error) {
      console.error(error);
    }
  };

  const handleGotoSignup = () => {
    navigation.navigate(ROUTE_NAMES.REGISTER);
  };

  type EmailValidation = (email: string) => string | null;
  const validateEmail: EmailValidation = () => {
    if (!email) {
      return "Email is required";
    }
    if (!isValidEmail(email)) {
      return "Invalid email format";
    }
    return null;
  };

  type PasswordValidation = (password: string) => string | null;
  const validatePassword: PasswordValidation = () => {
    if (!password) {
      return "Password is required";
    }
    return null;
  };

  return (
    <>
      <StatusBar
        barStyle="light-content"
        backgroundColor={MAIN_COLORS.BACKGROUND}
      />
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
              placeholderTextColor="#aaa"
              value={email}
              onChangeText={setEmail}
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
              placeholderTextColor="#aaa"
              secureTextEntry={!showPassword}
              value={password}
              onChangeText={setPassword}
            />
          </View>
          <Pressable style={styles.loginButton} onPress={handleLoginSubmit}>
            <Text style={styles.loginButtonText}>Login</Text>
          </Pressable>
        </View>
        <View style={styles.bottomPanel}>
          <Text style={styles.text}>
            Don't have an account?{" "}
            <TouchableWithoutFeedback onPress={handleGotoSignup}>
              <Text style={[styles.text, { fontWeight: "bold" }]}>Sign up</Text>
            </TouchableWithoutFeedback>
          </Text>
        </View>
      </View>
    </>
  );
}

export default Login;
