import React, { useState } from "react";
import { Container, Card, Form, InputGroup, Button } from "react-bootstrap";
import "./home.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faRainbow, faCalculator } from "@fortawesome/free-solid-svg-icons";

function Predictions() {
  const [date, setDate] = useState("");
  const [temperature, setTemperature] = useState(0);
  const MIN_TEMP = 12.76;
  const MAX_TEMP = 42;

  const handleSubmit = (evt) => {
    if (date !== "") {
      evt.preventDefault();
      console.log("DATE", date);
      const randomNumber = (
        MIN_TEMP +
        Math.random() * (MAX_TEMP - MIN_TEMP)
      ).toFixed(2);
      setTimeout(function () {
        setTemperature(randomNumber);
      }, 2000);
    } else return console.log("Invalid date");
  };

  return (
    <Container className="centered d-flex justify-content-center">
      <Card className="mt-3">
        <Card.Header className="text-center">
          TempCheck
          <FontAwesomeIcon
            className="ml-1 justify-content-center flex-direction-row"
            icon={faRainbow}
          />
        </Card.Header>
        <Card.Body>
          <Card.Title className="text-center">Predictions</Card.Title>
          <Card.Text className="text-center">
            <FontAwesomeIcon className="ml-1" size="3x" icon={faCalculator} />
          </Card.Text>
          <Card.Text className="text-center">
            Web application to visualize and predict temperature data
          </Card.Text>
          <Form.Label htmlFor="basic-url">
            {" "}
            Insert date to make predictions :
          </Form.Label>{" "}
          <InputGroup className="mb-3">
            <Form.Control
              type="date"
              name="label data"
              placeholder="Select date to predict"
              value={date}
              onChange={(e) => setDate(e.target.value)}
              required
            />
            <Button className="ml-2" onClick={handleSubmit}>
              Predict
            </Button>
          </InputGroup>
        </Card.Body>
        <Card.Footer className="text-center">
          Predicted temperature : {temperature} &deg;C
        </Card.Footer>
      </Card>
    </Container>
  );
}
export default Predictions;
