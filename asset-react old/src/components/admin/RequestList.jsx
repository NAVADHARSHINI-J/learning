import { useEffect, useState } from 'react';
import './css/RequestList.css'
import Sidebar from './SidebarAdmin';
import axios from 'axios';

function RequestList() {
    const [servicerequests, setServicerequests] = useState([])
    const [newrequests, setNewrequests] = useState([])
    const [selectedNewrequests, setSelectedNewrequests] = useState({})
    const [selectedService, setSelectedService] = useState({})
    const [showImage, setShowImage] = useState(false)
    const [filterservice, setFilterservice] = useState(null)
    const [filternew, setFilternew] = useState(null)
    const [filterserviceValue, setFilterserviceValue] = useState(null)
    const [filternewValue, setFilternewValue] = useState(null)

    const getServiceRequests = async () => {
        let token = localStorage.getItem("token")
        let headers = {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
        try {
            const response = await axios.get(`http://localhost:8081/api/servicerequest/getall`, headers)
            setServicerequests(response.data)
        } catch (error) {
            console.log(error)
        }
    }
    const getNewRequests = async () => {
        let token = localStorage.getItem("token")
        let headers = {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
        try {
            const response = await axios.get(`http://localhost:8081/api/assetrequest/getall`, headers)
            setNewrequests(response.data)
        } catch (error) {
            console.log(error)
        }
    }
    useEffect(() => {
        getNewRequests()
        getServiceRequests()
    }, [])

    //updating the approval of service request
    const updateServiceApprove = async (serviceRequest) => {
        let token = localStorage.getItem("token")
        let headers = {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
        try {
            const response = await axios.put(`http://localhost:8081/api/servicerequest/update/${serviceRequest.id}`
                , { "status": "APPROVED" }, headers
            )
            //delete the updated
            let temp = [...servicerequests]
            temp = temp.filter(s => s.id !== serviceRequest.id)
            temp.push(response.data)
            setServicerequests(temp)
        } catch (error) {
            console.log(error);
        }
        //after changing the status get the allocation detail of the service request
        //and update the allocation as undermaintenance
        try {
            const resp = await axios.get(`http://localhost:8081/api/assetallocation/${serviceRequest.asset?.id}/${serviceRequest.employee?.id}`, headers)
            console.log(resp);
            try {
                const r = await axios.put(`http://localhost:8081/api/assetallocation/update/${resp.data.id}`,
                    { "status": "UNDER_MAINTENANCE" }, headers
                )
                console.log(r)
            } catch (error) {

            }
        } catch (err) {
            console.log(err)
        }
    }

    //declining the service request
    const updateServiceDecline = async (serviceRequest) => {
        let token = localStorage.getItem("token")
        let headers = {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
        try {
            const response = await axios.put(`http://localhost:8081/api/servicerequest/update/${serviceRequest.id}`
                , { "status": "DECLINED" }, headers
            )
            //delete the updated
            let temp = [...servicerequests]
            temp = temp.filter(s => s.id !== serviceRequest.id)
            temp.push(response.data)
            setServicerequests(temp)
        } catch (error) {
            console.log(error);
        }
    }

    //approve the new request
    const approveNewRequest = async (newRequest) => {
        let token = localStorage.getItem("token")
        let headers = {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
        //change the status of the new request as approved
        try {
            const response = await axios.put(`http://localhost:8081/api/assetrequest/update-status/${newRequest.id}?status=APPROVED`
                , headers
            )
            let temp = [...newrequests]
            temp = temp.filter(n => n.id !== newRequest.id)
            temp.push(response.data)
            setNewrequests(temp)
        } catch (error) {
            console.log(error)
        }
        //after that allocate the asset for the requested employee
        try {
            const resp = await axios
                .post(`http://localhost:8081/api/assetallocation/add/${newRequest.asset.id}/${newRequest.employee.id}`,
                    {}, headers
                )
            console.log(resp)
        } catch (err) {
            console.log(err)
        }
    }

    //decline the new request
    const declineNewRequest = async (newRequest) => {
        let token = localStorage.getItem("token")
        let headers = {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        }
        //change the status of the new request as declined
        try {
            const response = await axios.put(`http://localhost:8081/api/assetrequest/update-status/${newRequest.id}?status=DECLINED`
                , headers
            )
            let temp = [...newrequests]
            temp = temp.filter(n => n.id !== newRequest.id)
            temp.push(response.data)
            setNewrequests(temp)
        } catch (error) {
            console.log(error)
        }
    }

    //change the service according to the filter
    const callService = async () => {
        if (filterservice === "employee") {
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            try {
                const resp = await axios.get(`http://localhost:8081/api/servicerequest/byEmployeeId?empId=${filterserviceValue}`, headers)
                setServicerequests(resp.data)
            } catch (error) {
                console.log(error)
                alert("Employee Id is invalid");
            }
        }
        else if (filterservice === "asset") {
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            try {
                const resp = await axios.get(`http://localhost:8081/api/servicerequest/byAssetId?assetId=${filterserviceValue}`, headers)
                setServicerequests(resp.data)
            } catch (error) {
                console.log(error)
                alert("Asset Id is invalid");
            }
        }
        else if (filterservice === "status") {
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            try {
                const resp = await axios.get(`http://localhost:8081/api/servicerequest/bystatus?status=${filterserviceValue}`, headers)
                setServicerequests(resp.data)
            } catch (error) {
                console.log(error)
            }
        }
        else {
            getServiceRequests()
        }
    }
    //change the new asset according to the filter
    const callNew = async () => {
        if (filternew === "employee") {
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            try {
                const resp = await axios.get(`http://localhost:8081/api/assetrequest/getbyempid/${filternewValue}`, headers)
                setNewrequests(resp.data)
            } catch (error) {
                console.log(error)
                alert("Employee Id is invalid");
            }
        }
        else if (filternew === "asset") {
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            try {
                const resp = await axios.get(`http://localhost:8081/api/assetrequest/getbyassetid/${filternewValue}`, headers)
                setNewrequests(resp.data)
            } catch (error) {
                console.log(error)
                alert("Asset Id is invalid");
            }
        }
        else if (filternew === "status") {
            let token = localStorage.getItem("token")
            let headers = {
                headers: {
                    "Authorization": `Bearer ${token}`
                }
            }
            try {
                const resp = await axios.get(`http://localhost:8081/api/assetrequest/getbystatus?status=${filternewValue}`, headers)
                setNewrequests(resp.data)
            } catch (error) {
                console.log(error)
            }
        }
        else {
            getNewRequests()
        }
    }

    return (
        <div>
            <div className="container-fluid">
                <div className="row">
                    {/* <!-- Sidebar Component --> */}
                    <Sidebar />
                    {/* <!-- Main Content Area --> */}
                    <div className="col-md-10 p-4">
                        <div className="card">
                            <div className="card-header" style={{ backgroundColor: "#159895" }}>
                                <ul className="nav nav-tabs card-header-tabs">
                                    <li className="nav-item">
                                        <a className="nav-link text-warning active" data-bs-toggle="tab" href="#service-requests">Service Requests</a>
                                    </li>
                                    <li className="nav-item">
                                        <a className="nav-link text-warning" data-bs-toggle="tab" href="#new-requests">New Requests</a>
                                    </li>
                                </ul>
                            </div>
                            <div className="card-body tab-content">
                                {/* <!-- Service Requests Tab --> */}
                                <div className="tab-pane active" id="service-requests">
                                    <form onSubmit={(e) => { e.preventDefault(), callService() }}>
                                        <div className='row justify-content-end align-items-center g-2 mb-2'>
                                            <div className='col-auto'>
                                                <select className="form-select " defaultValue="all" onChange={(e) => setFilterservice(e.target.value)} >
                                                    <option value="all" >All</option>
                                                    <option value="employee">Employee Id</option>
                                                    <option value="asset">Asset Id</option>
                                                    <option value="status" >Status</option>
                                                </select>
                                            </div>{filterservice !== "all" && <div className='col-auto'>
                                                {
                                                    filterservice === "employee" ? <input className='form-control' placeholder='Enter employee Id'
                                                        onChange={(e) => { setFilterserviceValue(e.target.value) }} /> :
                                                        filterservice === "asset" ? <input className='form-control' placeholder='Enter asset Id'
                                                            onChange={(e) => { setFilterserviceValue(e.target.value) }} /> :
                                                            filterservice === "status" ?
                                                                <select className="form-select" defaultValue="PENDING" onChange={(e) => { setFilterserviceValue(e.target.value) }} >
                                                                    <option value="PENDING">--select status--</option>
                                                                    <option value="PENDING" >PENDING</option>
                                                                    <option value="APPROVED">APPROVED</option>
                                                                    <option value="DECLINED">DECLINED</option>
                                                                </select> : ""
                                                }
                                            </div>}
                                            <div className='col-auto'>
                                                <button type='submit'
                                                    className='btn btn-primary'>Search</button>
                                            </div>
                                        </div>
                                    </form>
                                    <table className="table table-striped table-hover">
                                        <thead>
                                            <tr>
                                                <th>Request ID</th>
                                                <th>Employee</th>
                                                <th>Asset</th>
                                                <th>Date</th>
                                                <th>Status</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {servicerequests.map((s, index) => (
                                                <tr key={index}>
                                                    <td>{s.id}</td>
                                                    <td>{s.employee?.name}</td>
                                                    <td>{s.asset?.name}</td>
                                                    <td>{s.requestDate}</td>
                                                    <td><span className="badge bg-warning">{s.status}</span></td>
                                                    {
                                                        s.status === "PENDING" ? <td className="table-actions">
                                                            <button className="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#serviceRequestDetailsModal1"
                                                                onClick={() => setSelectedService(s)}>
                                                                <i className="bi bi-eye"></i>
                                                            </button>
                                                            <button className="btn btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#approveServiceRequestModal"
                                                                onClick={() => setSelectedService(s)}>
                                                                <i className="bi bi-check-circle"></i>
                                                            </button>
                                                            <button className="btn btn-sm btn-danger" data-bs-toggle="modal" data-bs-target="#declineServiceRequestModal"
                                                                onClick={() => setSelectedService(s)}>
                                                                <i className="bi bi-x-circle"></i>
                                                            </button>
                                                        </td> : s.status === "APPROVED" ? <td className="table-actions">
                                                            <button className="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#serviceRequestDetailsModal1"
                                                                onClick={() => setSelectedService(s)}>
                                                                <i className="bi bi-eye"></i>
                                                            </button>
                                                        </td> : s.status === "DECLINED" ? <td className="table-actions">
                                                            <button className="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#serviceRequestDetailsModal1"
                                                                onClick={() => setSelectedService(s)}>
                                                                <i className="bi bi-eye"></i>
                                                            </button>
                                                            <button className="btn btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#approveServiceRequestModal"
                                                                onClick={() => setSelectedService(s)}>
                                                                <i className="bi bi-check-circle"></i>
                                                            </button>
                                                        </td> : ""
                                                    }

                                                </tr>
                                            ))}

                                        </tbody>
                                    </table>
                                </div>
                                {/* <!-- New Requests Tab --> */}
                                <div className="tab-pane " id="new-requests">
                                    <form onSubmit={(e) => { e.preventDefault(), callNew() }}>
                                        <div className='row justify-content-end align-items-center g-2 mb-2'>
                                            <div className='col-auto'>
                                                <select className="form-select " defaultValue="all" onChange={(e) => setFilternew(e.target.value)} >
                                                    <option value="all" >All</option>
                                                    <option value="employee">Employee Id</option>
                                                    <option value="asset">Asset Id</option>
                                                    <option value="status" >Status</option>
                                                </select>
                                            </div>{filternew !== "all" && <div className='col-auto'>
                                                {
                                                    filternew === "employee" ? <input className='form-control' placeholder='Enter employee Id'
                                                        onChange={(e) => { setFilternewValue(e.target.value) }} /> :
                                                        filternew === "asset" ? <input className='form-control' placeholder='Enter asset Id'
                                                            onChange={(e) => { setFilternewValue(e.target.value) }} /> :
                                                            filternew === "status" ?
                                                                <select className="form-select" defaultValue="PENDING" onChange={(e) => { setFilternewValue(e.target.value) }} >
                                                                    <option value="PENDING">--select status--</option>
                                                                    <option value="PENDING" >PENDING</option>
                                                                    <option value="APPROVED">APPROVED</option>
                                                                    <option value="DECLINED">DECLINED</option>
                                                                </select> : ""
                                                }
                                            </div>}
                                            <div className='col-auto'>
                                                <button type='submit'
                                                    className='btn btn-primary'>Search</button>
                                            </div>
                                        </div>
                                    </form>
                                    <table className="table table-striped table-hover">
                                        <thead>
                                            <tr>
                                                <th>Request ID</th>
                                                <th>Employee</th>
                                                <th>Requested Asset</th>
                                                <th>Date</th>
                                                <th>Status</th>
                                                <th>Actions</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {
                                                newrequests.map((n, index) => (
                                                    <tr key={index}>
                                                        <td>{n.id}</td>
                                                        <td>{n.employee?.name}</td>
                                                        <td>{n.asset?.name}</td>
                                                        <td>{n.requestDate}</td>
                                                        <td><span className="badge bg-warning">{n.status}</span></td>
                                                        {
                                                            n.status === "PENDING" ? <td className="table-actions">
                                                                <button className="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#newRequestDetailsModal1"
                                                                    onClick={() => setSelectedNewrequests(n)}>
                                                                    <i className="bi bi-eye"></i>
                                                                </button>
                                                                <button className="btn btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#approveNewRequestModal"
                                                                    onClick={() => setSelectedNewrequests(n)}>
                                                                    <i className="bi bi-check-circle"></i>
                                                                </button>
                                                                <button className="btn btn-sm btn-danger" data-bs-toggle="modal" data-bs-target="#declineNewRequestModal"
                                                                    onClick={() => setSelectedNewrequests(n)}>
                                                                    <i className="bi bi-x-circle"></i>
                                                                </button>
                                                            </td> : n.status === "APPROVED" ? <td className="table-actions">
                                                                <button className="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#newRequestDetailsModal1"
                                                                    onClick={() => setSelectedNewrequests(n)}>
                                                                    <i className="bi bi-eye"></i>
                                                                </button>
                                                            </td> : n.status === "DECLINED" ? <td className="table-actions">
                                                                <button className="btn btn-sm btn-info" data-bs-toggle="modal" data-bs-target="#newRequestDetailsModal1"
                                                                    onClick={() => setSelectedNewrequests(n)}>
                                                                    <i className="bi bi-eye"></i>
                                                                </button>
                                                                <button className="btn btn-sm btn-success" data-bs-toggle="modal" data-bs-target="#approveNewRequestModal"
                                                                    onClick={() => setSelectedNewrequests(n)}>
                                                                    <i className="bi bi-check-circle"></i>
                                                                </button>
                                                            </td> : ""
                                                        }
                                                    </tr>
                                                ))
                                            }

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            {/* <!-- Service Request Details Modals --> */}
            {selectedService && <div className="modal fade" id="serviceRequestDetailsModal1" tabIndex="-1">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header bg-info text-white">
                            <h5 className="modal-title">Service Request Details</h5>
                            <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div className="modal-body">
                            <div className="row mb-2">
                                <div className="col-6">
                                    <strong>Employee ID:</strong> {selectedService.employee?.id}
                                </div>
                                <div className="col-6">
                                    <strong>Employee Name:</strong>  {selectedService.employee?.name}
                                </div>
                            </div>
                            <div className="row mb-2">
                                <div className="col-6">
                                    <strong>Asset ID:</strong>  {selectedService.asset?.id}
                                </div>
                                <div className="col-6">
                                    <strong>Asset Name:</strong> {selectedService.asset?.name}
                                </div>
                            </div>
                            <div className="row mb-2">
                                <div className="col-12">
                                    <strong>Request Date:</strong> {selectedService.requestDate}
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-12">
                                    <strong>Reason for Service:</strong>
                                    <p>{selectedService.reason}</p>
                                </div>
                            </div>
                            <div className='row mb-2'>
                                <button type='button' className='btn btn-primary' onClick={() => { setShowImage(true) }} >View Image</button>
                            </div>
                            {
                                showImage && selectedService.imageUrl !== null ? <div className="text-center">
                                    <img src={`/images/${selectedService.imageUrl.split("\\").pop()}`} className="img-thumbnail rounded" />
                                </div> : ""
                            }
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" onClick={() => setShowImage(false)} data-bs-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>}
            {/* <!-- New Request Details Modals --> */}
            {selectedNewrequests && <div className="modal fade" id="newRequestDetailsModal1" tabIndex="-1">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header bg-info text-white">
                            <h5 className="modal-title">New Request Details</h5>
                            <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div className="modal-body">
                            <div className="row mb-2">
                                <div className="col-6">
                                    <strong>Employee ID:</strong> {selectedNewrequests.employee?.id}
                                </div>
                                <div className="col-6">
                                    <strong>Employee Name:</strong>  {selectedNewrequests.employee?.name}
                                </div>
                            </div>
                            <div className="row mb-2">
                                <div className="col-6">
                                    <strong>Requested Asset:</strong>  {selectedNewrequests.asset?.name}
                                </div>
                                <div className="col-6">
                                    <strong>Asset Id:</strong>{selectedNewrequests.asset?.id}
                                </div>
                            </div>
                            <div className="row mb-2">
                                <div className="col-6">
                                    <strong>Asset Type:</strong> {selectedNewrequests.asset?.category?.name}
                                </div>
                                <div className="col-6">
                                    <strong>Request Date:</strong> {selectedNewrequests.requestDate}
                                </div>
                            </div>
                            <div className="row">
                                <div className="col-12">
                                    <strong>Reason:</strong>
                                    <p>{selectedNewrequests.reason}</p>
                                </div>
                            </div>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>}
            {/* <!-- Approve Service Request Modal --> */}
            {selectedService && <div className="modal fade" id="approveServiceRequestModal" tabIndex="-1">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header bg-success text-white">
                            <h5 className="modal-title">Approve Service Request</h5>
                            <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div className="modal-body">
                            Are you sure you want to approve this service request for Asset {selectedService.asset?.name}?
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="button" className="btn btn-success"
                                onClick={() => { updateServiceApprove(selectedService) }} data-bs-dismiss="modal">Confirm</button>
                        </div>
                    </div>
                </div>
            </div>}

            {/* <!-- Decline Service Request Modal --> */}
            {selectedService && <div className="modal fade" id="declineServiceRequestModal" tabIndex="-1">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header bg-danger text-white">
                            <h5 className="modal-title">Decline Service Request</h5>
                            <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div className="modal-body">
                            Are you sure you want to decline this service request?
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="button" className="btn btn-danger"
                                onClick={() => { updateServiceDecline(selectedService) }} data-bs-dismiss="modal">Confirm</button>
                        </div>
                    </div>
                </div>
            </div>}

            {/* <!-- Approve New Request Modal --> */}
            {selectedNewrequests && <div className="modal fade" id="approveNewRequestModal" tabIndex="-1">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header bg-success text-white">
                            <h5 className="modal-title">Approve New Request</h5>
                            <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div className="modal-body">
                            Are you sure you want to approve this new request?
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="button" className="btn btn-success"
                                onClick={() => { approveNewRequest(selectedNewrequests) }} data-bs-dismiss="modal">Confirm</button>
                        </div>
                    </div>
                </div>
            </div>}

            {/* <!-- Decline New Request Modal --> */}
            {selectedNewrequests && <div className="modal fade" id="declineNewRequestModal" tabIndex="-1">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header bg-danger text-white">
                            <h5 className="modal-title">Decline New Request</h5>
                            <button type="button" className="btn-close" data-bs-dismiss="modal"></button>
                        </div>
                        <div className="modal-body">
                            Are you sure you want to decline this new request?
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="button" className="btn btn-danger"
                                onClick={() => { declineNewRequest(selectedNewrequests) }} data-bs-dismiss="modal">Confirm</button>
                        </div>
                    </div>
                </div>
            </div>}
        </div>
    )
}
export default RequestList;