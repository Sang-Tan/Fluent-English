function getEnv(key: string) {
  const envVar = process.env[key];
  if (envVar === undefined) {
    throw new Error(`Environment variable ${key} is missing`);
  }

  return envVar;
}

export default getEnv;
