export interface RequestFunction {
  (url: string, options: RequestInit): Promise<any>;
}
