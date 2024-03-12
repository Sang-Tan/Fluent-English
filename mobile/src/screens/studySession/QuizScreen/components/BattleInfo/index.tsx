import { BattleInfoDto } from "src/types/study/session";

import { View, Text } from "react-native";

interface Props {
  data: BattleInfoDto;
}

function BattleInfo({ data }: Props) {
  return (
    <View>
      <Text>User HP: {data.userCurrentHp}</Text>
      <Text>User streak: {data.userStreak}</Text>
      <Text>User shield: {data.userShield}</Text>
      <Text>Enemy name: {data.enemyName}</Text>
    </View>
  );
}

export default BattleInfo;
