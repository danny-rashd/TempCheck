import { useNavigate } from "react-router-dom";
import React, { useState } from "react";
import { useDropzone } from "react-dropzone";
import Papa from "papaparse";
import { Container, Form, Button, Modal } from "react-bootstrap";
import "./table.css";
export const DataTable = (props) => {
  const { getRootProps, getInputProps } = useDropzone({});

  // State to show modal
  const [smShow, setSmShow] = useState(false);

  // State to store parsed data
  const [parsedData, setParsedData] = useState([]);

  //State to store table Column name
  const [tableRows, setTableRows] = useState([]);

  //State to store the values
  const [values, setValues] = useState([]);

  const changeHandler = (event) => {
    // Passing file data (event.target.files[0]) to parse using Papa.parse
    Papa.parse(event.target.files[0], {
      header: true,
      skipEmptyLines: true,
      complete: function (results) {
        const rowsArray = [];
        const valuesArray = [];

        // Iterating data to get column name and their values
        results.data.forEach((d) => {
          rowsArray.push(Object.keys(d));
          valuesArray.push(Object.values(d));
        });

        // Parsed Data Response in array format
        setParsedData(results.data);

        // Filtered Column Names
        setTableRows(rowsArray[0]);

        // Filtered Values
        setValues(valuesArray);
      },
    });
  };

  return (
    <Container className="d-grid h-50">
      <Form className="text-center p-2 w-100">
        <Container>
          <Button
            variant="primary"
            onClick={() => setSmShow(true)}
            className="me-2 mt-3"
          >
            Upload file
          </Button>
          <Modal
            size="sm"
            show={smShow}
            onHide={() => setSmShow(false)}
            aria-labelledby="example-modal-sizes-title-sm"
          >
            <Modal.Header closeButton>
              <Modal.Title id="example-modal-sizes-title-sm">
                Small Modal
              </Modal.Title>
            </Modal.Header>
            <Modal.Body>
              {" "}
              <div {...getRootProps({ className: "dropzone" })}>
                <input
                  {...getInputProps()}
                  type="file"
                  name="file"
                  onChange={changeHandler}
                  accept=".csv"
                  style={{ display: "block", margin: "10px auto" }}
                />
              </div>
            </Modal.Body>
          </Modal>
        </Container>
        <br />
        <br />
        {/* Table */}
        <div
          className="container"
          style={{
            width: "100%",
            height: "550px",
            minWidth: "900px",
            overflowY: "auto",
            padding: "10px",
          }}
        >
          <table className="table table-dark">
            <thead
              className="thead-dark"
              style={{ position: "sticky", top: "0" }}
            >
              <tr>
                {tableRows.map((rows, index) => {
                  return <th key={index}>{rows}</th>;
                })}
              </tr>
            </thead>
            <tbody>
              {values.map((value, index) => {
                return (
                  <tr key={index}>
                    {value.map((val, i) => {
                      return <td key={i}>{val}</td>;
                    })}
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      </Form>
    </Container>
  );
};
