import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import InputGroup from "react-bootstrap/InputGroup";
import FormControl from "react-bootstrap/FormControl";
import Card from "react-bootstrap/Card";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faEnvelope,
  faLock,
  faRightToBracket,
  faUser,
} from "@fortawesome/free-solid-svg-icons";
import "./Login.css";

function Register() {
  return (
    <Container id="main-container" className="d-grid h-100">
      <Card>
        <Form id="sign-up-form" className="text-center p-3 w-100">
          <Form.Group className="d-flex justify-content-center mb-1">
            <img
              className="mb-4 bootstrap-logo"
              src="https://icons.getbootstrap.com/assets/icons/thermometer-half.svg"
              alt="Bootstrap 5"
            />
          </Form.Group>
          <h1 className="mb-3 fs-31 fw-normal">Register</h1>
          <InputGroup
            className="d-flex justify-content-center mt-2 mb-2"
            controlId="sign-in-email-address"
          >
            <InputGroup.Text>
              <FontAwesomeIcon icon={faEnvelope} />
            </InputGroup.Text>
            <Form.Control
              required
              autoComplete="off"
              type="email"
              size="lg"
              placeholder="Email address"
              className="position-relative"
            />
          </InputGroup>
          <InputGroup
            className="d-flex justify-content-center mt-2 mb-2"
            controlId="sign-in-email-address"
          >
            <InputGroup.Text>
              <FontAwesomeIcon icon={faUser} />
            </InputGroup.Text>
            <Form.Control
              required
              autoComplete="off"
              type="text"
              size="lg"
              placeholder="First Name"
              className="position-relative"
            />
          </InputGroup>
          <InputGroup
            className="d-flex justify-content-center mt-2 mb-2"
            controlId="sign-in-email-address"
          >
            <InputGroup.Text>
              <FontAwesomeIcon icon={faUser} />
            </InputGroup.Text>
            <Form.Control
              required
              autoComplete="off"
              type="email"
              size="lg"
              placeholder="Last Name"
              className="position-relative"
            />
          </InputGroup>
          <InputGroup
            className="d-flex justify-content-center mb-2"
            controlId="sign-in-password"
          >
            <InputGroup.Text>
              <FontAwesomeIcon icon={faLock} />
            </InputGroup.Text>
            <FormControl
              required
              autoComplete="off"
              type="password"
              size="lg"
              placeholder="Password"
              className="position-relative"
            />
          </InputGroup>
          <Form.Group className="d-flex justify-content-center m-1"></Form.Group>
          <div className="d-grid">
            <Button variant="primary" size="lg">
              Submit
              <FontAwesomeIcon icon={faRightToBracket} className="mr-3" />
            </Button>
          </div>
        </Form>
      </Card>
    </Container>
  );
}

export default Register;
