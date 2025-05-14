import { useParams } from 'react-router';
import './css/AssetDetails.css'
import Sidebar from './Sidebar'
import { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
function AssetDetails() {

    const [assetdetails, setAssetDetails] = useState([]);

    const { assetId } = useParams('assetId')
    console.log("asset id: ", assetId);

    useEffect(() => {
        const getAssetsById = async () => {
            try {
                const response = await axios.get(`http://localhost:8081/api/asset/getbyid/${assetId}`);
                setAssetDetails(response.data)
            } catch (error) {

            }

        }
        getAssetsById();
    }, [])
    return (
        <div className="container-fluid">
            <div className="row">
                { /* <!-- Sidebar --> */}
                <Sidebar />
                {/* <!-- Main Content --> */}
                <div className="col-md-10 p-4">
                    <div className="card">
                        <div className="card-header secondary-bg text-white">
                            Asset Details
                        </div>
                        <div className="card-body">
                            <div className="row">
                                <div className="col-md-6">
                                    <Link to={`/employee/newassetrequest/${assetId}`} >
                                        <button className="btn btn-success">Request Asset</button>
                                    </Link>
                                </div>
                                <div className="row mb-3">

                                </div>
                            </div>

                            <div className="col-md-6">
                                <h3 >{assetdetails.name}</h3>
                                <hr />
                                <div className="row mb-3">
                                    <div className="col-6">
                                        <strong>Model:</strong> {assetdetails.model}
                                    </div>
                                    <div className="col-6">
                                        <strong>Date:</strong> {assetdetails.date}
                                    </div>
                                </div>
                                <div className="row mb-3">
                                    <div className="col-6">
                                        <strong>Configuration:</strong> {assetdetails.configuration}
                                    </div>
                                    <div className="col-6">
                                        <strong>Available Units:</strong> {assetdetails.quantity}
                                    </div>
                                </div>
                                <div className="row mb-3">
                                    <div className="col-12">
                                        <strong>Description:</strong>
                                        <p>{assetdetails.description}</p>
                                    </div>
                                </div>
                                <div className="row mb-3">
                                    <div className="col-12">
                                        <strong>Status</strong> {assetdetails.status}
                                    </div>
                                </div>
                            </div>
                        </div>


                    </div>
                </div>
            </div>
        </div>

    )
}

export default AssetDetails;