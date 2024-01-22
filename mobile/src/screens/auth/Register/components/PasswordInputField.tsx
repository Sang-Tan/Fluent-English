import { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-native-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-regular-svg-icons";
import { View, TextInput, Pressable } from "react-native";
import styles, { COLORS } from "../styles";

interface PasswordInputFieldProps {
  value: string;
  onChangeText: (text: string) => void;
  placeholder: string;
}
function PasswordInputField({
  value,
  onChangeText,
  placeholder,
}: PasswordInputFieldProps) {
  const [showPassword, setShowPassword] = useState<boolean>(false);

  const toggleShowPassword = () => {
    setShowPassword(!showPassword);
  };

  return (
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
        placeholder={placeholder}
        placeholderTextColor="#aaa"
        secureTextEntry={!showPassword}
        value={value}
        onChangeText={onChangeText}
      />
    </View>
  );
}

export default PasswordInputField;
