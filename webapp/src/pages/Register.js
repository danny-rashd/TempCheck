import { useState, useEffect } from "react";
import { Form, Button, Card, Container } from "react-bootstrap";



function Register() {
  const initialValues = { username: "", email: "", password: "" };
  const [formValues, setFormValues] = useState(initialValues);
  const [formErrors, setFormErrors] = useState({});
  const [isSubmit, setIsSubmit] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormValues({ ...formValues, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setFormErrors(validate(formValues));
    setIsSubmit(true);
  };

  useEffect(() => {
    console.log(formErrors);
    if (Object.keys(formErrors).length === 0 && isSubmit) {
      console.log(formValues);
    }
  }, [formErrors]);
  const validate = (values) => {
    const errors = {};
    const USER_REGEX = /^[a-zA-Z][a-zA-Z0-9_]{3,23}$/;
    const PWD_REGEX =/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_]).{8,24}$/;
    const EMAIL_REGEX = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/i;
    if (!values.username) {
      errors.username = "Username is required!";
    } else if (!USER_REGEX.test(values.username)) {
      errors.email = "This is not a valid email format!";
    }
    if (!values.email) {
      errors.email = "Email is required!";
    } else if (!EMAIL_REGEX.test(values.email)) {
      errors.email = "This is not a valid email format!";
    }
    if (!values.password) {
      errors.password = "Password is required";
    } else if (!PWD_REGEX.test(values.password)) {
      errors.password = "This is not a valid password format!";
    }
    return errors;
  };

  return (
    <Container
      className="d-flex align-items-center justify-content-center"
      style={{ minHeight: "100vh" }}
    >
      <div className="w-100" style={{ maxWidth: "400px" }}>
        {Object.keys(formErrors).length === 0 && isSubmit ? (
          <div className="ui message success">Signed in successfully</div>
        ) : (
          <pre>{JSON.stringify(formValues, undefined, 2)}</pre>
        )}
        <Card>
          <Form onSubmit={handleSubmit}>
            <h2 className="text-center mb-4">Sign Up</h2>
            <div className="ui divider"></div>
            <div className="ui form">
              <Form.Group className="mb-3" id="email">
                <Form.Label>Username</Form.Label>
                <Form.Control
                  type="text"
                  name="username"
                  placeholder="Username"
                  value={formValues.onChange}
                  onChange={handleChange}
                  required
                ></Form.Control>
              </Form.Group>
              <p>{formErrors.username}</p>
              <Form.Group id="email">
                <Form.Label>Email</Form.Label>
                <Form.Control
                  type="text"
                  name="email"
                  placeholder="Email"
                  value={formValues.email}
                  onChange={handleChange}
                  required
                ></Form.Control>
              </Form.Group>
              <p>{formErrors.email}</p>
              <Form.Group id="password">
                <Form.Label>Password</Form.Label>
                <Form.Control
                  type="password"
                  name="password"
                  placeholder="Password"
                  value={formValues.password}
                  onChange={handleChange}
                  required
                ></Form.Control>
              </Form.Group>
              <p>{formErrors.password}</p>
              <Button className="w-100" type="Login">
                Sign Up
              </Button>
            </div>
          </Form>
        </Card>
      </div>
    </Container>
  );
}

export default Register;
