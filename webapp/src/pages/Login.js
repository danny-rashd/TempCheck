import { useState } from 'react'
import {
  Container,
  Card,
  Form,
  InputGroup,
  FormControl,
  Button
} from "react-bootstrap";
import "./Login.css";
import { useNavigate, useLocation } from 'react-router-dom'
import { useAuth } from '../components/auth'
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faSignInAlt,
  faEnvelope,
  faLock,
} from "@fortawesome/free-solid-svg-icons";

export const Login = () => {
  const [user, setUser] = useState('')
  const navigate = useNavigate()
  const location = useLocation()
  const auth = useAuth()

  const redirectPath = location.state?.path || '/'

  const handleLogin = () => {
    auth.login(user)
    navigate(redirectPath, { replace: true })
  }
  return (
    <Container id="main-container" className="d-grid h-100">
        <Card>
          <Form id="sign-in-form" className="text-center p-3 w-100">
            <Form.Group className="d-flex justify-content-center mb-1">
              <img
                className="mb-4 bootstrap-logo"
                src="https://icons.getbootstrap.com/assets/icons/thermometer-half.svg"
                alt="Bootstrap 5"
              />
            </Form.Group>
            <h1 className="mb-3 fs-31 fw-normal">Sign In</h1>
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
                onChange={e =>setUser(e.target.value)}
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
              <Button variant="primary" size="lg" onClick={handleLogin}>
                Login
                <FontAwesomeIcon icon={faSignInAlt} className="mr-3" />
              </Button>
            </div>
          </Form>
        </Card>
      </Container>
  )
}