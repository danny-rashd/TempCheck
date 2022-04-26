import { faEnvelope } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React from "react";
import { Card, Button, Container } from "react-bootstrap";

function ForgotPassword() {
  return (
    <Container className="d-flex align-items-center p-5 justify-content-center w-50">
      <Card className="text-center">
        <Card.Header>Forgot Password?</Card.Header>
        <Card.Body>
          <Card.Text>
            Enter your email address{" "}
            <div class="input-group mt-2">
              <div class="input-group-prepend">
                <span class="input-group-text" id="basic-addon1">
                  <FontAwesomeIcon icon={faEnvelope} />
                </span>
              </div>
              <input
                id="email"
                name="email"
                type="email"
                placeholder="Email address"
                className="form-control"
                autoComplete="off"
              />
              <Button className="ml-1" variant="primary">
                Send email
              </Button>
            </div>{" "}
          </Card.Text>
        </Card.Body>
      </Card>
    </Container>
  );
}

export default ForgotPassword;
