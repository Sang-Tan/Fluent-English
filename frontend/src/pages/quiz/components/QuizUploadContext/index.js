import { createContext } from "react";

/**
 * @typedef {Object} QuizUploadContextValue
 * @property {string} uploadFolder
 */
/**
 * @type {React.Context<QuizUploadContextValue>}
 */
export const QuizUploadContext = createContext();

function QuizUploadContextProvider({ uploadFolder, children }) {
  return (
    <QuizUploadContext.Provider value={{ uploadFolder }}>
      {children}
    </QuizUploadContext.Provider>
  );
}

export default QuizUploadContextProvider;
