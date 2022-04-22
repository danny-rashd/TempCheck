import "react-dropzone-uploader/dist/styles.css";
import Dropzone from "react-dropzone-uploader";
import { Container, Form } from "react-bootstrap";
import "./table.css";
export const Upload = () => {
  // specify upload params and url for your files
  const getUploadParams = ({ meta }) => {
    return { url: "http://localhost:8080/api/v1/springboot/upload" };
  };

  // called every time a file's `status` changes
  const handleChangeStatus = ({ meta, file }, status) => {
    console.log(status, meta, file);
  };

  // receives array of files that are done uploading when submit button is clicked
  const handleSubmit = (files, allFiles) => {
    console.log(files.map((f) => f.meta));
    allFiles.forEach((f) => f.remove());
  };

  return (
    <Container className="d-grid h-50">
      <Form className="text-center p-2 w-100">
        <Dropzone
          getUploadParams={getUploadParams}
          onChangeStatus={handleChangeStatus}
          onSubmit={handleSubmit}
          accept=".csv"
        />
      </Form>
    </Container>
  );
};

<Upload />;
