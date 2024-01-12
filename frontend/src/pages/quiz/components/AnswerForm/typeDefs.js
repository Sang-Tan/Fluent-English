/**
 * @typedef {Object} AnswerData
 * @property {string} type - "multiple-choice" | "input"
 * @property {string} explanation
 */
/**
 * @typedef {Object} InputAnswerDetail
 * @property {string} answer
 * @typedef {AnswerData & InputAnswerDetail} InputAnswerData
 */

/**
 * @typedef {Object} AudioAnswerData
 * @property {string} id
 * @property {string} url
 */
/**
 * @typedef {Object} AnswerChoiceData
 * @property {string} type - "audio" | "text"
 * @property {string[] | AudioAnswerData[]} data
 */
/**
 * @typedef {Object} MultipleChoiceAnswerDetail
 * @property {AnswerChoiceData} choices
 * @property {string} correctChoice - index of the correct choice (0-based)
 * @typedef {AnswerData & MultipleChoiceAnswerDetail} MultipleChoiceAnswerData
 */

// to make this file a module
export default undefined;
