import React, { useMemo, useState } from "react";
import { Container, Card, Form, Button } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.css";
import "react-bootstrap-table-next/dist/react-bootstrap-table2.min.css";
import BootstrapTable from "react-bootstrap-table-next";
import paginationFactory from "react-bootstrap-table2-paginator";
import { useDropzone } from "react-dropzone";
import Papa from "papaparse";

//generate data
export const productsGenerator = (quantity) => {
  const items = [];
  for (let i = 1; i < quantity; i++) {
    items.push({ id: i, name: `Ordinarycoders course ${i}`, price: 100 + i });
  }
  return items;
};

const products = productsGenerator(100);

export default function Table() {
  const { getInputProps, isFocused, isDragAccept, isDragReject } = useDropzone(
    {}
  );

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

  const columns = [
    {
      dataField: "id",
      text: "Product ID",
      sort: true,
    },
    {
      dataField: "name",
      text: "Product Name",
      sort: true,
    },
    {
      dataField: "price",
      text: "Product Price in $",
      sort: true,
    },
  ];

  return (
    <Container id="main-container" className="d-grid h-50">
      <Form id="sign-in-form" className="text-center p-2 w-100">
        <div>Welcome</div>
        <Button>Logout</Button>
        <Card>
          <BootstrapTable
            bootstrap4
            keyField="id"
            data={products}
            columns={columns}
            pagination={paginationFactory({ sizePerPage: 5 })}
          />
        </Card>
      </Form>
    </Container>
  );
}
