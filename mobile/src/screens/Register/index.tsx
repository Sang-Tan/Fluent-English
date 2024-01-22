import ROUTE_NAMES from "src/routes/routeNames";
import styles, { COLORS } from "./styles";
import globalStyles from "src/styles/globalStyles";
import {
  View,
  Text,
  TextInput,
  Pressable,
  TouchableWithoutFeedback,
} from "react-native";
import { FontAwesomeIcon } from "@fortawesome/react-native-fontawesome";
import PasswordInputField from "./components/PasswordInputField";
import { faUsers, faEnvelope } from "@fortawesome/free-solid-svg-icons";

import { isValidEmail } from "src/helpers/validation";
import { useState } from "react";
import useRequest from "src/hooks/useRequest";

function Register({ navigation }: { navigation: any }) {
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [passwordConfirm, setPasswordConfirm] = useState<string>("");
  const doRequest = useRequest();

  const handleRegisterSubmit = async () => {
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

    if (password !== passwordConfirm) {
      alert("Passwords do not match");
      return;
    }

    try {
      const response = await doRequest("/register", {
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
        navigation.navigate(ROUTE_NAMES.REGISTER_SUCCESS);
      } else {
        alert("Something went wrong");
      }
    } catch (error) {
      console.log(error);
      alert("Something went wrong");
    }
  };

  return (
    <View style={[globalStyles.statusBarPadding, styles.container]}>
      <View style={styles.topPanel}>
        <View style={styles.logoWrapper}>
          <FontAwesomeIcon
            icon={faUsers}
            size={40}
            color={COLORS.OUTLINE}
            style={styles.logo}
          />
        </View>
        <Text style={styles.title}>Create an account</Text>
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
        <PasswordInputField
          value={password}
          onChangeText={setPassword}
          placeholder="Password"
        />
        <PasswordInputField
          value={passwordConfirm}
          onChangeText={setPasswordConfirm}
          placeholder="Confirm Password"
        />
        <Pressable style={styles.registerButton} onPress={handleRegisterSubmit}>
          <Text style={styles.registerButtonText}>Register</Text>
        </Pressable>
      </View>
      <View style={styles.bottomPanel}>
        <Text style={styles.text}>
          Already have an account?{" "}
          <TouchableWithoutFeedback
            onPress={() => navigation.navigate(ROUTE_NAMES.LOGIN)}
          >
            <Text style={[styles.text, { fontWeight: "bold" }]}>Sign in</Text>
          </TouchableWithoutFeedback>
        </Text>
      </View>
    </View>
  );
}

function validateEmail(email: string): string | null {
  if (!email) {
    return "Email is required";
  }

  if (!isValidEmail(email)) {
    return "Email is invalid";
  }

  return null;
}

function validatePassword(password: string): string | null {
  if (!password) {
    return "Password is required";
  }

  if (password.length < 8) {
    return "Password must be at least 8 characters";
  }

  return null;
}

export default Register;
