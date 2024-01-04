import {
  Container,
  Row,
  Col,
  Card,
  Form,
  Button,
  Alert,
} from "react-bootstrap";
import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "src/contexts/AuthContext";
import Spinner from "react-bootstrap/Spinner";
import useRequest from "src/hooks/useRequest";

function Login() {
  const [error, setError] = useState(null);
  const [, setAuthInfo] = useContext(AuthContext);
  const [alertShow, setAlertShow] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    const requestData = {
      email: data.get("email"),
      password: data.get("password"),
      rememberMe: data.get("rememberMe"),
    };

    const options = {
      method: "post",
      body: JSON.stringify(requestData),
      headers: {
        "Content-Type": "application/json",
      },
    };

    request("/login", options);
  };

  /**
   * @param {Response} response
   */
  const handleResponse = (response) => {
    if (response.ok) {
      response.json().then((data) => {
        setAuthInfo({
          isAuthenticated: true,
          token: data.accessToken,
        });
        navigate("/");
      });
    } else {
      response.json().then((data) => {
        setAlertShow(true);
        setError(data || "Something went wrong!");
      });
    }
  };

  const handleException = (exception) => {
    setAlertShow(true);
    setError("Something went wrong!");
  };

  const [request, loading] = useRequest({
    onResponse: handleResponse,
    onException: handleException,
  });

  return (
    <section className="bg-light p-3 p-md-4 p-xl-5">
      <Container>
        <Row className="justify-content-center">
          <Col xs={12} xxl={11}>
            <Card className="border-light-subtle shadow-sm">
              <Row className="g-0">
                <Col xs={12} md={6}>
                  <Card.Img
                    className="img-fluid rounded-start w-100 h-100 object-fit-cover"
                    src="/images/auth/login-background.jpg"
                    alt=""
                    loading="lazy"
                  />
                </Col>
                <Col
                  xs={12}
                  md={6}
                  className="d-flex align-items-center justify-content-center"
                >
                  <Col xs={12} lg={11} xl={10}>
                    <Card.Body className="p-3 p-md-4 p-xl-5">
                      <Row>
                        <Col xs={12}>
                          <div className="mb-5">
                            <h1 className="text-center">Admin Login</h1>
                          </div>
                        </Col>
                      </Row>
                      <Form onSubmit={handleSubmit}>
                        <Alert
                          variant="danger"
                          show={alertShow}
                          onClose={() => setAlertShow(false)}
                          dismissible
                        >
                          {error?.message || "Something went wrong!"}
                        </Alert>
                        <Row className="gy-3 overflow-hidden">
                          <Col xs={12}>
                            <Form.Floating className="mb-3">
                              <Form.Control
                                type="text"
                                name="email"
                                id="email"
                                placeholder="name@example.com"
                                required
                              />
                              <Form.Label htmlFor="email">Email</Form.Label>
                            </Form.Floating>
                          </Col>
                          <Col xs={12}>
                            <Form.Floating className="mb-3">
                              <Form.Control
                                type="password"
                                name="password"
                                id="password"
                                placeholder="Password"
                                required
                              />
                              <Form.Label htmlFor="password">
                                Password
                              </Form.Label>
                            </Form.Floating>
                          </Col>
                          <Col xs={12}>
                            <Form.Check className="form-check">
                              <Form.Check.Input
                                type="checkbox"
                                value="true"
                                name="rememberMe"
                                id="remember_me"
                              />
                              <Form.Check.Label
                                className="form-check-label text-secondary"
                                htmlFor="remember_me"
                              >
                                Keep me logged in
                              </Form.Check.Label>
                            </Form.Check>
                          </Col>
                          <Col xs={12}>
                            <div className="d-grid">
                              <Button
                                variant="dark"
                                size="lg"
                                type="submit"
                                disabled={loading}
                              >
                                {loading ? (
                                  <Spinner as="span" size="sm" />
                                ) : (
                                  "Login"
                                )}
                              </Button>
                            </div>
                          </Col>
                        </Row>
                      </Form>
                    </Card.Body>
                  </Col>
                </Col>
              </Row>
            </Card>
          </Col>
        </Row>
      </Container>
    </section>
  );
}

export default Login;
