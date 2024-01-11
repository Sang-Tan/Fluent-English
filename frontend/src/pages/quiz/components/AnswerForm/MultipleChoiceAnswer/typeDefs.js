import "../typeDefs";
/**
 * @typedef {Object} ChoiceState
 * @property {number} key
 * @property {string | import("../typeDefs").AudioAnswerData} data
 */
/**
 * @typedef {Object} MultipleChoiceAnswerState
 * @property {ChoiceState[]} choices
 * @property {number} correctChoice - key of the correct choice
 * @property {string} choiceType
 */
/**
 * @param {MultipleChoiceAnswerState} state
 * @param {Object} action
 * @returns {MultipleChoiceAnswerState}
 */

// to make this file a module
export default undefined;
