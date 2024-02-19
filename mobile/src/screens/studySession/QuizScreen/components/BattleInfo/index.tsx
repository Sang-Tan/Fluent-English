import { BattleInfoDto } from "src/screens/studySession/types";

import { View, Text } from "react-native";

interface Props {
  data: BattleInfoDto;
}

function BattleInfo({ data }: Props) {
  return (
    <View>
      <Text>User HP: {data.userHp}</Text>
      <Text>User streak: {data.userStreak}</Text>
      <Text>User shield: {data.userShield}</Text>
      <Text>Enemy name: {data.enemyName}</Text>
    </View>
  );
}

export default BattleInfo;
