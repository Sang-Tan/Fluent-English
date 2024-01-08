import { useState, useRef } from "react";
import { toast } from "react-toastify";
import { PencilSquare, EyeFill, ArrowClockwise } from "react-bootstrap-icons";
import { Modal, Button } from "react-bootstrap";
import classNames from "classnames/bind";
import styles from "./ImageFileInput.module.scss";

const cx = classNames.bind(styles);

function ImageFileInput({
  initialImageUrl = null,
  onUploadUrlChange = (url) => {},
}) {
  console.log("Render ImageFileInput");
  const [isZooming, setZooming] = useState(false);
  const [previewUrl, setPreviewUrl] = useState(initialImageUrl);
  const [isDeleteModalOpen, setDeleteModalOpen] = useState(false);
  const fileInputRef = useRef(null);

  const changePreviewUrl = (url) => {
    if (previewUrl && previewUrl.startsWith("blob:")) {
      URL.revokeObjectURL(previewUrl);
    }
    setPreviewUrl(url === null ? initialImageUrl : url);
    onUploadUrlChange(url);
  };

  const handleFileChange = (event) => {
    if (event.target.files.length === 0) {
      return;
    }
    const file = event.target.files[0];
    if (file && file.type.startsWith("image/")) {
      changePreviewUrl(URL.createObjectURL(file));
    } else {
      toast.error("Please select an image file.");
    }
  };

  const handleFileRemove = () => {
    changePreviewUrl(null);
    fileInputRef.current.value = null;
    setDeleteModalOpen(false);
  };

  return (
    <>
      <div className={cx("upload")}>
        <div className={cx("preview")}>
          {previewUrl ? (
            <img src={previewUrl} alt="Preview" className={cx("image")} />
          ) : (
            <div>No Image</div>
          )}
        </div>
        <div className={cx("options")}>
          <div className={cx("btn-wrapper")}>
            <div className={cx("square-container")}>
              <button
                className={cx("btn", "btn btn-success")}
                disabled={!previewUrl}
                onClick={() => setZooming(true)}
              >
                <EyeFill />
              </button>
            </div>
          </div>
          <div className={cx("btn-wrapper")}>
            <div className={cx("square-container")}>
              <input
                type="file"
                accept="image/*"
                onChange={handleFileChange}
                ref={fileInputRef}
              />
              <button
                className={cx("btn", "btn btn-primary")}
                onClick={() => fileInputRef.current.click()}
              >
                <PencilSquare />
              </button>
            </div>
          </div>
          <div className={cx("btn-wrapper")}>
            <div className={cx("square-container")}>
              <button
                className={cx("btn", "btn btn-danger")}
                onClick={() => setDeleteModalOpen(true)}
                disabled={!previewUrl || previewUrl === initialImageUrl}
              >
                <ArrowClockwise />
              </button>
            </div>
          </div>
        </div>
      </div>

      {isZooming && (
        <div>
          <div
            className={cx("preview-layer")}
            onClick={() => setZooming(false)}
          >
            <div className={cx("wrapper")}>
              <img src={previewUrl} alt="Preview" className={cx("image")} />
            </div>
          </div>
        </div>
      )}

      <Modal
        show={isDeleteModalOpen}
        onHide={() => setDeleteModalOpen(false)}
        centered
      >
        <Modal.Header closeButton>
          <Modal.Title>Confirm</Modal.Title>
        </Modal.Header>
        <Modal.Body>Are you sure you want to delete this image?</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setDeleteModalOpen(false)}>
            Cancel
          </Button>
          <Button variant="danger" onClick={handleFileRemove}>
            Delete
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default ImageFileInput;
