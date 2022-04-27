import React from "react";
import {
  Container,
  Card,
  Form,
  InputGroup,
  FormControl,
} from "react-bootstrap";
import "./home.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faHome,
  faRainbow,
  faCalendar,
} from "@fortawesome/free-solid-svg-icons";

function Predictions() {
  return (
    <Container id="main-container" className="h-50 w-100">
      <Card className="mt-3">
        <Card.Header className="text-center">
          <FontAwesomeIcon className="mr-2" icon={faHome} />
          Make a prediction
        </Card.Header>
        <Card.Body>
          <Card.Title className="text-center">TempCheck</Card.Title>
          <Card.Text className="text-center">
            <FontAwesomeIcon
              className="ml-1 justify-content-center flex-direction-row"
              size="2x"
              icon={faRainbow}
            />
          </Card.Text>
          <Card.Text className="text-center">
            Web application to visualize and predict temperature data
          </Card.Text>

          <Form.Label htmlFor="basic-url">
            {" "}
            Insert date to make predictions :
          </Form.Label>
          <InputGroup className="mb-3">
            <InputGroup.Text id="basic-addon3">
              <FontAwesomeIcon icon={faCalendar} />
            </InputGroup.Text>
            <FormControl id="basic-url" aria-describedby="basic-addon3" />
          </InputGroup>
        </Card.Body>
        <Card.Footer className="text-center">Predictions :</Card.Footer>
      </Card>
    </Container>
  );
}
export default Predictions;
