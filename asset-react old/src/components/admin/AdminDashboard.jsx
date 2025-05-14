import { useEffect, useState } from "react";
import "./css/Dashboard.css"
import Sidebar from "./SidebarAdmin";
import axios from "axios";
function AdminDashboard() {

    const [totalAsset, setTotalAsset] = useState(null);
    const [totalEmployee, setTotalEmployee] = useState(null);
    const [pendingRequest, setPendingRequest] = useState(null);
    const [pendingService, setPendingService] = useState(null);
    const [admin, setAdmin] = useState(null);

    useEffect(() => {
        const getAsset = async () => {
            let token = localStorage.getItem("token")
            let user = localStorage.getItem("username")
            setAdmin(user)
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            try {
                const response = await axios.get("http://localhost:8081/api/asset/getAssetSize", headers)
                setTotalAsset(response.data)
            } catch (error) {
                console.log(error)
            }
        }
        const getEmployee = async () => {
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            try {
                const response = await axios.get("http://localhost:8081/api/employee/getEmpSize", headers)
                setTotalEmployee(response.data)
            } catch (error) {
                console.log(error)
            }
        }
        const getRequest = async () => {
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            try {
                const response = await axios.get(`http://localhost:8081/api/assetrequest/getbystatus?status=PENDING`, headers)
                let temp = response.data.length
                setPendingRequest(temp)
            } catch (error) {
                console.log(error)
            }
        }
        const getservice = async () => {
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            try {
                const response = await axios.get(`http://localhost:8081/api/servicerequest/bystatus?status=PENDING`, headers)
                let temp = response.data.length
                setPendingService(temp)
            } catch (error) {
                console.log(error)
            }
        }
        getAsset()
        getEmployee()
        getRequest()
        getservice()
    }, [])

    return (
        <div>
            <div className="container-fluid">
                <div className="row">
                    {/* <!-- Sidebar (Original) -->*/}
                    <Sidebar />
                    {/* <!-- Main Content Area --> */}
                    <div className="col-md-10 p-4">
                        <div className="card">
                            <div className="card-header">
                                <h3 className="mb-2">Dashboard</h3>
                            </div>
                            <div className="card-body">
                                <div className="row mb-4 ">
                                    <div className="col">
                                        <div className="p-4 bg-light rounded shadow-sm d-flex align-items-center justify-content-between">
                                            <h3 className="mb-0 title fade-in">
                                                <i className="bi bi-person-circle me-2"></i>
                                                Welcome back, {admin}!
                                            </h3>
                                            <span className="badge bg-success fs-6">Admin Panel</span>
                                        </div>
                                    </div>
                                </div>
                                <div className="dashboard-stats row ">
                                    <div className="col-sm-5">
                                        <div className="card dashboard card-total-employees text-center">
                                            <div className="card-body dashboard-body">
                                                <h5 className="card-title dashboard-title">Total Employees</h5>
                                                <p className="display-6">{totalEmployee}</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="col-sm-5">
                                        <div className="card dashboard card-total-assets text-center">
                                            <div className="card-body dashboard-body">
                                                <h5 className="card-title dashboard-title">Total Assets</h5>
                                                <p className="display-6">{totalAsset}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div className="dashboard-stats row ">
                                    <div className="col-sm-5">
                                        <div className="card dashboard card-pending-requests text-center">
                                            <div className="card-body dashboard-body">
                                                <h5 className="card-title dashboard-title">Pending Requests</h5>
                                                <p className="display-6">{pendingRequest}</p>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="col-sm-5">
                                        <div className="card dashboard card-pending-asset text-center">
                                            <div className="card-body dashboard-body">
                                                <h5 className="card-title dashboard-title">Pending Service</h5>
                                                <p className="display-6">{pendingService}</p>
                                            </div>
                                        </div>
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
export default AdminDashboard;