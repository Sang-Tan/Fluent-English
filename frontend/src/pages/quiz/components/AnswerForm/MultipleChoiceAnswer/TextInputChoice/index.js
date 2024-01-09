import "../../typeDefs";

import { forwardRef, useImperativeHandle, useState, useEffect } from "react";
import styles from "./TextInputChoice.module.scss";
import classNames from "classnames/bind";

const cx = classNames.bind(styles);

/**
 * @typedef {Object} TextInputChoiceProps
 * @property {string} data
 */
/**
 * @typedef {Object} TextInputChoiceRef
 * @property {() => Promise<string>} validateAndGetData
 */
/**
 * @type {React.ForwardRefExoticComponent<React.PropsWithoutRef<TextInputChoiceProps> & React.RefAttributes<TextInputChoiceRef>>}
 */
const TextInputChoice = forwardRef(({ data }, ref) => {
  const [pendingValue, setPendingValue] = useState(data || "");

  useEffect(() => {
    setPendingValue(data || "");
  }, [data]);

  useImperativeHandle(ref, () => ({
    /**
     * @returns {Promise<string>}
     */
    async validateAndGetData() {
      if (!pendingValue) {
        throw Error("Text is required");
      }

      return pendingValue;
    },
  }));

  return (
    <input
      className={cx("input")}
      value={pendingValue}
      onChange={(e) => setPendingValue(e.target.value)}
      placeholder="Enter answer"
    />
  );
});

export default TextInputChoice;
