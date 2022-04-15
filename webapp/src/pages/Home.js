import { Container, Card, Form, Button, Row, Col } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../components/auth";
export const Home = () => {
  const navigate = useNavigate();
  const auth = useAuth();
  const handleLogout = () => {
    auth.logout();
    navigate("/");
  };
  return (
    <Container id="main-container" className="d-grid h-50">
      <Form id="sign-in-form" className="text-center p-2 w-100">
        {" "}
        <div>Welcome {auth.user}</div>
        <Button onClick={handleLogout}>Logout</Button>
      </Form>
      <Card>
        <Card.Header></Card.Header>
        <Card.Body>
          <Row>
            <Card.Title>Home page</Card.Title>
            <Card.Text>This is the home page</Card.Text>
            <Col className="text-center">
              <Button variant="primary">Login as User</Button>
            </Col>
          </Row>
        </Card.Body>
      </Card>
    </Container>
  );
};
