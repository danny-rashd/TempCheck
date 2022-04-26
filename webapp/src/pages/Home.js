import { Card, Form, Button } from "react-bootstrap";
import "./home.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHome, faRainbow } from "@fortawesome/free-solid-svg-icons";

export const Home = () => {
  return (
    <div className="centered">
      <Form id="sign-in-form" className="text-center p-2 w-100"></Form>
      <Card className="text-center">
        <Card.Header>
          <FontAwesomeIcon className="mr-2" icon={faHome} />
          Home
        </Card.Header>
        <Card.Body>
          <Card.Title>TempCheck</Card.Title>
          <FontAwesomeIcon className="ml-1" size="2x" icon={faRainbow} />
          <Card.Text>
            Web application to visualize and predict temperature data
          </Card.Text>
          <Button href="/login" variant="primary">
            Login
          </Button>
          <Button href="/register" variant="secondary">
            Register
          </Button>
        </Card.Body>
      </Card>
    </div>
  );
};
