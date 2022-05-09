import LineChart from "../components/LineChart";
import React from "react";
import { Container } from "react-bootstrap";

function ChartData() {
  return (
    <div>
      <Container className="d-flex justify-content-center mt-3">
        <h1>ChartData</h1>
      </Container>
      <div className="justify-content-center d-flex">
        <LineChart />
      </div>
    </div>
  );
}

export default ChartData;
