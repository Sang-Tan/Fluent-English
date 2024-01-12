/**
 * @typedef {Object} AttachmentData
 * @property {string} type
 * @property {string} url
 * @property {string} id
 */
/**
 * @typedef {Object} QuestionData
 * @property {string} content
 * @property {AttachmentData} attachment
 */

/**
 * @typedef {Object} AttachmentState
 * @property {string} type
 * @property {string} pendingUrl
 * @property {AttachmentData} savedData
 */
/**
 * @typedef {Object} QuestionState
 * @property {string} content
 * @property {AttachmentState} attachment
 */

// to make this file a module
export default undefined;
