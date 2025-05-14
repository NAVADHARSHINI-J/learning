
import React, { useState, useEffect } from 'react';
import { Chart } from 'primereact/chart';
import axios from 'axios';

function Dashboard() {
    const [chartData, setChartData] = useState({});
    const [label, setLabel] = useState([]);
    const [datas, setData] = useState([]);

    useEffect(() => {
        const getData = async () => {
            const resp = await axios.get("http://localhost:8081/api/product/chart")
            console.log(resp)
            setData(resp.data.datas)
            setLabel(resp.data.lables)
            const data = {
                labels: label,
                datasets: [
                    {
                        label: 'Sales',
                        data: datas,
                        backgroundColor: [
                            'rgba(255, 159, 64, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(153, 102, 255, 0.2)'
                        ],
                        borderColor: [
                            'rgb(255, 159, 64)',
                            'rgb(75, 192, 192)',
                            'rgb(54, 162, 235)',
                            'rgb(153, 102, 255)'
                        ],
                        borderWidth: 1
                    }
                ]
            };
            setChartData(data);
        }
        getData();

    }, []);

    return (
        <div className="card">
            <Chart type="bar" data={chartData} />
        </div>
    )
}
export default Dashboard