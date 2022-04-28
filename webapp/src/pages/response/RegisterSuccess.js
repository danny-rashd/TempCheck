import React, { useState } from "react";
import {
  Button,
  Container,
  Form,
  Card,
  Modal,
  Row,
  Col,
} from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEnvelope } from "@fortawesome/free-solid-svg-icons";
import "../home.css";
import axios from "axios";
import styled from "styled-components";
const MainWrapper = styled.div`
  padding-top: 40px;
`;

export const RegisterSuccess = (props) => {
  const [show, setShow] = useState(false);
  const [values, setValues] = useState({
    email: "",
  });
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);
  const RESEND_EMAIL_API = "http://localhost:8080/api/v1/token-resend";

  const handleSubmit = (evt) => {
    evt.preventDefault();
    setShow(false);
    console.log(values);
    setValues("");
    axios
      .get(RESEND_EMAIL_API, { params: { email: values.email } })
      .then(() => {
        console.log("Added Tweets");
        alert("NEW CONFIRMATION EMAIL SENT");
      });
  };

  const handleChange = (e) => {
    e.persist();
    setValues((values) => ({
      ...values,
      [e.target.name]: e.target.value,
    }));
  };

  const loginRedirect = () => {
    localStorage.clear();
    props.history.push("/login");
  };

  return (
    <Container>
      <MainWrapper>
        <Card>
          <Card.Header as="h5">Registration Successful!</Card.Header>
          <Card.Body>
            <Card.Title>
              {" "}
              To activate your account, please click the confirmation link sent
              to your email
            </Card.Title>
            <Row xs={4}>
              <Col>
                <Button onClick={() => loginRedirect()}>
                  Go to Login page
                </Button>
              </Col>
              <Col>
                <Button variant="info" onClick={handleShow}>
                  Resend Confirmation email
                </Button>
              </Col>
            </Row>
          </Card.Body>
        </Card>

        <Modal show={show} onHide={handleClose} animation={false}>
          <Modal.Header closeButton>
            <Modal.Title>Resend Confirmation email</Modal.Title>
          </Modal.Header>
          <Form onSubmit={handleSubmit}>
            <Modal.Body>
              {" "}
              <div className="input-group">
                <div className="input-group-prepend">
                  <span className="input-group-text" id="basic-addon1">
                    <FontAwesomeIcon icon={faEnvelope} />
                  </span>
                </div>
                <input
                  id="email"
                  name="email"
                  type="email"
                  placeholder="Email address"
                  className="form-control"
                  value={values.email}
                  onChange={handleChange}
                  autoComplete="off"
                />
              </div>{" "}
            </Modal.Body>
            <Modal.Footer>
              <Button className="ml-1" type="submit" variant="primary">
                Submit
              </Button>
            </Modal.Footer>
          </Form>
        </Modal>
      </MainWrapper>
    </Container>
  );
};
