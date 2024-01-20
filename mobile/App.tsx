import { StatusBar } from "expo-status-bar";
import { StyleSheet, Text, View } from "react-native";
import React from "react";

function App() {
  return (
    <View style={styles.container}>
      <Text>Open up App.tsx to start working on your app! Test</Text>
      <StatusBar style="auto" />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: "#fff",
    flex: 1,
    justifyContent: "center",
    padding: 20,
  },
});

export default App;
