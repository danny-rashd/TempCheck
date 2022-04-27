import "react-dropzone-uploader/dist/styles.css";
import Dropzone from "react-dropzone-uploader";
import {
  Container,
  Form,
  Row,
  Button,
  ListGroup,
  Card,
  Modal,
} from "react-bootstrap";
import "./home.css";
import React, { useEffect, useState } from "react";
import axios from "axios";
import BootstrapTable from "react-bootstrap-table-next";
import paginationFactory from "react-bootstrap-table2-paginator";
import filterFactory, {
  numberFilter,
  dateFilter,
} from "react-bootstrap-table2-filter";
import ToolkitProvider from "react-bootstrap-table2-toolkit";
import "react-bootstrap-table2-filter/dist/react-bootstrap-table2-filter.min.css";
import "react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css";
import "react-bootstrap-table-next/dist/react-bootstrap-table2.css";
import Papa from "papaparse";

export const TempTable = () => {
  const UPLOAD_CSV_API = "http://localhost:8080/api/v1/upload";
  const GET_CSV_API = "http://localhost:8080/api/v1/files";
  const DOWNLOAD_CSV_API = "http://localhost:8080/api/v1/download/";
  const WAIT_TIME = 1000;

  // upload file
  const [post, setPost] = useState([]);

  // load data
  const [data, setData] = useState([]);

  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const getUploadParams = ({ meta }) => {
    return { url: UPLOAD_CSV_API };
  };

  const handleChangeStatus = ({ meta, file }, status) => {
    console.log(status, meta, file);
  };

  const handleSubmit = (files, allFiles) => {
    console.log(files.map((f) => f.meta));
    allFiles.forEach((f) => f.remove());
  };

  useEffect(() => {
    const id = setInterval(() => {
      axios
        .get(GET_CSV_API)
        .then((res) => {
          setPost(res.data);
          console.log(res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    }, WAIT_TIME);
    return () => clearInterval(id);
  }, [post]);

  const columns = [
    {
      dataField: "A",
      text: "A",
      sort: true,
      headerAlign: "center",
      headerAttrs: {
        hidden: false,
      },
    },
    {
      dataField: "Epoch",
      text: "Epoch",
      sort: true,
      headerAlign: "center",
      headerAttrs: {
        hidden: false,
      },
    },
    {
      dataField: "Temperature",
      text: "Temperature",
      sort: true,
      filter: numberFilter({
        getFilter: (filter) => {
          let Filter = filter;
        },
      }),
    },
    {
      dataField: "Datetime",
      text: "Datetime",
      headerAlign: "center",
      sort: true,
      filter: dateFilter({
        getFilter: (filter) => {
          let DateFilter = filter;
        },
      }),
    },
  ];

  const ExportFiltertoCSV = (props) => {
    const handleClick = () => {
      props.onExport();
    };
    return (
      <div className="d-flex justify-content-center m-2">
        <Button className="btn btn-success" onClick={handleClick}>
          Export to CSV
        </Button>
      </div>
    );
  };
  const pagination = paginationFactory({
    page: 1,
    sizePerPage: 10,
    lastPageText: ">>",
    firstPageText: "<<",
    nextPageText: ">",
    prePageText: "<",
    showTotal: true,
    alwaysShowAllBtns: true,
    onPageChange: function (page, sizePerPage) {
      console.log("page", page);
      console.log("sizePerPage", sizePerPage);
    },
    onSizePerPageChange: function (page, sizePerPage) {
      console.log("page", page);
      console.log("sizePerPage", sizePerPage);
    },
  });

  const handleClick = async (id) => {
    const response = await fetch(DOWNLOAD_CSV_API + id);
    console.log(response);
    const reader = response.body.getReader();
    const result = await reader.read(); // raw array
    const decoder = new TextDecoder("utf-8");
    const csv = decoder.decode(result.value); // the csv text
    const results = Papa.parse(csv, { header: true }); // object with { data, errors, meta }
    const rows = results.data; // array of objects
    setData(rows);
  };

  return (
    <>
      <div className="d-flex justify-content-center m-2">
        <Button
          variant="primary"
          size="lg"
          className="d-flex justify-content-center"
          onClick={handleShow}
        >
          Upload file
        </Button>
      </div>
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Upload File</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form className="p-10 mt-3">
            <Dropzone
              getUploadParams={getUploadParams}
              onChangeStatus={handleChangeStatus}
              onSubmit={handleSubmit}
              accept=".csv"
            />
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="primary" onClick={handleClose}>
            Save Changes
          </Button>
        </Modal.Footer>
      </Modal>
      <div className="container">
        <div className="item">
          <Card className="mt-2">
            <Card.Header>
              <strong>Uploaded Files</strong>
            </Card.Header>
            <ListGroup className="list-group-flush mt-3 mb-3">
              <ol>
                <p class="text-info">
                  Click the button to insert the data in the table below
                </p>
                {post.map((file) => (
                  <li>
                    <Button onClick={() => handleClick(file.objectId)}>
                      <strong>{file.filename}</strong>
                    </Button>
                    <Row className="mb-1">{`Id: ${file.objectId}`}</Row>
                  </li>
                ))}
              </ol>
            </ListGroup>
          </Card>
        </div>
      </div>
      <div className="ml-3 mr-3">
        {" "}
        <ToolkitProvider
          bootstrap4
          keyField="id"
          columns={columns}
          data={data}
          exportCSV={{ onlyExportFiltered: true, exportAll: false }}
        >
          {(props) => (
            <React.Fragment>
              <ExportFiltertoCSV {...props.csvProps} />
              <BootstrapTable
                pagination={pagination}
                filter={filterFactory()}
                {...props.baseProps}
              />
            </React.Fragment>
          )}
        </ToolkitProvider>
      </div>
      <Container></Container>
    </>
  );
};

<TempTable />;
