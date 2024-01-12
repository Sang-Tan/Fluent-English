import "./typeDefs";

import React, {
  forwardRef,
  useImperativeHandle,
  useState,
  useEffect,
  useContext,
  useReducer,
} from "react";
import useAsyncRequest from "src/hooks/useAsyncRequest";
import { QuizUploadContext } from "../QuizUploadContext";

import questionReducer, {
  initialQuestion,
  initializeQuestion,
} from "./questionReducer";
import questionActions from "./questionReducer/constants";

import ImageFileInput from "src/components/fileinput/ImageFileInput";
import AudioFileInput from "src/components/fileinput/AudioFileInput";
import { Form, Button } from "react-bootstrap";

import style from "./QuestionForm.module.scss";
import classNames from "classnames/bind";

const cx = classNames.bind(style);

/**
 * @typedef {import("./typeDefs").QuestionData} QuestionData
 */
/**
 * @typedef {object} QuestionFormProps
 * @property {QuestionData} [data]
 * @property {any} [errors]
 */
/**
 * @typedef {object} QuestionFormRef
 * @property {() => Promise<QuestionData>} validateAndGetData
 */
/**
 * @type {React.ForwardRefExoticComponent<React.PropsWithoutRef<QuestionFormProps> & React.RefAttributes<QuestionFormRef>>}
 */
const QuestionForm = forwardRef(
  ({ data = initialQuestion, errors = {} }, ref) => {
    const [questionState, dispatch] = useReducer(
      questionReducer,
      data,
      initializeQuestion
    );
    const [validationError, setValidationError] = useState(null);
    const [request] = useAsyncRequest();
    const { uploadFolder } = useContext(QuizUploadContext);

    const { content, attachment } = questionState;
    const contentError = validationError?.content || errors?.content;
    const attachmentError = validationError?.attachment || errors?.attachment;

    useEffect(() => {
      dispatch({
        type: questionActions.INIT_STATE,
        payload: {
          initialQuestion: data,
        },
      });
    }, [data]);

    useImperativeHandle(ref, () => ({
      /**
       * @returns {Promise<QuestionData>}
       */
      async validateAndGetData() {
        setValidationError(null);
        const errors = {};

        /** @type {QuestionData} */
        const extractedData = {};

        if (!content) {
          errors.content = "Content is required";
        } else {
          extractedData.content = content;
        }

        try {
          const uploadedAttachment = await tryUploadAttachment();
          if (uploadedAttachment) {
            extractedData.attachment = uploadedAttachment;
            extractedData.attachment.type = attachment.type;
          } else {
            extractedData.attachment = attachment?.savedData || undefined;
          }
        } catch (error) {
          errors.attachment = error.message;
        }

        if (Object.keys(errors).length > 0) {
          setValidationError(errors);
          throw Error("Validation failed");
        }

        return extractedData;
      },
    }));

    /**
     * @returns {Promise<import("./typeDefs").AttachmentData> | Promise<null>}
     */
    const tryUploadAttachment = async () => {
      if (!attachment) {
        return null;
      }

      if (!attachment.pendingUrl) {
        if (attachment?.url) {
          return null;
        } else {
          throw Error("No attachment");
        }
      }

      const formData = new FormData();
      formData.append(
        "file",
        await fetch(attachment.pendingUrl).then((r) => r.blob())
      );
      formData.append("folder", uploadFolder);

      const resp = await request("/upload", {
        method: "POST",
        body: formData,
      });

      if (!resp.ok) {
        throw Error("Upload failed");
      }

      const respData = await resp.json();

      return {
        url: respData.url,
        id: respData.id,
      };
    };

    const changeAttachmentType = (type) => {
      dispatch({
        type: questionActions.CHANGE_ATTACHMENT_TYPE,
        payload: {
          type,
        },
      });
    };

    const addAttachment = () => {
      dispatch({
        type: questionActions.ADD_ATTACHMENT,
      });
    };

    const removeAttachment = () => {
      dispatch({
        type: questionActions.REMOVE_ATTACHMENT,
      });
    };

    const changeAttachmentPendingUrl = (url) => {
      dispatch({
        type: questionActions.CHANGE_ATTACHMENT_PENDING_URL,
        payload: {
          pendingUrl: url,
        },
      });
    };

    return (
      <div>
        <Form.Group controlId="content" className="mb-3">
          <Form.Label>Content</Form.Label>
          <Form.Control
            type="text"
            name="content"
            value={content}
            onChange={(e) => {
              dispatch({
                type: questionActions.CHANGE_CONTENT,
                payload: {
                  content: e.target.value,
                },
              });
            }}
          />
          {contentError && (
            <Form.Text className="text-danger">{contentError}</Form.Text>
          )}
        </Form.Group>
        {attachment ? (
          <div>
            <Form.Group controlId="attachment" className="mb-3">
              <Form.Label>Attachment</Form.Label>
              <Form.Control
                as="select"
                name="attachmentType"
                value={attachment.type}
                onChange={(e) => changeAttachmentType(e.target.value)}
                className="mb-3"
              >
                <option value="image">Image</option>
                <option value="audio">Audio</option>
              </Form.Control>
              {attachment.type === "image" ? (
                <div className={cx("d-flex justify-content-center")}>
                  <div className={cx("image-inp-wrapper")}>
                    <div className={cx("ratio-wrapper")}>
                      <div className={cx("image-inp")}>
                        <ImageFileInput
                          name="image"
                          initialImageUrl={attachment.url}
                          onUploadUrlChange={changeAttachmentPendingUrl}
                        />
                      </div>
                    </div>
                  </div>
                </div>
              ) : (
                <AudioFileInput
                  name="audio"
                  initialAudioUrl={attachment.url}
                  onUploadUrlChange={changeAttachmentPendingUrl}
                />
              )}
              {attachmentError && (
                <Form.Text className="text-danger">{attachmentError}</Form.Text>
              )}
            </Form.Group>
            <div className="d-flex justify-content-center">
              <Button variant="outline-danger" onClick={removeAttachment}>
                Remove Attachment
              </Button>
            </div>
          </div>
        ) : (
          <Button variant="outline-primary" onClick={addAttachment}>
            Add Attachment
          </Button>
        )}
      </div>
    );
  }
);

export default QuestionForm;
