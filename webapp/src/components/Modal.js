import React from "react";
import { Modal } from "react-bootstrap";

function Modal() {
  return (
    <>
      <Button
        variant="primary"
        onClick={() => setSmShow(true)}
        className="me-2"
      >
        Upload file
      </Button>
      <Modal
        size="sm"
        show={smShow}
        onHide={() => setSmShow(false)}
        aria-labelledby="example-modal-sizes-title-sm"
      >
        <Modal.Header closeButton>
          <Modal.Title id="example-modal-sizes-title-sm">
            Small Modal
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>...</Modal.Body>
      </Modal>
    </>
  );
}
export default Modal;
