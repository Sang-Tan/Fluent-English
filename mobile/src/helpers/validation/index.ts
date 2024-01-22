function isValidEmail(email: string): boolean {
  const re = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;
  return re.test(email);
}

export { isValidEmail };
