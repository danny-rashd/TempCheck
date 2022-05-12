import React, { useEffect, useState } from "react";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Line } from "react-chartjs-2";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

export default function LineChart() {
  const labels = [];
  const [data, setData] = useState({
    labels,
    datasets: [
      {
        label: "Dataset 1",
        data: [],
        borderColor: "rgb(255,99,132)",
        backgroundColor: "rgb(255,99,132)",
      },
      {
        label: "Dataset 2",
        data: [],
        borderColor: "rgb(53,162,235)",
        backgroundColor: "rgb(53,162,235))",
      },
    ],
  });
  useEffect(() => {
    const fetchData = async () => {
      const url =
        "https://raw.githubusercontent.com/danny-rashd/TempCheck/main/webapp/src/components/temp_final.json";
      const labelSet = [];
      const dataSet1 = [];
      fetch(url, {
        method: "GET",
      })
        .then((data) => {
          const res = data.json();
          return res;
        })
        .then((res) => {
          for (const val of res) {
            dataSet1.push(val.Temperature);
            labelSet.push(val.Datetime);
          }

          setData({
            labels: labelSet,
            datasets: [
              {
                label: "Dataset 1",
                data: dataSet1,
                borderColor: "rgb(255,99,132)",
                backgroundColor: "rgb(255,99,132)",
              },
            ],
          });
        })
    };

    fetchData();
  });
  return (
    <div style={{ width: "70%", height: "70%" }}>
      <Line data={data} />
    </div>
  );
}
