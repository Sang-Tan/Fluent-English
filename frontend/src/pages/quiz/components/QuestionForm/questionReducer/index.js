import {
  INIT_STATE,
  CHANGE_CONTENT,
  ADD_ATTACHMENT,
  REMOVE_ATTACHMENT,
  CHANGE_ATTACHMENT_PENDING_URL,
  CHANGE_ATTACHMENT_TYPE,
} from "./constants";
const DEFAULT_ATTACHMENT_TYPE = "image";

/**
 * @type {import("../typeDefs").QuestionState} initialQuestion
 */
const initialQuestion = {
  content: "",
};

/**
 * @param {import("../typeDefs").QuestionState} questionState
 * @param {any} action
 */
const questionReducer = (questionState, action) => {
  switch (action.type) {
    case INIT_STATE:
      return initializeQuestion(action.payload.initialQuestion);
    case CHANGE_CONTENT:
      return changeContent(questionState, action.payload.content);
    case ADD_ATTACHMENT:
      return addAttachment(questionState);
    case CHANGE_ATTACHMENT_PENDING_URL:
      return changeAttachmentPendingUrl(
        questionState,
        action.payload.pendingUrl
      );
    case CHANGE_ATTACHMENT_TYPE:
      return changeAttachmentType(questionState, action.payload.type);
    case REMOVE_ATTACHMENT:
      return removeAttachment(questionState);
    default:
      throw new Error("Invalid action type");
  }
};

/**
 * @param {import("../typeDefs").QuestionData} initialQuestion
 * @returns {import("../typeDefs").QuestionState}
 */
const initializeQuestion = (initialQuestion) => {
  /** @type {import("../typeDefs").QuestionState} */
  const state = {};
  state.content = initialQuestion?.content || "";
  if (initialQuestion?.attachment) {
    state.attachment = {
      type: initialQuestion.attachment.type || DEFAULT_ATTACHMENT_TYPE,
      url: initialQuestion.attachment.url || null,
      pendingUrl: null,
      savedData: initialQuestion.attachment,
    };
  }
  return state;
};

/**
 * @param {import("../typeDefs").QuestionState} questionState
 * @param {string} content
 * @returns {import("../typeDefs").QuestionState}
 */
const changeContent = (questionState, content) => {
  return {
    ...questionState,
    content,
  };
};

/**
 * @param {import("../typeDefs").QuestionState} questionState
 * @param {import("../typeDefs").AttachmentData} attachmentData
 * @returns {import("../typeDefs").QuestionState}
 */
const addAttachment = (questionState) => {
  return {
    ...questionState,
    attachment: {
      type: DEFAULT_ATTACHMENT_TYPE,
    },
  };
};

/**
 * @param {import("../typeDefs").QuestionState} questionState
 * @param {string} attachmentPendingUrl
 * @returns {import("../typeDefs").QuestionState}
 */
const changeAttachmentPendingUrl = (questionState, attachmentPendingUrl) => {
  return {
    ...questionState,
    attachment: {
      ...questionState.attachment,
      pendingUrl: attachmentPendingUrl,
    },
  };
};

/**
 * @param {import("../typeDefs").QuestionState} questionState
 * @param {string} attachmentType
 * @returns {import("../typeDefs").QuestionState}
 */
const changeAttachmentType = (questionState, attachmentType) => {
  return {
    ...questionState,
    attachment: {
      type: attachmentType,
    },
  };
};

/**
 * @param {import("../typeDefs").QuestionState} questionState
 * @returns {import("../typeDefs").QuestionState}
 */
const removeAttachment = (questionState) => {
  return {
    ...questionState,
    attachment: undefined,
  };
};

export { initializeQuestion, initialQuestion };
export default questionReducer;
